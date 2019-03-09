package cn.qumiandan.pay.lianlian.api;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.pay.lianlian.vo.LianLianPaymentNoticeVO;

/**
 * 连连支付回调service
 * @author yuleidian
 * @version 创建时间：2019年1月5日 下午3:39:48
 */
public interface ILianLianPayCallbackService extends IBaseService {

	/**
	 * 连连提现回调
	 * @param vo
	 */
	void paymentCallback(LianLianPaymentNoticeVO vo);
	
	
}
