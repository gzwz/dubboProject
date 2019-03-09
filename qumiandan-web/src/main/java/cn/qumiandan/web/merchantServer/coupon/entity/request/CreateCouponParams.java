package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreateCouponParams implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	/** id */
	private Long id;
	
	/** 模板id */
	@NotNull(message = "模板id不能为空")
	private Long templeteId;
	
	/** 优惠券名称 */
	@NotBlank(message = "优惠券名称不能为空")
	private String name;
	
	/** 优惠券面值 */
	//@NotEmpty(message = "优惠券面值不能为空")
	private BigDecimal faceValue;

	/** 发行者(模板中的使用范围，是店铺，则提供店铺id) */
	@NotNull(message = "发行者不能为空")
	private Long publisher;

	/** 领取规则  json key:规则id, value:规则内容 */
	private String takeRule;
	
	/** 使用规则  json key:规则id, value:规则内容 */
	private String useRule;
	
	/** 发行者名称(是店铺，则提供店铺名称) */
	@NotBlank(message = "优惠券发行者名称不能为空")
	private String publisherName;

	/** 使用条件说明 */
	//@NotEmpty(message = "使用条件说明不能为空")
	private String conditionDesc;
	
	/** 生效时间 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "生效时间不能为空")
	private Date startTime;

	/** 失效时间 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "失效时间不能为空")
	private Date endTime;
	
	/** 有效期 */
	@NotNull(message = "有效期不能为空")
	private Integer validity;
	
	/** 发行总数量 */
	@NotNull(message = "发行总数量不能为空")
	private Long totalNumber;
	
	/** 剩余数量(剩余数量 < 发行总数量) */
	//private Long remainNumber;
	
	/** 需要排除的商品*/
	private List<Long> excludeProductIds;

}
