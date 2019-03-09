package cn.qumiandan.ticket.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.ticket.api.ITicketUseRecordService;
import cn.qumiandan.ticket.entity.TicketUseRecord;
import cn.qumiandan.ticket.mapper.TicketUseRecordMapper;
import cn.qumiandan.ticket.vo.QueryRecordVO;
import cn.qumiandan.ticket.vo.TicketUseRecordVO;
import cn.qumiandan.utils.CopyBeanUtil;

/**
 * 资格券转让记录管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = ITicketUseRecordService.class)
public class TicketUseRecordServiceImpl extends ServiceImpl<TicketUseRecordMapper,TicketUseRecord> implements ITicketUseRecordService{

	@Autowired
	private TicketUseRecordMapper ticketUseRecordMapper;
	/**
	 * 添加转让资格券记录
	 * @param ticketUseRecordVO
	 * @return
	 */
	@Override
	public Boolean addTicketUseRecord(TicketUseRecordVO ticketUseRecordVO) {
		if(ticketUseRecordVO == null) {
			throw new QmdException("参数错误");
		}
		TicketUseRecord ticketUseRecord = CopyBeanUtil.copyBean(ticketUseRecordVO, TicketUseRecord.class);
		int i = ticketUseRecordMapper.insert(ticketUseRecord);
		if(i!=1) {
			throw new QmdException("添加失败");
		}
		return true;
	}

	/**
	 * 批量添加转让资格券记录
	 * @param ticketUseRecordVOList
	 * @return
	 */
	@Override
	public Boolean batchAddTicketUseRecord(List<TicketUseRecordVO> ticketUseRecordVOList) {
		if(ticketUseRecordVOList != null && ticketUseRecordVOList.isEmpty()) {
			throw new QmdException("参数不能为空");
		}
		 List<TicketUseRecord> list = CopyBeanUtil.copyList(ticketUseRecordVOList, TicketUseRecord.class);
		boolean flag =  saveBatch(list);
		return flag;
	}

	/**
	 * 根据资格券id查询资格券转让记录
	 * @param ticketId
	 * @return
	 */
	@Override
	public List<QueryRecordVO> getTicketUseRecordByTicketId(String ticketId) {
		List<QueryRecordVO> ticketUseRecordByTicketId = ticketUseRecordMapper.getTicketUseRecordByTicketId(ticketId);
		return ticketUseRecordByTicketId;
	}

}
