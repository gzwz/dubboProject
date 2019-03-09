package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 验证用户是否存在
 * @author yuleidian
 * @version 创建时间：2018年11月29日 下午3:42:33
 */
@Data
public class VerifyUserExistParams {

	@NotBlank(message = "请输入手机号")
	@Min(value = 11, message = "请输入正确的手机号")
	private String mobile;
	
	
}
