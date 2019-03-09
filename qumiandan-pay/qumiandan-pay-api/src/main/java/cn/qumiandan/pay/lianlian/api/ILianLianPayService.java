package cn.qumiandan.pay.lianlian.api;

import cn.qumiandan.pay.withdraw.vo.WithdrawCashResultVO;

/**
 * 连连支付
 * @author yuleidian
 * @version 创建时间：2019年1月3日 下午8:26:12
 */
public interface ILianLianPayService {

	/**
	 * 普通提现 
	 * 连连提现接口
	 * @param applyId
	 * @return
	 */
	WithdrawCashResultVO withdraw(Long applyId);
	
}
