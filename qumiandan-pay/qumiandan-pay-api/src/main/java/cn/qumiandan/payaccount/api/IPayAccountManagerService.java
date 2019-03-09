package cn.qumiandan.payaccount.api;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.payaccount.vo.PayAccountManagerVO;

/**
 * 支付账户管理信息service
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午12:08:54
 */
public interface IPayAccountManagerService extends IBaseService {

	/**
	 * 添加支付账户关联信息
	 * @param vo
	 * @return
	 */
	PayAccountManagerVO addPayAccountManager(PayAccountManagerVO vo);
	
	/**
	 * 更新支付账户关联信息
	 * @param vo
	 * @return
	 */
	int updatePayAccountManager(PayAccountManagerVO vo);
	
	
	/**
	 * 删除支付账户关联信息
	 * @param id 关联信息id
	 * @param operateId 操作人
	 */
	void deletePayAccountManager(Long id, Long operateId);
	
	/**
	 * 删除支付账户关联信息
	 * @param userId	用户id
	 * @param accountId	账户id
	 * @param operateId 操作人
	 */
	void deletePayAccountManager(Long userId, Long accountId, Long operateId);
	
	/**
	 * 删除支付账户关联信息
	 * @param userId	用户id
	 * @param accountId	账户id
	 * @param operateId 操作人
	 */
	void deletePayAccountManagerByAccountId(Long accountId, Long operateId);
}
