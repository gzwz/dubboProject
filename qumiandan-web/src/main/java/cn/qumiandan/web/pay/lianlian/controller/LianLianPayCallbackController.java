package cn.qumiandan.web.pay.lianlian.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.pay.lianlian.api.ILianLianPayCallbackService;
import cn.qumiandan.web.pay.lianlian.entity.request.LianLianNoticeParams;
import cn.qumiandan.web.pay.lianlian.entity.response.LianLianNotifyResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 这个Controller需放到服务器上运行调试 异步回调接口 
 * 用于接收连连付款状态 只有生成连连支付单的订单才会有异步回调通知，
 * 没生成连连支付单的订单连连是不会提交到银行渠道走付款流程
 * 
 * 用spring mvc获取需要配置下面 或者参考另一个文件夹的异步回调demo从http request 数据流中获取
 * <mvc:annotation-driven> <mvc:message-converters register-defaults="false">
 * <!-- 避免IE执行AJAX时,返回JSON出现下载文件 --> <bean id="fastJsonHttpMessageConverter"
 * class=
 * "org.springframework.http.converter.json.MappingJackson2HttpMessageConverter "
 * > <property name="supportedMediaTypes"> <list>
 * <value>text/html;charset=UTF-8</value>
 * <value>application/json;charset=UTF-8</value>
 * <value>text/json;charset=UTF-8</value> </list> </property> </bean>
 * </mvc:message-converters> </mvc:annotation-driven>
 * 
 * @author lihp
 * @date 2017-3-17 上午09:55:30
 */
@Slf4j
@RestController
@RequestMapping("/lianLianPayCallbackController/")
public class LianLianPayCallbackController {

	@Reference
	private ILianLianPayCallbackService lianLianPayCallbackService;
	
	/**
	 * 支付平台异步通知更新   用这个demo需要xml配置json转化格式，不然回调接收异常，具体配置参考类注释
	 * 
	 * 目前不需要做什么  使用的是连连同步返回状态
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("receiveNotify")
	public LianLianNotifyResult receiveNotify(@RequestBody(required = false) LianLianNoticeParams businessNoticeBean) {
		log.info("连连回调了" + businessNoticeBean.toString());
		LianLianNotifyResult result = new LianLianNotifyResult();
		/*if (Objects.nonNull(businessNoticeBean)) {
			try {
				lianLianPayCallbackService.paymentCallback(CopyBeanUtil.copyBean(businessNoticeBean, LianLianPaymentNoticeVO.class));
			} catch (Exception e) {
				if (e instanceof QmdException) {
					QmdException ex = (QmdException) e;
					if (PayErrorCode.PAY8000.getCode().equals(ex.getCode())) {
						result.setRet_code("0000");
						result.setRet_msg("交易成功");
						return result;
					}
				}
			}
		} else {
			result.setRet_code("1004");
			result.setRet_msg("请求参数为空");
			return result;
		}*/
		result.setRet_code("0000");
		result.setRet_msg("交易成功");
		return result;
	}

}
