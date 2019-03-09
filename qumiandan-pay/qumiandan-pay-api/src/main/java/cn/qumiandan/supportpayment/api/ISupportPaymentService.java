package cn.qumiandan.supportpayment.api;

import java.util.List;

import cn.qumiandan.supportpayment.vo.SupportPaymentVO;

/**
 * 扫呗支付方式接口
 * @author lrj
 *
 */
public interface ISupportPaymentService {

	/**
	 * 查询扫呗所有支付方式
	 * @return
	 */
	List<SupportPaymentVO> getAllSupportPayment();
	
	/**
	 * 根据code查询支付方式
	 * @param code
	 * @return
	 */
	List<SupportPaymentVO> getSupportPaymentByCode(String codes);
}
