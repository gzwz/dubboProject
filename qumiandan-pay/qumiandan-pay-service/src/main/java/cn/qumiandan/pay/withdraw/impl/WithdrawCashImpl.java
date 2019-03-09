package cn.qumiandan.pay.withdraw.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.enums.PayTypeEnum;
import cn.qumiandan.pay.lianlian.api.ILianLianPayService;
import cn.qumiandan.pay.withdraw.api.IWithdrawCashService;
import cn.qumiandan.pay.withdraw.entity.WithdrawCash;
import cn.qumiandan.pay.withdraw.enums.WithdrawStatusEnum;
import cn.qumiandan.pay.withdraw.mapper.WithdrawMapper;
import cn.qumiandan.pay.withdraw.vo.QueryResponseParamsVO;
import cn.qumiandan.pay.withdraw.vo.QueryWithdrawCashParamsVO;
import cn.qumiandan.pay.withdraw.vo.SalemanLevelApplyWithdrawCaseResultVO;
import cn.qumiandan.pay.withdraw.vo.ShopApplyWithdrawCashResultVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashResultVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashVo;
import cn.qumiandan.payaccount.api.IBankCardService;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.BankCardVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.shop.vo.ShopDetailVO;
import cn.qumiandan.system.api.ISysPropertiesService;
import cn.qumiandan.system.enums.SysPropertiresEnums;
import cn.qumiandan.system.vo.SysPropertiesVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.enums.TradeDetailTypeEnum;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@Service(interfaceClass = IWithdrawCashService.class)
public class WithdrawCashImpl implements IWithdrawCashService {
	
	@Autowired
	private WithdrawMapper withdrawMapper;
	
	@Autowired
	private IPayAccountService payAccountService;
	
	@Autowired
	private ILianLianPayService lianLianPayService;
	
	@Autowired
	private ITradeDetailService tradeDetailService;
	
	@Reference
	private ISysPropertiesService sysPropertiesService;
	
	@Reference
	private IShopService shopService;
	
	@Reference
	private ISalemanService salemanService;
	
	@Autowired
	private IBankCardService bankCardService;
	
	
	/**
	 * 验证时限
	 * @param timeLimit
	 */
	private void checkWithDrawCashTimeLimit(Calendar now, String timeLimit) {
		String[] timeLimits = timeLimit.split("-");
		int start = Integer.parseInt(timeLimits[0]);
		int end = Integer.parseInt(timeLimits[1]);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		if (hour < start || hour > end) {
			throw new QmdException("此时段不能提现, 提现时间为:" + timeLimits[0] + "-" + timeLimits[1]);
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public ShopApplyWithdrawCashResultVO shopApplyCash(Long shopId, Long createId) {
		AssertUtil.isNull(shopId, "WithdrawCashImpl|shopApplyCash|传入参数shopId为空");
		AssertUtil.isNull(createId, "WithdrawCashImpl|shopApplyCash|传入参数createId为空");
		Calendar now = Calendar.getInstance();
		SysPropertiesVO timeLimitVo = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.WithdrawTimeLimit.getId());
		// 验证是否是提现时间
		if (Objects.nonNull(timeLimitVo) && StringUtils.isNotBlank(timeLimitVo.getValue())) {
			checkWithDrawCashTimeLimit(now, timeLimitVo.getValue());
		}
		
		PayAccountVO shopAccount = payAccountService.getPayAccountByShopId(shopId);
		if (Objects.isNull(shopAccount)) {
			throw new QmdException("申请提现失败, 店铺没有绑定账户");
		}
		
		SysPropertiesVO minAmountLimitVo = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.WithdrawAmountMinLimit.getId());
		// 验证提现最小金额
		if (Objects.nonNull(minAmountLimitVo) || StringUtils.isNotBlank(minAmountLimitVo.getValue())) {
			BigDecimal minAmountLimit = new BigDecimal(minAmountLimitVo.getValue());
			if (shopAccount.getSettBalance().compareTo(minAmountLimit) == -1) {
				throw new QmdException("申请提现失败, 账户可提现余额不足 最低" + minAmountLimit.divide(new BigDecimal(100)).toString() + "元");
			}
		} 
		
		WithdrawCash entity = doAddWithdrawCast(createId, now, shopAccount, TradeDetailTypeEnum.SHOPWITHDRAWCASH);
		// 拼接数据
		ShopDetailVO shop = shopService.getShopDetailById(shopId);
		ShopApplyWithdrawCashResultVO vo = new ShopApplyWithdrawCashResultVO();
		if (Objects.nonNull(shop)) {
			vo.setShopName(shop.getName());
		}
		vo.setShopId(shopId);
		vo.setMessage("申请成功, 正在审核");
		vo.setSuccess(true);
		vo.setWithdrawCashAmount(entity.getWithdrawalAmount());
		return vo;
	}

