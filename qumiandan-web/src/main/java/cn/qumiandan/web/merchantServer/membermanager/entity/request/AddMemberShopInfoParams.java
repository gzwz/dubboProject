package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 添加店铺成员请求参数
 * @author yuleidian
 * @version 创建时间：2018年11月29日 下午3:31:04
 */
@Data
public class AddMemberShopInfoParams implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 验证码
	 */
	@NotBlank(message = "请输入手机验证码")
	private String code;
	
	/** 手机号 */
	@NotBlank(message = "请输入手机号")
	@Min(value = 11, message = "请输入正确的手机号")
	private String mobile;
	
	/** 店铺管理员备注名 */
	private String remarkName;
	
	/** 角色id*/
	@NotNull(message = "请为用户选择角色")
	private Long roleId;
	
	/** 店铺ids*/
	@NotEmpty(message = "请为用户选择管理的店铺")
	private List<Long> shopIds;
	
	/** 创建人*/
	private Long createId;
	
}
