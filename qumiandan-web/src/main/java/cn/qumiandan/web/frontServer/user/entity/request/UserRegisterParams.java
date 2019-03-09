package cn.qumiandan.web.frontServer.user.entity.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * 用户账号密码注册
 * @author yuleidian
 * @version 创建时间：2018年11月9日 下午2:42:44
 */
@Data
public class UserRegisterParams {

	@NotBlank(message = "验证码不能为空")
	private String code;							// 用户名
	
	@NotBlank(message = "请填写账号")
	private String username;						// 账号
	
	@NotBlank(message = "请填写密码")
	private String password;						// 密码
	
	private String nickName;						// 用户昵称
	@Pattern(regexp="^\\d{6}$",message="省编码格式不正确")
    private String proCode;				// 用户个人资料填写的省份
	@Pattern(regexp="^\\d{6}$",message="市编码格式不正确")
    private String cityCode;			// 普通用户个人资料填写的城市
	@Pattern(regexp="^\\d{6}$",message="县编码格式不正确")
    private String countryCode;			// 国家，如中国为CN
	private String sex;								// 性别
	private String realName;						// 真实姓名
	private String mobile;							// 电话	
	private Date birthday;							// 生日
	private String portrait;						// 头像地址
	private String email;							// 电子邮箱
	
}
