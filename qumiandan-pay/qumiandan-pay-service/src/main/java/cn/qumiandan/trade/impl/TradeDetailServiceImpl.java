package cn.qumiandan.trade.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.enums.PayTypeEnum;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.entity.TradeDetail;
import cn.qumiandan.trade.enums.TradeDetailTypeEnum;
import cn.qumiandan.trade.enums.TradePayStatusEnum;
import cn.qumiandan.trade.enums.TradeTypeEnums;
import cn.qumiandan.trade.mapper.TradeDetailMapper;
import cn.qumiandan.trade.vo.QueryTradeDetailParamsVO;
import cn.qumiandan.trade.vo.QueryTradeDetailResponseParams;
import cn.qumiandan.trade.vo.TradeAndStatisticVO;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.trade.vo.TradeResultVO;
import cn.qumiandan.trade.vo.TradeStatisticsVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = ITradeDetailService.class)
public class TradeDetailServiceImpl implements ITradeDetailService {

	@Autowired
	private TradeDetailMapper tradeDetailMapper;

	@Autowired
	private IPayAccountService payAccountService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TradeDetailVO addTradeDetail(TradeDetailVO vo) {
		AssertUtil.isNull(vo, "TradeDetailServiceImpl|addTradeDetail|传入参数vo为空");
		TradeDetail entity = CopyBeanUtil.copyBean(vo, TradeDetail.class);
		if (!checkCUD(tradeDetailMapper.insert(entity))) {
			log.error("创建支付流水失败|accountInId:" + vo.getAccountInId() + "|accountOutId:" + vo.getAccountOutId());
			throw new QmdException("创建支付流水失败");
		}
		vo.setId(entity.getId());
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateTradeDetail(TradeDetailVO vo) {
		AssertUtil.isNull(vo, "TradeDetailServiceImpl|updateTradeDetail|传入参数vo为空");
		TradeDetail entity = CopyBeanUtil.copyBean(vo, TradeDetail.class);
		if (!checkCUD(tradeDetailMapper.updateById(entity))) {
			log.error("更新支付流水失败|tradeId:" + vo.getId());
			throw new QmdException("更新支付流水失败");
		}
	}

	@Override
	public TradeDetailVO getTradeDetailById(Long id) {
		AssertUtil.isNull(id, "TradeDetailServiceImpl|getTradeDetailById|传入id为空");
		TradeDetail entity = tradeDetailMapper.selectById(id);
		if (Objects.nonNull(entity)) {
			return CopyBeanUtil.copyBean(entity, TradeDetailVO.class);
		}
		return null;
	}

	@Override
	public TradeDetailVO addTransferIn(Long accountInId, Long accountOutId, String serialNo, String gameSerialNo,
			String outTradeNo, BigDecimal increaseValue, Date operateDate, TradeDetailTypeEnum type) {
		AssertUtil.isNull(accountInId, "PayAccountServiceImpl|addTransferIn|传入参数payAccountId为空");
		// AssertUtil.isNull(serialNo,
		// "PayAccountServiceImpl|addTransferIn|传入参数serialNo为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|addTransferIn|传入参数increaseValue为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|addTransferIn|传入参数operateDate为空");
		AssertUtil.isNull(type, "PayAccountServiceImpl|addTransferIn|传入参数type为空");

		TradeDetailVO trade = new TradeDetailVO();
		trade.setAccountInId(accountInId);
		trade.setAccountOutId(accountOutId);
		trade.setSerialNo(serialNo);
		trade.setGameSerialNo(gameSerialNo);
		trade.setAmount(increaseValue);
		trade.setCallbackAmount(increaseValue);
		trade.setTradeType(TradeTypeEnums.TRANSFERIN.getCode());
		trade.setPayChannel(PayTypeEnum.PlatformPay.getCode());
		trade.setType(type.getCode());
		trade.setProductName(type.getName());
		trade.setThirdTradeName("平台内部转账");
		trade.setOutTradeNo(outTradeNo);
		trade.setCurrency("RMB");
		trade.setStatus(TradePayStatusEnum.PAYED.getCode());
		trade.setCreateDate(operateDate);
		trade.setPayDate(operateDate);
		return addTradeDetail(trade);
	}

	@Override
	public TradeDetailVO addTransferOut(Long accountInId, Long accountOutId, String serialNo, String gameSerialNo,
			String outTradeNo, BigDecimal decreaseValue, Date operateDate, TradeDetailTypeEnum type) {
		AssertUtil.isNull(accountInId, "PayAccountServiceImpl|addTransferOut|传入参数payAccountId为空");
		// AssertUtil.isNull(serialNo,
		// "PayAccountServiceImpl|addTransferOut|传入参数serialNo为空");
		AssertUtil.isNull(decreaseValue, "PayAccountServiceImpl|addTransferOut|传入参数decreaseValue为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|addTransferOut|传入参数operateDate为空");
		AssertUtil.isNull(type, "PayAccountServiceImpl|addTransferOut|传入参数type为空");

		TradeDetailVO trade = new TradeDetailVO();
		trade.setAccountInId(accountInId);
		trade.setAccountOutId(accountOutId);
		trade.setSerialNo(serialNo);
		trade.setGameSerialNo(gameSerialNo);
		// 转出 统一转成负数
		trade.setAmount(decreaseValue.negate());
		trade.setCallbackAmount(decreaseValue.negate());
		trade.setTradeType(TradeTypeEnums.TRANSFEROUT.getCode());
		trade.setPayChannel(PayTypeEnum.PlatformPay.getCode());
		trade.setType(type.getCode());
		trade.setProductName(type.getName());
		trade.setThirdTradeName("平台内部转账");
		trade.setOutTradeNo(outTradeNo);
		trade.setCurrency("RMB");
		trade.setStatus(TradePayStatusEnum.PAYED.getCode());
		trade.setCreateDate(operateDate);
		trade.setPayDate(operateDate);
		return addTradeDetail(trade);
	}

	@Override
	public TradeDetailVO addTransferOutRefund(Long accountInId, Long accountOutId, String serialNo, String gameSerialNo,
			String outTradeNo, BigDecimal decreaseValue, String payChannel, Date operateDate,
			TradeDetailTypeEnum type) {
		AssertUtil.isNull(accountInId, "PayAccountServiceImpl|addTransferOutRefund|传入参数payAccountId为空");
		AssertUtil.isNull(serialNo, "PayAccountServiceImpl|addTransferOutRefund|传入参数serialNo为空");
		AssertUtil.isNull(decreaseValue, "PayAccountServiceImpl|addTransferOutRefund|传入参数decreaseValue为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|addTransferOutRefund|传入参数operateDate为空");

		TradeDetailVO trade = new TradeDetailVO();
		trade.setAccountInId(accountInId);
		trade.setAccountOutId(accountOutId);
		trade.setSerialNo(serialNo);
		trade.setGameSerialNo(gameSerialNo);
		// 转出 统一转成负数
		trade.setAmount(decreaseValue.negate());
		trade.setCallbackAmount(decreaseValue.negate());
		trade.setTradeType(TradeTypeEnums.TRANSFEROUT.getCode());
		trade.setType(type.getCode());
		trade.setPayChannel(payChannel);
		trade.setProductName(type.getName());
		trade.setThirdTradeName("退款");
		trade.setOutTradeNo(outTradeNo);
		trade.setCurrency("RMB");
		trade.setStatus(TradePayStatusEnum.REFUND.getCode());
		trade.setCreateDate(operateDate);
		trade.setPayDate(operateDate);
		return addTradeDetail(trade);
	}

	@Override
	public TradeDetailVO addTransferWithDrawCash(Long accountInId, Long accountOutId, BigDecimal decreaseValue,
			String payChannel, Date operateDate, TradeDetailTypeEnum type) {
		AssertUtil.isNull(accountInId, "PayAccountServiceImpl|addTransferOutRefund|传入参数payAccountId为空");
		AssertUtil.isNull(decreaseValue, "PayAccountServiceImpl|addTransferOutRefund|传入参数decreaseValue为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|addTransferOutRefund|传入参数operateDate为空");
		TradeDetailVO trade = new TradeDetailVO();
		trade.setAccountInId(accountInId);
		trade.setAccountOutId(accountOutId);
		// 转出 统一转成负数
		trade.setAmount(decreaseValue.negate());
		trade.setCallbackAmount(decreaseValue.negate());
		trade.setTradeType(TradeTypeEnums.WITHDRAW.getCode());
		trade.setType(type.getCode());
		trade.setPayChannel(payChannel);
		trade.setProductName(type.getName());
		trade.setThirdTradeName("提现");
		trade.setCurrency("RMB");
		trade.setStatus(TradePayStatusEnum.PAYED.getCode());
		trade.setCreateDate(operateDate);
		trade.setPayDate(operateDate);
		return addTradeDetail(trade);
	}

	@Override
	public TradeResultVO getTradeResult(Long tradeId) {
		AssertUtil.isNull(tradeId, "TradeDetailServiceImpl|isPayTradeSuccess|传入参数tradeId为空");
		TradeResultVO tradeResult = tradeDetailMapper.getTradeResult(tradeId);
		return tradeResult;
	}

	@Override
	public TradeDetailVO getTradeDetailByOrderIdAndType(String orderId, TradeDetailTypeEnum type) {
		AssertUtil.isNull(orderId, "TradeDetailServiceImpl|getTradeDetailByOrderId|传入参数orderId为空");
		AssertUtil.isNull(type, "TradeDetailServiceImpl|getTradeDetailByOrderId|传入参数type为空");
		TradeDetail trade = tradeDetailMapper.selectOne(new QueryWrapper<TradeDetail>().eq("serial_no", orderId)
				.eq("type", type.getCode()).eq("status", TradePayStatusEnum.PAYED.getCode()));
		if (Objects.nonNull(trade)) {
			return CopyBeanUtil.copyBean(trade, TradeDetailVO.class);
		}
		return null;
	}

	@Override
	public TradeDetailVO getPayedOrderTradeDetail(String orderId) {
		AssertUtil.isNull(orderId, "TradeDetailServiceImpl|getTradeDetailByOrderId|传入参数orderId为空");
		TradeDetail trade = tradeDetailMapper.selectOne(new QueryWrapper<TradeDetail>().eq("serial_no", orderId)
				.in("type",
						Lists.newArrayList(TradeDetailTypeEnum.SHOPORDER.getCode(),
								TradeDetailTypeEnum.SHOPORDERVT.getCode()))
				.eq("status", TradePayStatusEnum.PAYED.getCode()));
		if (Objects.nonNull(trade)) {
			return CopyBeanUtil.copyBean(trade, TradeDetailVO.class);
		}
		return null;
	}

	/**
	 * 查询流水
	 * 
	 * @param paramsVO
	 * @return
	 */
	@Override
	public TradeAndStatisticVO queryTradeDetail(QueryTradeDetailParamsVO paramsVO) {
		QueryWrapper<TradeDetail> queryWrapper = new QueryWrapper<>();
		if (paramsVO.getUserId() != null) {
			PayAccountVO payAccountByUserId = payAccountService.getPayAccountByUserId(paramsVO.getUserId());
			if (payAccountByUserId != null) {
				queryWrapper.eq("account_in_id", payAccountByUserId.getId());

			}
		}
		if (paramsVO.getAccountId() != null) {
			queryWrapper.eq("account_in_id", paramsVO.getAccountId());
		}
		if (paramsVO.getAccountIds() != null) {
			queryWrapper.in("account_in_id", paramsVO.getAccountIds());
		}
		if (!ObjectUtils.isEmpty(paramsVO.getStatusList())) {
			queryWrapper.in("status", paramsVO.getStatusList());
		}
		if (!ObjectUtils.isEmpty(paramsVO.getTypeList())) {
			queryWrapper.in("type", paramsVO.getTypeList());
		}
		if (!ObjectUtils.isEmpty(paramsVO.getTradeTypeList())) {
			queryWrapper.in("trade_type", paramsVO.getTradeTypeList());
		}
		if (paramsVO.getStartCreateDate() != null) {
			queryWrapper.ge("create_date", paramsVO.getStartCreateDate());
		}
		if (paramsVO.getEndCreateDate() != null) {
			queryWrapper.lt("create_date", paramsVO.getEndCreateDate());
		}
		queryWrapper.orderByDesc("create_date");
		PageHelper.startPage(paramsVO.getPageNum(), paramsVO.getPageSize());
		List<TradeDetail> tradeDetailList = tradeDetailMapper.selectList(queryWrapper);
		// 计算统计
		List<TradeDetail> tradeDetailList2 = tradeDetailMapper.selectList(queryWrapper);
		//流水数量
		long count = PageHelper.count(()->tradeDetailMapper.selectList(queryWrapper));
		BigDecimal inAccount = new BigDecimal(0);
		BigDecimal outAccount = new BigDecimal(0);
		if(!ObjectUtils.isEmpty(tradeDetailList2)) {
			for (TradeDetail tradeDetail : tradeDetailList2) {
				if (tradeDetail.getAmount().compareTo(new BigDecimal(0)) == 1) {
					inAccount = inAccount.add(tradeDetail.getAmount());
				}
				if (tradeDetail.getAmount().compareTo(new BigDecimal(0)) == -1) {
					outAccount = outAccount.add(tradeDetail.getAmount());
				}
			}
		}
		
		// 设置返回参数
		TradeAndStatisticVO andStatisticVO = new TradeAndStatisticVO();
		PageInfo<QueryTradeDetailResponseParams> info = new PageInfo<>();
		andStatisticVO.setInAccount(inAccount);
		andStatisticVO.setOutAccount(outAccount);
		if (!ObjectUtils.isEmpty(tradeDetailList)) {
			List<QueryTradeDetailResponseParams> tradeDetailVOList = CopyBeanUtil.copyList(tradeDetailList,
					QueryTradeDetailResponseParams.class);
			info.setList(tradeDetailVOList);
			andStatisticVO.setDetailResponseParams(info);
		}
		info.setTotal(count);
		return andStatisticVO;
	}

	/**
	 * 用户账户查询流水
	 * 
	 * @param paramsVO
	 * @return
	 */
	@Override
	public TradeAndStatisticVO userQueryTradeDetail(QueryTradeDetailParamsVO paramsVO) {
		AssertUtil.isNull(paramsVO.getUserId(), "TradeDetailServiceImpl|userQueryTradeDetail|传入参数userId为空");
		PayAccountVO payAccountByUserId = payAccountService.getPayAccountByUserId(paramsVO.getUserId());
		if (payAccountByUserId == null) {
			log.error("该用户账户不存在|userId:" + paramsVO.getUserId());
			throw new QmdException("该用户账户不存在");
		}
		paramsVO.setAccountId(payAccountByUserId.getId());
		return queryTradeDetail(paramsVO);
	}

	/**
	 * 查询流水统计
	 * 
	 * @param accountIds
	 * @return
	 */
	@Override
	public TradeStatisticsVO queryTradeStatistics(List<Long> accountIds) {
		TradeStatisticsVO statisticsVO = new TradeStatisticsVO();
		statisticsVO.setInAccount(tradeDetailMapper.getInAccount(accountIds));
		statisticsVO.setOutAccount(tradeDetailMapper.getOutAccount(accountIds));
		return statisticsVO;
	}

}
