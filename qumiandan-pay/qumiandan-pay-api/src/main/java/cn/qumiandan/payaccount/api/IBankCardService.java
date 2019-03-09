package cn.qumiandan.payaccount.api;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.payaccount.vo.BankCardVO;

/**
 * 银行卡service
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午11:11:43
 */
public interface IBankCardService extends IBaseService {

	/**
	 * 添加银行卡信息
	 * @param vo
	 * @return
	 */
	BankCardVO addBankCard(BankCardVO vo);
	
	/**
	 * 更新银行卡信息
	 * @param vo
	 */
	void updateBankCard(BankCardVO vo);
	
	/**
	 * 根据账户编号查询银行卡信息
	 * @param accountId
	 * @return
	 */
	BankCardVO getBankCardByAccountId(Long accountId);
	// ----------------------------------------特殊地方调用-------------------------------------------------
	/**
	 * 根据账户id删除银行卡信息
	 * @param accountId	账户id
	 * @param operateId	操作人
	 */
	void deleteBankCardByAccountId(Long accountId, Long operateId);
	
	/**
	 * 根据店铺id查询银行卡信息
	 * @param shopId
	 * @return
	 */
	BankCardVO getBankCardByShopId(Long shopId);
}
