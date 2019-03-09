package cn.qumiandan.web.commonServer.sms.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.web.commonServer.sms.entity.BaseSmsVO;
/**
 * 短信服务
 * @author WLZ
 * 2018年11月12日
 */
public interface IAliSmsService {

	/** 发送短信验证码 */
	public SendSmsResponse SendSmsResponse(BaseSmsVO smsVO) throws QmdException;
	
	/** 查询短信验证码 */
	public QuerySendDetailsResponse querySendDetails(String bizId) throws QmdException;
}
