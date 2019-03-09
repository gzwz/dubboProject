package cn.qumiandan.pay.saobei.service;

import cn.qumiandan.pay.IPayFactory;
import cn.qumiandan.pay.saobei.vo.JSPayVO;
import cn.qumiandan.pay.saobei.vo.JSRefundVO;
import cn.qumiandan.pay.saobei.vo.WechatAuthopenIdVO;
import cn.qumiandan.pay.saobei.vo.response.pay.JSPayResVO;
import cn.qumiandan.pay.saobei.vo.response.pay.JSRefundRes;
import cn.qumiandan.pay.saobei.vo.response.pay.WechatAuthopenIdResVO;

/**
 * 扫呗支付接口
 * @author yuleidian
 * @version 创建时间：2018年12月8日 下午2:06:11
 */
public interface ISaoBeiPayService extends IPayFactory {

	/**
	 * 微信扫码支付获取 认证信息接口
	 * @param req
	 */
	WechatAuthopenIdResVO getAuthopenId(WechatAuthopenIdVO vo);
	
	
	/**
	 * 公众号预支付(统一下单)
	 * 普通订单
	 * @param req
	 */
	JSPayResVO jsPay(JSPayVO vo);
	/**
	 * 公众号预支付(统一下单)
	 * 游戏订单
	 * @param req
	 */
	public JSPayResVO gameJsPay(JSPayVO vo);
	
	/**
	 * 申请退款
	 * 判断 是否退款成功调用 isSuccess方法
	 * @param vo
	 * @return
	 */
	JSRefundRes jsRefund(JSRefundVO vo);
}
