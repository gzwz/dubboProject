package cn.qumiandan.ticket.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.ticket.entity.TicketUseRecord;
import cn.qumiandan.ticket.vo.QueryRecordVO;
@Mapper
public interface TicketUseRecordMapper extends BaseMapper<TicketUseRecord>{
	
	/**
	 * 根据资格券id查询资格券转让记录
	 * @param ticketId
	 * @return
	 */
	List<QueryRecordVO> getTicketUseRecordByTicketId(@Param("ticketId")String ticketId);
	
}
