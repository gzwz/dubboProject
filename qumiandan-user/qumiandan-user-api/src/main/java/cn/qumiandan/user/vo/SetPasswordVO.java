package cn.qumiandan.user.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 设置密码参数
 * @author lrj
 *
 */
@Data
public class SetPasswordVO implements Serializable {


		private static final long serialVersionUID = 1L;
		
		/**
		 * 用户名（手机号）
		 */
		private String userName;
		
		/**
		 * 新密码
		 */
		private String newPassword;
		
		/**
		 * 旧密码
		 */
		private String oldPassword;
		
		/**
		 * 验证码
		 */
		private String code;



}
