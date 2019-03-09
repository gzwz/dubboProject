package cn.qumiandan.pay.cashback.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.cashback.api.ICashbackService;
import cn.qumiandan.pay.cashback.entity.Cashback;
import cn.qumiandan.pay.cashback.enums.CashbackStatusEnum;
import cn.qumiandan.pay.cashback.mapper.CashbackMapper;
import cn.qumiandan.pay.cashback.vo.CashbackVO;
import cn.qumiandan.pay.cashback.vo.QueryCashbackRequestParamsVO;
import cn.qumiandan.pay.cashback.vo.QueryCashbackResPonseParamsVO;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.ticket.api.IQualificationTicketService;
import cn.qumiandan.ticket.enums.TicketStatusEnums;
import cn.qumiandan.ticket.vo.QualificationTicketVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.enums.TradeDetailTypeEnum;
import cn.qumiandan.trade.enums.TradePayStatusEnum;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 返现管理实现类
 * @author lrj
 *
 */
@Slf4j
@Component
@Service(interfaceClass = ICashbackService.class)
public class CashbackServiceImpl implements ICashbackService{

	@Autowired
	private CashbackMapper cashbackMapper;
	
	@Reference
	private IQualificationTicketService ticketService;
	
	@Reference
	private IShopService shopService;
	
	@Reference
	private IUserService userService;
	
	@Autowired
	private IPayAccountService payAccountService ;
	
	@Reference
	private ITradeDetailService detailService;
	
	private QualificationTicketVO validationCouldCashback(String ticketId) {
		QualificationTicketVO ticketById = ticketService.getTicketById(ticketId);
		//查询资格券信息
		if(ticketById == null) {
			log.error("addCashback|资格券不存在,ticketId:"+ticketId);
			throw new QmdException("资格券不存在");
		}
		if(ticketById.getShopId() ==null ||(ticketById.getShopId() !=null && !ticketById.getStatus().equals(TicketStatusEnums.USED.getCode())) ) {
			log.error("addCashback|该资格券未使用结束,ticketId:"+ticketId);
			throw new QmdException("该资格券未使用结束");
		}
		//查询店铺账户营业额信息
		PayAccountVO payAccountByShopId = payAccountService.getPayAccountByShopId(ticketById.getShopId());
		if(payAccountByShopId == null) {
			log.error("addCashback|店铺账户不存在,shopId:"+ticketById.getShopId());
			throw new QmdException("店铺账户不存在");
		}
		if(payAccountByShopId.getBalance().compareTo(ticketById.getCashbackDoorsill()) == -1) {
			log.error("addCashback|店铺营业额未达到返现门槛,返现门槛:"+ticketById.getCashbackDoorsill()+";营业额："+payAccountByShopId.getBalance());
			throw new QmdException("店铺营业额未达到返现门槛");
		}
		return ticketById;
	}
	
	/**
	 * 申请返现
	 */
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public CashbackVO addCashback(CashbackVO cashbackVO) {
		AssertUtil.isNull(cashbackVO, "添加返现信息参数为空");
		AssertUtil.isNull(cashbackVO.getTicketId(), "添加返现信息资格券id为空");
		Integer selectCount = cashbackMapper.selectCount(new QueryWrapper<Cashback>().eq("ticket_id", cashbackVO.getTicketId()));
		if(selectCount>0) {
			log.error("addCashback|该券已申请过返现,ticketId:"+cashbackVO.getTicketId());
			throw new QmdException("该券已申请过返现");
		}
		QualificationTicketVO ticketById = validationCouldCashback(cashbackVO.getTicketId());
		Date now = new Date();
		cashbackVO.setCreateDate(now);
		cashbackVO.setShopId(ticketById.getShopId());
		cashbackVO.setActualAmount(ticketById.getReturnMoney());
		Cashback cashback = CopyBeanUtil.copyBean(cashbackVO, Cashback.class);
		int i = cashbackMapper.insert(cashback);
		if(i != 1) {
			log.error("addCashback|申请返现失败，受影响行数不为1，受影响行数i:"+i);
			throw new QmdException("申请返现失败");
		}
		ticketService.setTicketStatus(ticketById.getId(), TicketStatusEnums.Returning.getCode());
		cashbackVO.setId(cashback.getId());
		return cashbackVO;
	}

	/**
	 * 修改返现申请
	 */
	@Override
	public void updateCashback(CashbackVO cashbackVO) {
		AssertUtil.isNull(cashbackVO, "修改返现信息参数为空");
		AssertUtil.isNull(cashbackVO.getId(), "修改返现信息id为空");
		Cashback cashback = CopyBeanUtil.copyBean(cashbackVO, Cashback.class);
		cashback.setUpdateDate(new Date());
		int i = cashbackMapper.updateById(cashback);
		if(i != 1) {
			log.error("addCashback|修改返现申请失败，受影响行数不为1，受影响行数i:"+i);
			throw new QmdException("修改返现申请失败");
		}
	}

