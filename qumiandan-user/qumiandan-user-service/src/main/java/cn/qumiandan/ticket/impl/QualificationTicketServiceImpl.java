package cn.qumiandan.ticket.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.ticket.api.IQualificationTicketService;
import cn.qumiandan.ticket.api.ITicketUseRecordService;
import cn.qumiandan.ticket.entity.QualificationTicket;
import cn.qumiandan.ticket.enums.TicketStatusEnums;
import cn.qumiandan.ticket.mapper.QualificationMapper;
import cn.qumiandan.ticket.vo.AgentQueryTicketByUserIdVO;
import cn.qumiandan.ticket.vo.QualificationTicketVO;
import cn.qumiandan.ticket.vo.QueryTicketParamsVO;
import cn.qumiandan.ticket.vo.TicketNumVO;
import cn.qumiandan.ticket.vo.TicketUseRecordVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 资格券管理实现类
 * 
 * @author wlz
 *
 */
@Slf4j
@Component
@Service(interfaceClass = IQualificationTicketService.class)
public class QualificationTicketServiceImpl extends ServiceImpl<QualificationMapper, QualificationTicket>
		implements IQualificationTicketService {

	@Autowired
	private QualificationMapper mapper;
	@Autowired
	private ITicketUseRecordService ticketUseRecordService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ISalemanService salemanService;
	@Reference
	private IPayAccountService payAccountService;
	

	@Override
	public void delQualificationTicketByIds(Set<String> ids) {
		AssertUtil.isNull(ids, "需要删除的id集合不能为空");
		List<QualificationTicket> dellist = mapper.selectList(new QueryWrapper<QualificationTicket>().in("id", ids));
		for (QualificationTicket ticket : dellist) {
			if (!TicketStatusEnums.UNUSE.getCode().equals(ticket.getStatus())) {
				throw new QmdException("资格券："+ticket.getName()+":"+ticket.getId()+" 已被使用或者删除,不能删除！");
			}
		}
		int batchIds = mapper.update(new QualificationTicket(), new UpdateWrapper<QualificationTicket>()
				.in("id", ids).set("status", TicketStatusEnums.Deleted.getCode()));
		if (batchIds != ids.size()) {
			throw new QmdException("实际删除数量和需要删除数量不一致，请联系管理员！");
		}
		
	}
	
	@Override
	public List<QualificationTicketVO> createTicket(Long num, QualificationTicketVO param) {
		AssertUtil.isNull(num, "创建条数不能为空");
		AssertUtil.isNull(param, "参数不能为空");
		List<QualificationTicketVO> result = null;
		SalemanVO salemanVO = salemanService.getSalemanByUserId(param.getUserId());
		if (ObjectUtils.isEmpty(salemanVO)) {
			throw new QmdException("该业务员不存在");
		} else if (0 != salemanVO.getParentId()) {
			throw new QmdException("该业务员存在省市代理，无法发放资格券");
		} else {
			List<QualificationTicket> entityList = new ArrayList<QualificationTicket>();
			for (int i = 0; i < num; i++) {
				QualificationTicket entity = CopyBeanUtil.copyBean(param, QualificationTicket.class);
				entity.setCreateDate(new Date());
				entity.setStatus(StatusEnum.normal.getCode());
				entityList.add(entity);
			}
			saveBatch(entityList);
			result = CopyBeanUtil.copyList(entityList, QualificationTicketVO.class);
		}

		return result;
	}

	@Override
	public QualificationTicketVO getTicketById(String ticketId) {
		AssertUtil.isNull(ticketId, "资格券id参数不能为空");
		QualificationTicket ticket = this.getById(ticketId);
		if (null == ticket) {
			return null;
		}
		QualificationTicketVO bean = CopyBeanUtil.copyBean(ticket, QualificationTicketVO.class);
		//判断是否达到返现门槛
		if(null == bean.getShopId()) {
			bean.setCouldCashBack(false);
			return bean;
		}
		PayAccountVO payAccountByShopId = payAccountService.getPayAccountByShopId(bean.getShopId());
		if(null == payAccountByShopId.getShopId() 
				|| payAccountByShopId.getBalance().compareTo(bean.getCashbackDoorsill()) == -1) {
			bean.setCouldCashBack(false);
			return bean;
		}
		if(bean.getStatus().equals(TicketStatusEnums.USED.getCode())) {
			bean.setCouldCashBack(true);
		}
		return bean;
	}

	/**
	 * 根据用户id查询资格券
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	@Override
	public List<QualificationTicketVO> getTicketByUserId(Long userId, Byte status) {
		QueryWrapper<QualificationTicket> queryWrapper = new QueryWrapper<QualificationTicket>().eq("user_id", userId);
		if (status != null) {
			queryWrapper.eq("status", status);
		}
		else {
			queryWrapper.in("status", Lists.newArrayList(TicketStatusEnums.UNUSE.getCode(),
					TicketStatusEnums.USING.getCode(),
					TicketStatusEnums.USED.getCode(),
					TicketStatusEnums.Returning.getCode(),
					TicketStatusEnums.Returned.getCode()));
		}
		queryWrapper.orderByDesc("status");
		queryWrapper.orderByDesc("create_date");
		List<QualificationTicket> list = mapper.selectList(queryWrapper);
		if (list.isEmpty()) {
			return null;
		}
		List<QualificationTicketVO> qualificationTicketList = CopyBeanUtil.copyList(list, QualificationTicketVO.class);
		setCouldCashback(qualificationTicketList);
		return qualificationTicketList;
	}

	/**
	 * 根据用户id查询资格券（分页）
	 * 
	 * @param userId
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<QualificationTicketVO> getTicketPageByUserId(Long userId, Byte status, int pageNum, int pageSize) {

		QueryWrapper<QualificationTicket> queryWrapper = new QueryWrapper<QualificationTicket>().eq("user_id", userId);
		if (status != null) {
			queryWrapper.eq("status", status);
		}else {
			queryWrapper.in("status", Lists.newArrayList(TicketStatusEnums.UNUSE.getCode(),
					TicketStatusEnums.USING.getCode(),
					TicketStatusEnums.USED.getCode(),
					TicketStatusEnums.Returning.getCode(),
					TicketStatusEnums.Returned.getCode()));
		}
		Page<QualificationTicket> startPage = PageHelper.startPage(pageNum, pageSize);
		List<QualificationTicket> list = mapper.selectList(queryWrapper);
		if (list.isEmpty()) {
			return null;
		}
		List<QualificationTicketVO> qualificationTicketList = CopyBeanUtil.copyList(list, QualificationTicketVO.class);
		//设置是否达到返现参数值
		setCouldCashback(qualificationTicketList);
		PageInfo<QualificationTicketVO> pageInfo = new PageInfo<QualificationTicketVO>(qualificationTicketList);
		pageInfo.setTotal(startPage.getTotal());
		return pageInfo;
	}

	/**
	 * 资格券转让(单个转让），mobile为接受转让者手机号
	 * 
	 * @param ticketId
	 * @param targetMobile
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public Boolean makeOverSingleTicket(String ticketId, String targetMobile) {
		// 查看资格券是否存在
		QualificationTicket qualificationTicket = mapper.selectById(ticketId);
		if (qualificationTicket == null) {
			throw new QmdException("该资格券不存在");
		}
		if(!qualificationTicket.getStatus().equals(TicketStatusEnums.UNUSE.getCode())) {
			throw new QmdException("只有未使用的资格券可转让");
		}
		// 判断用户是否能接受资格券
		Long userId = validatUserqualification(targetMobile);
		Long fromUserId = qualificationTicket.getUserId();
		qualificationTicket.setUserId(userId);
		qualificationTicket.setUpdateDate(new Date());
		// 修改资格券表记录
		boolean updateFlag = updateById(qualificationTicket);
		// 新增资格券转让记录
		TicketUseRecordVO ticketUseRecord = new TicketUseRecordVO();
		ticketUseRecord.setTicketId(ticketId);
		ticketUseRecord.setFromUserId(fromUserId);
		ticketUseRecord.setCurrentUserId(userId);
		ticketUseRecord.setCreateDate(qualificationTicket.getUpdateDate());
		boolean addFlag = ticketUseRecordService.addTicketUseRecord(ticketUseRecord);
		if (!updateFlag || !addFlag) {
			throw new QmdException("资格券转让失败");
		}
		return updateFlag && addFlag;
	}

	/**
	 * 资格券转让(多条转让），mobile为接受转让者手机号
	 * 
	 * @param ticketIdSet
	 * @param targetMobile
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public Boolean makeOverBatchTicket(Set<String> ticketIdSet, String targetMobile) {
		// 查看ticketIdSet中所有资格券是否存在
		List<QualificationTicket> qualificationTicketList = mapper
				.selectList(new QueryWrapper<QualificationTicket>().in("id", ticketIdSet));
		if (qualificationTicketList == null || qualificationTicketList.size() != ticketIdSet.size()) {
			throw new QmdException("资格券信息不完整");
		}
		for(QualificationTicket qualificationTicket : qualificationTicketList) {
			if(!qualificationTicket.getStatus().equals(TicketStatusEnums.UNUSE.getCode())) {
				throw new QmdException("只有未使用的资格券可转让");
			}
		}
		// 判断用户是否能接受资格券
		Long userId = validatUserqualification(targetMobile);
		Long fromUserId = qualificationTicketList.get(0).getUserId();
		// 批量修改资格券表记录
		Date date = new Date();
		for (QualificationTicket entity : qualificationTicketList) {
			entity.setUserId(userId);
			entity.setUpdateDate(date);
		}
		boolean updateFlag = updateBatchById(qualificationTicketList);
		// 批量插入转让记录
		List<TicketUseRecordVO> ticketUseRecordList = new ArrayList<TicketUseRecordVO>();
		for (String ticketId : ticketIdSet) {
			TicketUseRecordVO ticketUseRecord = new TicketUseRecordVO();
			ticketUseRecord.setTicketId(ticketId);
			ticketUseRecord.setFromUserId(fromUserId);
			ticketUseRecord.setCurrentUserId(userId);
			ticketUseRecord.setCreateDate(date);
			ticketUseRecordList.add(ticketUseRecord);
		}
		boolean addFlag = ticketUseRecordService.batchAddTicketUseRecord(ticketUseRecordList);
		if (!updateFlag || !addFlag) {
			throw new QmdException("资格券转让失败");
		}
		return updateFlag && addFlag;
	}

	/**
	 * 判断用户是否有可用的券,是返回true,否返回false
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public Boolean hasAvailableTicket(Long userId) {
		int count = mapper.selectCount(new QueryWrapper<QualificationTicket>().eq("user_id", userId).eq("status",
				TicketStatusEnums.UNUSE.getCode()));
		if (count == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 修改资格券的状态为使用中
	 * 
	 * @param ticketId
	 * @param shopId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public Integer setTicketStatusToUsing(String ticketId, Long shopId) {
		QualificationTicket qualificationTicket = mapper.selectById(ticketId);
		if (qualificationTicket == null) {
			throw new QmdException("该资格券不存在");
		}
		if (!qualificationTicket.getStatus().equals(TicketStatusEnums.UNUSE.getCode())) {
			throw new QmdException("创建店铺时，状态为未使用的资格券才可用来创建店铺");
		}
		qualificationTicket.setShopId(shopId);
		qualificationTicket.setStatus(TicketStatusEnums.USING.getCode());
		int i = mapper.updateById(qualificationTicket);
		if (i != 1) {
			throw new QmdException("使用资格券失败");
		}
		return i;
	}

	/**
	 * 修改资格券的状态为使用结束
	 * 
	 * @param ticketId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public Integer setTicketStatusToUsed(String ticketId) {
		QualificationTicket qualificationTicket = mapper.selectById(ticketId);
		if (qualificationTicket == null) {
			throw new QmdException("该资格券不存在");
		}
		if (!qualificationTicket.getStatus().equals(TicketStatusEnums.USING.getCode())) {
			throw new QmdException("创建店铺时资格券为使用中状态，创建成功才能将该资格券状态改为使用结束");
		}
		qualificationTicket.setStatus(TicketStatusEnums.USED.getCode());
		int i = mapper.updateById(qualificationTicket);
		if (i != 1) {
			throw new QmdException("使用资格券失败");
		}
		return i;
	}

	/**
	 * 修改资格券的状态为未使用
	 * 
	 * @param ticketId
	 * @param shopId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public Integer setTicketStatusToUnuse(String ticketId) {
		QualificationTicket qualificationTicket = mapper.selectById(ticketId);
		if (qualificationTicket == null) {
			throw new QmdException("该资格券不存在");
		}
		if (!qualificationTicket.getStatus().equals(TicketStatusEnums.USING.getCode())) {
			throw new QmdException("创建店铺时资格券为使用中状态，创建失败才能将该资格券状态改为未使用");
		}
		int i = mapper.update(qualificationTicket, new UpdateWrapper<QualificationTicket>().set("shop_id", null)
				.set("status", TicketStatusEnums.UNUSE.getCode()).eq("id", ticketId));
		if (i != 1) {
			throw new QmdException("使用资格券失败");
		}
		return i;
	}

	/**
	 * 验证用户是否可以接受资格券
	 * 
	 * @param targetMobile
	 * @return
	 */
	private Long validatUserqualification(String targetMobile) {
		UserVO userVO = userService.getUserByUsername(targetMobile);
		if (userVO == null) {
			throw new QmdException("目标用户不存在");
		}
		SalemanVO managerVO = salemanService.getSalemanByUserId(userVO.getId());
		if (managerVO == null) {
			throw new QmdException("目标用户无法接受资格券");
		}
		return userVO.getId();
	}

	/**
	 * 店铺审核通过修改资格券状态
	 * 
	 * @param shopId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public void useTicketByShopId(Long shopId) {
		int count = mapper.selectCount(new QueryWrapper<QualificationTicket>().eq("shop_id", shopId));
		if (count != 1) {
			log.error("店铺使用资格券数量不等于1");
			throw new QmdException("店铺使用资格券数量不等于1");
		}
		int i = mapper.update(new QualificationTicket(), new UpdateWrapper<QualificationTicket>().eq("shop_id", shopId)
				.set("status", TicketStatusEnums.USED.getCode()));
		if (i != 1) {
			log.error("更新资格状态为已使用券失败，受影响行数不为 1");
			throw new QmdException("更新资格状态为已使用券失败");
		}
	}

	/**
	 * 总后台查询资格券
	 */
	@Override
	public PageInfo<QualificationTicketVO> queryTicket(QueryTicketParamsVO paramsVO, int pageNum, int pageSize) {
		Page<QualificationTicketVO> page = PageHelper.startPage(pageNum, pageSize);
		List<QualificationTicketVO> list = mapper.queryTicket(paramsVO);
		if(ObjectUtils.isEmpty(list)) {
			return null;
		}
		setCouldCashback(list);
		PageInfo<QualificationTicketVO> info = new PageInfo<QualificationTicketVO>(page);
		return info;
	}

	/**
	 * 根据店铺id查询资格券
	 */
	@Override
	public QualificationTicketVO getQualificationTicketByShopId(Long shopId) {
		QualificationTicket qualificationTicket = mapper
				.selectOne(new QueryWrapper<QualificationTicket>().eq("shop_id", shopId));
		if (qualificationTicket == null) {
			return null;
		}
		PayAccountVO payAccountByShopId = payAccountService.getPayAccountByShopId(shopId);
		QualificationTicketVO qualificationTicketVO = CopyBeanUtil.copyBean(qualificationTicket, QualificationTicketVO.class);
		if(payAccountByShopId != null) {
			if(payAccountByShopId.getBalance().compareTo(qualificationTicket.getCashbackDoorsill()) != -1
					&& qualificationTicket.getStatus().equals(TicketStatusEnums.USED.getCode())
					) {
				qualificationTicketVO.setCouldCashBack(true);
			}
		}
		return qualificationTicketVO;
	}

	@Override
	public List<TicketNumVO> getTiketNumByUserIdList(List<Long> userId, Byte status) {
		List<TicketNumVO> tiketNumByUserIdList = mapper.getTiketNumByUserIdList(userId, status);
		return tiketNumByUserIdList;
	}

	/**
	 * 根据资格券id集合查询资格券信息
	 */
	@Override
	public List<QualificationTicketVO> getTicketByIdList(List<String> ticketIdList) {
		List<QualificationTicket> qualificationTickets = mapper
				.selectList(new QueryWrapper<QualificationTicket>().in("id", ticketIdList));
		if (ObjectUtils.isEmpty(qualificationTickets)) {
			return null;
		}
		List<QualificationTicketVO> qualificationTicketVOs = CopyBeanUtil.copyList(qualificationTickets, QualificationTicketVO.class);
		setCouldCashback(qualificationTicketVOs);
		return qualificationTicketVOs;
	}

	/**
	 * 代理查询下线资格券
	 */
	@Override
	public PageInfo<QualificationTicketVO> agentQueryTicketByUserId(AgentQueryTicketByUserIdVO paramVO) {
		// 判断OfflineUserId是否是AgentUserId的下线
		if (!salemanService.isOffline(paramVO.getAgentUserId(), paramVO.getOfflineUserId())) {
			return null;
		}
		PageHelper.startPage(paramVO.getPageNum(), paramVO.getPageSize());
		QueryWrapper<QualificationTicket> queryWrapper = new QueryWrapper<QualificationTicket>().eq("user_id",
				paramVO.getOfflineUserId());
		if (paramVO.getStatus() != null) {
			queryWrapper.eq("status", paramVO.getStatus());
		}
		else {
			queryWrapper.in("status", Lists.newArrayList(TicketStatusEnums.UNUSE.getCode(),
					TicketStatusEnums.USING.getCode(),
					TicketStatusEnums.USED.getCode(),
					TicketStatusEnums.Returning.getCode(),
					TicketStatusEnums.Returned.getCode()));
		}
		List<QualificationTicket> selectList = mapper.selectList(queryWrapper);
		if (ObjectUtils.isEmpty(selectList)) {
			return null;
		}
		List<QualificationTicketVO> qualificationTicketVOs = CopyBeanUtil.copyList(selectList,
				QualificationTicketVO.class);
		setCouldCashback(qualificationTicketVOs);
		PageInfo<QualificationTicketVO> pageInfo = new PageInfo<>(qualificationTicketVOs);
		return pageInfo;
	}


	/**
	 * 查询资格券是否可申请返现
	 * @param qualificationTicketVOs
	 * @return
	 */
	private void setCouldCashback(List<QualificationTicketVO> qualificationTicketVOs) {
		List<Long> shopIdList = new ArrayList<>();
		for (QualificationTicketVO qualificationTicketVO : qualificationTicketVOs) {
			if (qualificationTicketVO.getShopId() != null) {
				shopIdList.add(qualificationTicketVO.getShopId());
			}
		}
		if (ObjectUtils.isEmpty(shopIdList)) {
			return ;
		}
		
		List<PayAccountVO> payAccountByShopIds = payAccountService.getPayAccountByShopIds(shopIdList);
		if (ObjectUtils.isEmpty(payAccountByShopIds)) {
			return ;
		}
		for (QualificationTicketVO qualificationTicketVO : qualificationTicketVOs) {
			for(PayAccountVO accountVO : payAccountByShopIds) {
				if(qualificationTicketVO.getShopId() != null 
						&& accountVO.getShopId() != null
						&& qualificationTicketVO.getShopId().equals( accountVO.getShopId())
						&& qualificationTicketVO.getStatus().equals(TicketStatusEnums.USED.getCode())
						&& accountVO.getBalance().compareTo(qualificationTicketVO.getCashbackDoorsill()) != -1
						) {
					//当店铺的账户总金额大于对应资格券的返现门槛时，将资格券qualificationTicketVO中是否可返现字段设置为true
					qualificationTicketVO.setCouldCashBack(true);
				}else {
					qualificationTicketVO.setCouldCashBack(false);
				}
			}
		}
	}

	@Override
	@Transactional( rollbackFor = {QmdException.class,Exception.class})
	public void setTicketStatus(String ticketId, Byte status) {
		QualificationTicket qualificationTicket = mapper.selectById(ticketId);
		if(qualificationTicket == null) {
			log.error("setTicketStatus|资格券不存在，ticketId:"+ticketId);
			throw new QmdException("该资格券不存在");
		}
		qualificationTicket.setStatus(status);
		int i = mapper.updateById(qualificationTicket);
		if(i != 1) {
			log.error("setTicketStatus|修改资格券状态失败，受影响函数不为1，受影响行数i:"+i);
			throw new QmdException("修改资格券状态失败");
		}
	}

}
