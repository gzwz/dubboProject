package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 添加店铺成员图片验证码请求参数
 * @author yuleidian
 * @version 创建时间：2018年11月29日 下午2:53:50
 */
@Data
public class AddMemberPicCodeParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@NotBlank(message = "请传入唯一标识sign")
	private String sign;
	
}