	/**
	 * 根据条件查询返现记录
	 */
	@Override
	public PageInfo<QueryCashbackResPonseParamsVO> queryCashback(QueryCashbackRequestParamsVO params) {
		QueryWrapper<Cashback> queryWrapper = new QueryWrapper<>();
		if( ObjectUtils.isEmpty(params.getStatusList())) {
			queryWrapper.in("status", params.getStatusList());
		}
		if(params.getStartCreateDate() != null) {
			queryWrapper.ge("create_date", params.getStartCreateDate());
		}
		if(params.getEndCreateDate() != null) {
			queryWrapper.lt("create_date", params.getEndCreateDate());
		}
		queryWrapper.orderByDesc("create_date");
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		//查询提现信息
		List<Cashback> cashbacks = cashbackMapper.selectList(queryWrapper);
		if(ObjectUtils.isEmpty(cashbacks)) {
			return null;
		}
		//申请人id集合
		List<Long> userIdList = new ArrayList<>();
		//资格券id集合
		List<String> ticketIdList = new ArrayList<>();
		//店铺id集合
		List<Long> shopIdList = new ArrayList<>();
		for(Cashback cashback:cashbacks) {
			userIdList.add(cashback.getCreateId());
			ticketIdList.add(cashback.getTicketId());
			shopIdList.add(cashback.getShopId());
		}
		//查询用户信息
		List<UserVO> userByUserIds = new ArrayList<>();
		if(!ObjectUtils.isEmpty(userIdList)) {
			userByUserIds = userService.getUserByUserIds(userIdList);
		}
		//查询店铺信息
		List<ShopBasicVO> shopByManager = new ArrayList<>();
		if(!ObjectUtils.isEmpty(shopIdList)) {
			shopByManager = shopService.getShopByManager(shopIdList);
		}
		//查询资格券信息
		List<QualificationTicketVO> ticketByIdList = new ArrayList<>();
		if(!ObjectUtils.isEmpty(ticketIdList)) {
			ticketByIdList = ticketService.getTicketByIdList(ticketIdList);
		}
		//组装数据
		List<QueryCashbackResPonseParamsVO> ponseParamsVOs = CopyBeanUtil.copyList(cashbacks, QueryCashbackResPonseParamsVO.class);
		for(QueryCashbackResPonseParamsVO vo : ponseParamsVOs) {
			//组装用户信息
			if(!ObjectUtils.isEmpty(userByUserIds)) {
				for(UserVO userVO : userByUserIds) {
					if(userVO.getId().equals(vo.getCreateId())) {
						vo.setUserName(userVO.getUserName());
					}
				}
			}
			//组装店铺信息
			if(!ObjectUtils.isEmpty(shopByManager)) {
				for(ShopBasicVO shopBasicVO : shopByManager) {
					if(shopBasicVO.getId().equals(vo.getShopId())) {
						vo.setShopName(shopBasicVO.getName());
						vo.setShopLogo(shopBasicVO.getLogo());
					}
				}
			}
			//组装资格券信息
			if(!ObjectUtils.isEmpty(ticketByIdList)) {
				for(QualificationTicketVO ticketVO : ticketByIdList) {
					if(ticketVO.getId().equals(vo.getTicketId())) {
						vo.setQualificationTicketVO(ticketVO);
					}
				}
			}
			
		}
		PageInfo<QueryCashbackResPonseParamsVO> pageInfo = new PageInfo<>(ponseParamsVOs);
		return pageInfo;
	}

	/**
	 * 审核返现
	 */
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void auditCashback(CashbackVO cashbackVO) {
		AssertUtil.isNull(cashbackVO.getId(), "auditCashback|返现记录id为空，id"+cashbackVO.getId());
		AssertUtil.isNull(cashbackVO.getStatus(), "auditCashback|返现记录状态为空,status:"+cashbackVO.getStatus());
		Cashback cashback = cashbackMapper.selectById(cashbackVO.getId());
		if(cashback == null) {
			log.error("auditCashback|返现记录为空,cashbackId:"+cashbackVO.getId());
			throw new QmdException("返现记录为空");
		}
		QualificationTicketVO ticket = validationCouldCashback(cashback.getTicketId());
		cashback.setStatus(cashbackVO.getStatus());
		int i = cashbackMapper.updateById(cashback);
		if( i != 1) {
			log.error("auditCashback|审核返现失败,受影响行数不为1，受影响行数i::"+i);
			throw new QmdException("审核返现失败");
		}
		if(cashbackVO.getStatus().equals(CashbackStatusEnum.FinishedWithdraw.getCode())) {
			PayAccountVO payAccount = payAccountService.getPayAccountByUserId(cashback.getCreateId());
			if(payAccount == null) {
				log.error("auditCashback|申请返现的用户账户不存在,userId:"+cashback.getCreateId());
				throw new QmdException("申请返现的用户账户不存在");
			}
			payAccountService.increaseBalanceAndAccountBalance(payAccount.getId(), cashback.getActualAmount());
			
			ticketService.setTicketStatus(ticket.getId(), TicketStatusEnums.Returned.getCode());
			
			// 打印流水日志
			TradeDetailVO vo = new TradeDetailVO();
			vo.setAccountInId(payAccount.getId());
			vo.setAmount(cashback.getActualAmount());
			// 本次申请的id
			vo.setSerialNo(cashback.getId().toString());
			vo.setStatus(TradePayStatusEnum.PAYED.getCode());
			vo.setCreateDate(new Date());
			vo.setType(TradeDetailTypeEnum.SALEMANCASHBACK.getCode());
			detailService.addTradeDetail(vo);
		}
		
	}

}
