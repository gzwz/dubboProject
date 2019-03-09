package cn.qumiandan.web.frontServer.user.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import cn.qumiandan.enums.LoginOrigin;
import lombok.Data;

/**
 * 手机验证码登录
 * @author yuleidian
 * @version 创建时间：2018年11月7日 下午3:42:08
 */
@Data
public class VerificationCodeParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Length(min = 11, max = 11, message = "请填写正确的手机号")
	@NotBlank(message = "请填写手机号")
	private String mobile;
	
	@NotBlank(message = "请填写验证码")
	private String code;
	
	@NotNull(message = "登录端来源不能空")
	private LoginOrigin origin;
	
	private String username;
	private String openId;				// 用户的唯一标识
	private String nickName;			// 用户昵称
	private String unionId;				// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	private Integer sex;					// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	@Pattern(regexp="^\\d{6}$",message="省编码格式不正确")
    private String proCode;				// 用户个人资料填写的省份
	@Pattern(regexp="^\\d{6}$",message="市编码格式不正确")
    private String cityCode;			// 普通用户个人资料填写的城市
	@Pattern(regexp="^\\d{6}$",message="县编码格式不正确")
    private String countryCode;			// 国家，如中国为CN
	private String portrait;			// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	private List<String> privilege;		// 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	private String realName;			// 真实姓名
	private Date birthday;				// 生日
	private String email;				// 电子邮箱
	
	
}