	/**
	 * 添加提现申请记录
	 * @param createId
	 * @param now
	 * @param shopAccount
	 * @return
	 */
	@Transactional(rollbackFor =  {QmdException.class, Exception.class})
	private WithdrawCash doAddWithdrawCast(Long createId, Calendar now, PayAccountVO account, TradeDetailTypeEnum type) {
		BankCardVO bankcard = bankCardService.getBankCardByAccountId(account.getId());
		if (Objects.isNull(bankcard)) {
			throw new QmdException("申请提现失败, 账户没有绑定银行卡");
		}
		BigDecimal fee = new BigDecimal(200);
		WithdrawCash entity = new WithdrawCash();
		entity.setFee(fee);
		entity.setCreateDate(now.getTime());
		entity.setCreateId(createId);
		entity.setAccountId(account.getId());
		entity.setBankNo(bankcard.getBankCardNo());
		entity.setCardHolder(bankcard.getBankCardHolder());
		entity.setWithdrawalAmount(account.getSettBalance());
		entity.setStatus(WithdrawStatusEnum.ApplyAuditing.getCode());
		if (!checkCUD(withdrawMapper.insert(entity))) {
			throw new QmdException("申请提现失败");
		}
		// 账户出账
		payAccountService.decreaseAccountBalanceAndSettBalanceById(account.getId(), account.getSettBalance());
		// 记录流水
		tradeDetailService.addTransferWithDrawCash(account.getId(), null, account.getSettBalance(), PayTypeEnum.LianLianPay.getCode(), now.getTime(), type);
		// 记录平台收取手续费
		tradeDetailService.addTransferOut(account.getId(), null, null, null, null, fee, now.getTime(), TradeDetailTypeEnum.PLATFORMWITHDRAWFEE);
		return entity;
	}

	@Override
	public List<ShopApplyWithdrawCashResultVO> shopOneKeyApplyCash(List<Long> shopIds, Long createId) {
		AssertUtil.isNull(shopIds, "WithdrawCashImpl|shopOneKeyApplyCash|传入参数shopIds为空");
		AssertUtil.isNull(createId, "WithdrawCashImpl|shopOneKeyApplyCash|传入参数createId为空");
		Calendar now = Calendar.getInstance();
		
		// 验证是否是提现时间
		SysPropertiesVO timeLimit = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.WithdrawTimeLimit.getId());
		if (Objects.nonNull(timeLimit) && StringUtils.isNotBlank(timeLimit.getValue())) {
			checkWithDrawCashTimeLimit(now, timeLimit.getValue());
		}
		
