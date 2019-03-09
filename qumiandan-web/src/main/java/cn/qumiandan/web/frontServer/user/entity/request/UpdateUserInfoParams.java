package cn.qumiandan.web.frontServer.user.entity.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * 更新用户信息接口
 * @author lrj
 *
 */
@Data
public class UpdateUserInfoParams implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
     * 用户Id
     */
	@NotNull(message = "用户Id不能为空")
    private Long id;
    
    /**
     * 微信账号unionid
     */
    private String unionId;

    /**
     * 微信openid
     */
    private String openId;

	@Pattern(regexp="^\\d{6}$",message="省编码格式不正确")
    private String proCode;				// 用户个人资料填写的省份
	@Pattern(regexp="^\\d{6}$",message="市编码格式不正确")
    private String cityCode;			// 普通用户个人资料填写的城市
	@Pattern(regexp="^\\d{6}$",message="县编码格式不正确")
    private String countryCode;			// 国家，如中国为CN
    
    
    /**
     * 支付宝id
     */
    private String alipayId;

    /**
     * 名字
     */
    private String name;

    /**
     * 更新人
     */
    private Long updateId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 座机
     */
    private String phone;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 银行卡
     */
    private String bankAccount;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 头像
     */
    private String portrait;
 
}
