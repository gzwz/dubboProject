package cn.qumiandan.web.commonServer.sms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.utils.GsonHelper;
import cn.qumiandan.web.commonServer.sms.entity.BaseSmsVO;
import cn.qumiandan.web.commonServer.sms.service.IAliSmsService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Data
public class AliSmsServiceImpl implements IAliSmsService,InitializingBean {

	// 产品名称:云通信短信API产品,开发者无需替换
	@Value("${alisms.product}")
	private String product;
	
	// 产品域名,开发者无需替换
	@Value("${alisms.domain}")
	private String domain;

	// 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	@Value("${alisms.accessKeyId}")
	private  String accessKeyId;
	 
	@Value("${alisms.accessKeySecret}")
	private  String accessKeySecret;
	
	private static IClientProfile profile ;
	
	private static IAcsClient acsClient;
	
	
	//@Bean()
	public IAliSmsService createIAliSmsService(){
		AliSmsServiceImpl aliSms = new AliSmsServiceImpl();
		aliSms.setAccessKeyId(accessKeyId);
		aliSms.setAccessKeySecret(accessKeySecret);
		aliSms.setDomain(domain);
		aliSms.setProduct(product);
		AliSmsServiceImpl.Initer initer = new Initer();
		initer.init();
		// 可自助调整超时时间
		return aliSms;
	}
	
	private class Initer{
		public void  init() {
			// 可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			
			// 初始化acsClient,暂不支持region化
			profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			try {
				DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			} catch (ClientException e) {
				log.error("初始化客户端异常");
				log.error(e.getErrMsg());
			}
			acsClient = new DefaultAcsClient(profile);
		}
	}

	
	
	public SendSmsResponse SendSmsResponse(BaseSmsVO smsVO) throws QmdException {

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(smsVO.getPhoneNumbers());
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(smsVO.getSignName());
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(smsVO.getTemplateCode());
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		String tmp = GsonHelper.toJson(smsVO);
		request.setTemplateParam(tmp);
		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId(smsVO.getOutId());

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = null; 
		// 暂时注释掉发送短信
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (Exception e) {
			log.error("发送短信异常");
			throw new QmdException(GwErrorCode.GW8101);
		}

		return sendSmsResponse;
	}

	public QuerySendDetailsResponse querySendDetails(String bizId) throws QmdException {

		// 组装请求对象
		QuerySendDetailsRequest request = new QuerySendDetailsRequest();
		// 必填-号码
		request.setPhoneNumber("15000000000");
		// 可选-流水号
		request.setBizId(bizId);
		// 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		request.setSendDate(ft.format(new Date()));
		// 必填-页大小
		request.setPageSize(10L);
		// 必填-当前页码从1开始计数
		request.setCurrentPage(1L);

		// hint 此处可能会抛出异常，注意catch
		QuerySendDetailsResponse querySendDetailsResponse = null;
		try {
			querySendDetailsResponse = acsClient.getAcsResponse(request);
		} catch (ClientException e) {
			log.error("发送短信异常");
			throw new QmdException(GwErrorCode.GW8101);
		}

		return querySendDetailsResponse;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AliSmsServiceImpl.Initer initer = new Initer();
		initer.init();
	}

}
