package cn.qumiandan.ticket.api;

import java.util.List;

import cn.qumiandan.ticket.vo.QueryRecordVO;
import cn.qumiandan.ticket.vo.TicketUseRecordVO;

/**
 * 资格券转让记录管理接口
 * @author lrj
 *
 */
public interface ITicketUseRecordService {

	/**
	 * 添加资格券转让记录
	 * @param ticketUseRecordVO
	 * @return
	 */
	Boolean addTicketUseRecord(TicketUseRecordVO ticketUseRecordVO);
	
	/**
	 * 批量添加转让资格券记录
	 * @param ticketUseRecordVOList
	 * @return
	 */
	Boolean batchAddTicketUseRecord(List<TicketUseRecordVO> ticketUseRecordVOList);
	
	/**
	 * 根据资格券id查询领取记录
	 * @return
	 */
	List<QueryRecordVO> getTicketUseRecordByTicketId(String ticketId);
}
