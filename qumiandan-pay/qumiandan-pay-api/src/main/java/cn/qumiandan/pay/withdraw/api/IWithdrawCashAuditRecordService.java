package cn.qumiandan.pay.withdraw.api;

import java.util.List;

import cn.qumiandan.pay.withdraw.vo.WithdrawCashAuditRecordVO;

/**
 * 提现审核记录管理接口
 * @author lrj
 *
 */
public interface IWithdrawCashAuditRecordService {
	
	/**
	 * 添加审核记录
	 * @param auditRecordVO
	 * @return
	 */
	WithdrawCashAuditRecordVO addAuditRecord(WithdrawCashAuditRecordVO auditRecordVO);

	/**
	 * 更新审核记录
	 * @param auditRecordVO
	 * @return
	 */
	void updateAuditRecord(WithdrawCashAuditRecordVO auditRecordVO);

	/**
	 * 查询审核记录
	 * @return
	 */
	List<WithdrawCashAuditRecordVO> getAuditRecordByWithdrawId(Long withdrawId);
	
	
	/**
	 * 查询最新的提现审核记录
	 * @return
	 */
	WithdrawCashAuditRecordVO getNewAuditRecordByWithdrawId(Long withdrawId);
}
