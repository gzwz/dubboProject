package cn.qumiandan.pay.withdraw.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.withdraw.api.IWithdrawCashAuditRecordService;
import cn.qumiandan.pay.withdraw.entity.WithdrawCashAuditRecord;
import cn.qumiandan.pay.withdraw.mapper.WithdrawCashAuditRecordMapper;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashAuditRecordVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 提现申请记录管理实现类
 * @author lrj
 *
 */
@Slf4j
@Component
@Service(interfaceClass = IWithdrawCashAuditRecordService.class)
public class WithdrawCashAuditRecordServiceImpl implements IWithdrawCashAuditRecordService{

	@Autowired
	private WithdrawCashAuditRecordMapper auditRecordMapper ;
	
	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public WithdrawCashAuditRecordVO addAuditRecord(WithdrawCashAuditRecordVO auditRecordVO) {
		WithdrawCashAuditRecord auditRecord = CopyBeanUtil.copyBean(auditRecordVO, WithdrawCashAuditRecord.class);
		int  i = auditRecordMapper.insert(auditRecord);
		if(i != 1) {
			log.info("添加提现审核记录失败,受影响的行数不为1");
			throw new QmdException("添加提现审核记录失败");
		}
		auditRecordVO.setId(auditRecord.getId());
		return auditRecordVO;
	}

	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	@Override
	public void updateAuditRecord(WithdrawCashAuditRecordVO auditRecordVO) {
		WithdrawCashAuditRecord auditRecord = CopyBeanUtil.copyBean(auditRecordVO, WithdrawCashAuditRecord.class);
		int  i = auditRecordMapper.updateById(auditRecord);
		if(i != 1) {
			log.info("修改提现审核记录失败,受影响的行数不为1");
			throw new QmdException("修改提现审核记录失败");
		}
	}

	@Override
	public List<WithdrawCashAuditRecordVO> getAuditRecordByWithdrawId(Long withdrawId) {
		List<WithdrawCashAuditRecord> list = auditRecordMapper.selectList(new QueryWrapper<WithdrawCashAuditRecord>().eq("withdraw_id", withdrawId));
		if(ObjectUtils.isEmpty(list)) {
			return null;
		}
		return CopyBeanUtil.copyList(list, WithdrawCashAuditRecordVO.class);
	}

	@Override
	public WithdrawCashAuditRecordVO getNewAuditRecordByWithdrawId(Long withdrawId) {
		List<WithdrawCashAuditRecord> list = auditRecordMapper.selectList(new QueryWrapper<WithdrawCashAuditRecord>().eq("withdraw_id", withdrawId));
		if(ObjectUtils.isEmpty(list)||list.size()<=0) {
			return null;
		}
		return CopyBeanUtil.copyBean(list.get(0), WithdrawCashAuditRecordVO.class);
	}

}
