package cn.qumiandan.web.commonServer.sms.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***
 * 接收短信的VO
 * @author WLZ
 * 2018年11月12日
 */
@Getter
@Setter
@ToString
public class BaseSmsVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 必填： 待发送手机号 */
	private String phoneNumbers;
	
	/** 必填:短信签名-可在短信控制台中找到 */
	private String signName;
	
	/** 必填:短信模板-可在短信控制台中找到 */
	private String templateCode;
	
	/**选填-上行短信扩展码(无特殊需求用户请忽略此字段)*/
	private String smsUpExtendCode;
	
	/** 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者 */
	private String outId;
	
	private Long resourceOwnerId;

	private String resourceOwnerAccount;

	private Long ownerId;

	private String templateParam;
	
}
