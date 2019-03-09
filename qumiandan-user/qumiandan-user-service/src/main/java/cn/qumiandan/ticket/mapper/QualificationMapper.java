package cn.qumiandan.ticket.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.ticket.entity.QualificationTicket;
import cn.qumiandan.ticket.vo.QualificationTicketVO;
import cn.qumiandan.ticket.vo.QueryTicketParamsVO;
import cn.qumiandan.ticket.vo.TicketNumVO;


@Mapper
public interface QualificationMapper extends BaseMapper<QualificationTicket> {

	/**
	 * 总后台查询资格券
	 * @param paramsVO
	 * @return
	 */
	List<QualificationTicketVO> queryTicket(@Param("paramsVO")QueryTicketParamsVO paramsVO );

	/**
	 * 根据用户id集合查询资格券数量
	 * @param userIdList
	 * @param status
	 * @return
	 */
	List<TicketNumVO> getTiketNumByUserIdList(@Param ("userIdList") List<Long> userIdList, @Param ("status") Byte status);
}