		List<ShopApplyWithdrawCashResultVO> resultList = Lists.newArrayListWithExpectedSize(shopIds.size());
		if (!CollectionUtils.isEmpty(shopIds)) {
			SysPropertiesVO minAmountLimitVo = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.WithdrawAmountMinLimit.getId());
			for (Long shopId : shopIds) {
				PayAccountVO shopAccount = payAccountService.getPayAccountByShopId(shopId);
				if (Objects.isNull(shopAccount)) {
					throw new QmdException("申请提现失败, 店铺没有绑定账户");
				}
				ShopApplyWithdrawCashResultVO vo = new ShopApplyWithdrawCashResultVO();
				if (Objects.nonNull(timeLimit) || StringUtils.isNotBlank(timeLimit.getValue())) {
					// 验证提现最小金额
					BigDecimal minAmountLimit = new BigDecimal(minAmountLimitVo.getValue());
					if (shopAccount.getSettBalance().compareTo(minAmountLimit) == -1) {
						vo.setShopId(shopId);
						vo.setSuccess(false);
						vo.setMessage("店铺可提现余额不足, 不能申请提现");
						resultList.add(vo);
						continue;
					}
				}
				
				WithdrawCash withdrawCash = doAddWithdrawCast(createId, now, shopAccount, TradeDetailTypeEnum.SHOPWITHDRAWCASH);
				vo.setShopId(shopId);
				vo.setSuccess(true);
				vo.setWithdrawCashAmount(withdrawCash.getWithdrawalAmount());
				vo.setMessage("申请成功, 正在审核");
				resultList.add(vo);
			}
		}
		
		// 拼接数据
		List<ShopBasicVO> shopList = shopService.getShopByManager(shopIds);
		if (!CollectionUtils.isEmpty(shopList) && !CollectionUtils.isEmpty(resultList)) {
			for (ShopApplyWithdrawCashResultVO result : resultList) {
				for (ShopBasicVO shop : shopList) {
					if (Objects.nonNull(shop) && shop.getId() == result.getShopId()) {
						result.setShopName(shop.getName());
					}
				}
			}
		}
		return resultList;
	}
	
	@Override
	public SalemanLevelApplyWithdrawCaseResultVO salemanLevelApplyCash(Long userId) {
		AssertUtil.isNull(userId, "WithdrawCashImpl|salemanLevelApplyCash|传入参数userId为空");
		SysPropertiesVO timeLimit = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.WithdrawTimeLimit.getId());
		if (Objects.isNull(timeLimit) || StringUtils.isBlank(timeLimit.getValue())) {
			throw new QmdException("申请提现失败, 系统异常");
		}
		// 验证是否是提现时间
		Calendar now = Calendar.getInstance();
		SysPropertiesVO timeLimitVo = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.WithdrawTimeLimit.getId());
		if (Objects.nonNull(timeLimitVo) && StringUtils.isNotBlank(timeLimitVo.getValue())) {
			checkWithDrawCashTimeLimit(now, timeLimitVo.getValue());
		}
		
		PayAccountVO account = payAccountService.getPayAccountByUserId(userId);
		if (Objects.isNull(account)) {
			throw new QmdException("申请提现失败, 提现人没有绑定账户");
		}
		
		SysPropertiesVO minAmountLimitVo = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.WithdrawAmountMinLimit.getId());
		// 验证提现最小金额
		if (Objects.nonNull(minAmountLimitVo) || StringUtils.isNotBlank(minAmountLimitVo.getValue())) {
			BigDecimal minAmountLimit = new BigDecimal(minAmountLimitVo.getValue());
			if (account.getSettBalance().compareTo(minAmountLimit) == -1) {
				throw new QmdException("申请提现失败, 账户可提现余额不足 最低" + minAmountLimit.divide(new BigDecimal(100)).toString() + "元");
			}
		}
		
		SalemanVO saleman = salemanService.getSalemanByUserId(userId);
		if (Objects.isNull(saleman)) {
			throw new QmdException("该账号不是业务员或代理,提现失败");
		}
		
		TradeDetailTypeEnum tradeType = null;
		switch (SalemanTypeEnums.getType(saleman.getType())) {
		case Saleman:
			tradeType = TradeDetailTypeEnum.SALEMANWITHDRAWCASH;
			break;
		case CountryAgent:
			tradeType = TradeDetailTypeEnum.COUNTRYWITHDRAWCASH;
			break;
		case CityAgent:
			tradeType = TradeDetailTypeEnum.CITYWITHDRAWCASH;
			break;
		case ProAgent:
			tradeType = TradeDetailTypeEnum.PROVINCEWITHDRAWCASH;
			break;
		default:
			throw new QmdException("该账号不是业务员或代理,提现失败");
		}
		
		WithdrawCash entity = doAddWithdrawCast(userId, now, account, tradeType);
		SalemanLevelApplyWithdrawCaseResultVO vo = new SalemanLevelApplyWithdrawCaseResultVO();
		vo.setSuccess(true);
		vo.setMessage("提现申请成功, 正在审核中");
		vo.setWithdrawCashAmount(entity.getWithdrawalAmount());
		return vo;
	}
	
	
	
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public WithdrawCashResultVO applyAudit(WithdrawCashVo withdrawCashVo) {		
		WithdrawCash withdrawCash = withdrawMapper.selectById(withdrawCashVo.getId());
		if(null==withdrawCash) {
			throw new QmdException("该提现申请不存在");
		}
		switch (withdrawCashVo.getStatus()) {
		case 0:
			WithdrawCash copyBean = CopyBeanUtil.copyBean(withdrawCashVo, WithdrawCash.class);	
			copyBean.setStatus(WithdrawStatusEnum.AuditUnPass.getCode());
			withdrawMapper.updateById(copyBean);
			return null;
		case 1:
			WithdrawCashResultVO withdraw = lianLianPayService.withdraw(withdrawCashVo.getId());
			return withdraw;
		default:
			log.error("体现审核参数错误，1：审核通过；0:审核未通过;status:"+withdrawCashVo.getStatus());
			throw new QmdException("体现审核参数错误");
		}
		

	}

	@Override
	public WithdrawCashVo payCallback(WithdrawCashVo withdrawCashVo) {
		WithdrawCash bean = CopyBeanUtil.copyBean(withdrawCashVo, WithdrawCash.class);
		withdrawMapper.updateById(bean);
		return withdrawCashVo;
	}

	@Override
	public WithdrawCashVo getWithdrawCashInfoById(Long id) {
		AssertUtil.isNull(id, "WithdrawCashImpl|getWithdrawCashInfoById|传入参数id为空");
		WithdrawCash info = withdrawMapper.selectById(id);
		if (Objects.nonNull(info)) {
			return CopyBeanUtil.copyBean(info, WithdrawCashVo.class);
		}
		return null;
	}
	
	/**
	 * 查询体现记录
	 */
	@Override
	public QueryResponseParamsVO queryWithdrawCash(QueryWithdrawCashParamsVO paramsVO ) {
		PageHelper.startPage(paramsVO.getPageNum(), paramsVO.getPageSize());
		List<WithdrawCashVo> withdrawCashs = withdrawMapper.queryWithdrawCash(paramsVO);
		if(ObjectUtils.isEmpty(withdrawCashs)) {
			return null;
		}
		 List<WithdrawCashVo> withdrawCashVos = CopyBeanUtil.copyList(withdrawCashs, WithdrawCashVo.class);
		PageInfo<WithdrawCashVo> pageInfo = new PageInfo<>(withdrawCashVos);
		//查询提现总金额
		List<WithdrawCashVo> allwithdrawCashs = withdrawMapper.queryWithdrawCash(paramsVO);
		BigDecimal sumWithdrawalAmount = new BigDecimal(0);
		for(WithdrawCashVo cash : allwithdrawCashs) {
			sumWithdrawalAmount = sumWithdrawalAmount.add(cash.getWithdrawalAmount());
		}
		QueryResponseParamsVO queryResponseParamsVO = new QueryResponseParamsVO();
		queryResponseParamsVO.setPageInfo(pageInfo);
		queryResponseParamsVO.setSumWithdrawalAmount(sumWithdrawalAmount);
		return queryResponseParamsVO;
	}
	
	/**
	 * 修改提现申请
	 */
	@Override
	public void updateWithdrawCashById(WithdrawCashVo withdrawCashVo) {
		WithdrawCash withdrawCash = CopyBeanUtil.copyBean(withdrawCashVo, WithdrawCash.class);
		int i = withdrawMapper.updateById(withdrawCash);
		if(i != 1) {
			log.error("修改提现申请失败,受影响行数不为1，withdrawCashId"+withdrawCashVo.getId());
			throw new QmdException("修改提现申请失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void manualWithdrawCash(Long id, Long operator) {
		AssertUtil.isNull(id, "WithdrawCashImpl|manualWithdrawCash|传入参数id为空");
		AssertUtil.isNull(id, "WithdrawCashImpl|manualWithdrawCash|传入参数operator为空");
		WithdrawCash withdraw = withdrawMapper.selectById(id);
		if (Objects.isNull(withdraw)) {
			throw new QmdException("没有找到提现申请信息");
		}
		
		Date now = new Date();
		withdraw.setPaymentDate(now);
		withdraw.setUpdateId(operator);
		withdraw.setUpdateDate(now);
		withdraw.setRemarkAudit("打款完成");
		withdraw.setStatus(WithdrawStatusEnum.ManualWithdraw.getCode());
		if (!checkCUD(withdrawMapper.updateById(withdraw))) {
			throw new QmdException("手动打款失败, 请联系管理员");
		}
	}
}
