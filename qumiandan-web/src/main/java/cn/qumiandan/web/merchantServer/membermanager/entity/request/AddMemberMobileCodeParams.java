package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 添加店铺成员手机验证码请求参数
 * @author yuleidian
 * @version 创建时间：2018年11月29日 下午2:40:37
 */
@Data
public class AddMemberMobileCodeParams implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "请输入验证码")
	private String code;
	
	@Min(value = 11, message = "请输入正确的手机号")
	@NotBlank(message = "请输入手机号")
	private String mobile;
	
	@NotBlank(message = "请传入唯一标识sign")
	private String sign;
}
