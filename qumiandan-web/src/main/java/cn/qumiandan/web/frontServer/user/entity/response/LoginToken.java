package cn.qumiandan.web.frontServer.user.entity.response;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * 登录结果
 * @author yuleidian
 * @version 创建时间：2018年11月10日 下午2:52:34
 */
@Data
public class LoginToken {

	@Expose
	private String token;			// 登录成功返回token
	
}
