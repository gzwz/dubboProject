package cn.qumiandan.web.frontServer.apply.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
@Data
public class AddApplyParams implements Serializable{


	private static final long serialVersionUID = 1L;
	/**
	 * 申请类型（5：申请开通店铺，4.申请省代理，3.申请市代理，2.申请区代，1.申请成为业务员）
	 */
	@NotNull(message = "类型不能为空")
	private Byte type ;

	/** 联系人 */
	@NotBlank(message = "联系人不能为空")
	private String name;
	/** 手机 */
	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp="^1[34578]\\d{9}$",message="店铺联系人电话格式不正确")
	private String mobile;
	/** 店铺名 */
//	@NotBlank(message = "店铺名不能为空")
	private String shop;
	/** 省code */
	@NotBlank(message = "省code不能为空")
	@Pattern(regexp="^\\d{6}$",message="省编码格式不正确")
	private String proCode;
	/** 市code */
	@Pattern(regexp="^\\d{6}$",message="市编码格式不正确")
	private String cityCode;
	/** 区code */
	@Pattern(regexp="^\\d{6}$",message="区编码格式不正确")
	private String countryCode;
	/**行业id*/
	private Long industryId;
	/** 创建者 */
	private Long createId;

}
