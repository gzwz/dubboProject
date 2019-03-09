package cn.qumiandan.web.frontServer.user.entity.response;

import java.util.Date;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

	/**
	 * 用户Id
	 */
	@Expose
	private Long id;

	/**
	 * 账号
	 */
	@Expose
	private String userName;

	/**
	 * 微信账号unionId
	 */
	@Expose
	private String unionId;

	/**
	 * 微信openid
	 */
	@Expose
	private String openId;

	/**
	 * 省
	 */
	@Expose
	private String proCode;

	/**
	 * 市
	 */
	@Expose
	private String cityCode;

	/**
	 * 区
	 */
	@Expose
	private String countryCode;

	/**
	 * 昵称
	 */
	@Expose
	private String nickName;

	/**
	 * 真实姓名
	 */
	@Expose
	private String realName;

	/**
	 * 电话
	 */
	@Expose
	private String mobile;

	/**
	 * 座机
	 */
	@Expose
	private String phone;

	/**
	 * 性别
	 */
	@Expose
	private Integer sex;

	/**
	 * 邮箱
	 */
	@Expose
	private String email;

	/**
	 * 生日
	 */
	@Expose
	private Date birthday;

	/**
	 * 头像
	 */
	@Expose
	private String portrait;
	
	/**
	 * 是否有密码标识：1是，0否
	 */
	@Expose
	private Byte hasPassword;
}
