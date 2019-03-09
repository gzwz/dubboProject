package cn.qumiandan.ticket.api;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.ticket.vo.AgentQueryTicketByUserIdVO;
import cn.qumiandan.ticket.vo.QualificationTicketVO;
import cn.qumiandan.ticket.vo.QueryTicketParamsVO;
import cn.qumiandan.ticket.vo.TicketNumVO;

/**
 * 资格券管理接口
 * @author wlz
 *
 */
public interface IQualificationTicketService {
	
	/**
	 * 根据资格券id回收删除资格券
	 * @param ids
	 */
	void delQualificationTicketByIds(Set<String> ids);

	/**
	 * 创建资格券
	 * @param num 需要创建的数量
	 * @return
	 */
	List<QualificationTicketVO> createTicket(Long num, QualificationTicketVO param);
	
	/**
	 * 根据券id 查询当前券
	 * @param ticketId
	 * @return
	 */
	QualificationTicketVO getTicketById(String ticketId);
	
	/**
	 * 根据用户id查询资格券
	 * @param userId
	 * @param status
	 * @return
	 */
	List<QualificationTicketVO> getTicketByUserId(Long userId,Byte status);
	
	/**
	 *  根据用户id查询资格券（分页）
	 * @param userId
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<QualificationTicketVO> getTicketPageByUserId(Long userId,Byte status,int pageNum,int pageSize);
	
	/**
	 * 资格券转让(单个转让），mobile为接受转让者手机号
	 * @param ticketId
	 * @param targetMobile
	 * @return
	 */
	Boolean makeOverSingleTicket(String ticketId, String targetMobile);
	
	/**
	 * 资格券转让(批量转让），mobile为接受转让者手机号
	 * @param ticketIdSet
	 * @param targetMobile
	 * @return
	 */
	Boolean makeOverBatchTicket(Set<String> ticketIdSet ,String mobile);
	
	/**
	 * 判断用户是否有可用的券,是返回true,否返回false
	 * @param userId
	 * @return
	 */
	Boolean hasAvailableTicket(Long userId);
	
	/**
	 * 修改资格券的状态为使用中
	 * @param ticketId
	 * @param shopId
	 * @return
	 */
	Integer setTicketStatusToUsing(String ticketId,Long shopId);
	
	/**
	 * 修改资格券的状态为使用结束
	 * @param ticketId
	 * @return
	 */
	Integer setTicketStatusToUsed(String ticketId);
	
	
	/**
	 * 修改资格券的状态为未使用
	 * @param ticketId
	 * @return
	 */
	Integer setTicketStatusToUnuse(String ticketId);
	
	/**
	 * 店铺审核通过修改资格券状态
	 * @param shopId
	 * @return
	 */
	void useTicketByShopId(Long shopId); 
	
	/**
	 * 总后台查询资格券
	 * @param paramsVO
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<QualificationTicketVO> queryTicket(QueryTicketParamsVO paramsVO, int pageNum,int pageSize);

	/**
	 * 根据资格券id集合查询资格券信息
	 * @param ticketIdList
	 * @return
	 */
	List<QualificationTicketVO> getTicketByIdList(List<String> ticketIdList);
	
	/**
	 * 根据店铺id查询资格券
	 * @param shopId
	 * @return
	 */
	QualificationTicketVO getQualificationTicketByShopId(Long shopId) ;
	
	/**
	 * 根据用户id集合查询资格券数量
	 * @param userId
	 * @param status
	 * @return
	 */
	List<TicketNumVO> getTiketNumByUserIdList(List<Long> userId, Byte status);

	/**
	 * 根据下线用户id查询下线资格券列表
	 * @param agentUserId
	 * @param usesrId
	 * @param status
	 * @return
	 */
	PageInfo<QualificationTicketVO> agentQueryTicketByUserId(AgentQueryTicketByUserIdVO agentQueryTicketByUserIdVO);
	
	/**
	 * 修改资格券状态
	 * @param ticketId
	 * @param status
	 */
	void setTicketStatus(String ticketId,Byte status);
}
