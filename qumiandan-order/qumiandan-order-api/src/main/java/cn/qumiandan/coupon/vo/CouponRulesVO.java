package cn.qumiandan.coupon.vo;

import java.io.Serializable;
import java.util.Date;

import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.coupon.constant.CouponCreateRulesEnum;
import cn.qumiandan.coupon.constant.CouponRulesTypeEnum;
import lombok.Data;

/**
 * 优惠券规则
 * @author WLZ
 * 2018年12月3日
 */
@Data
public class CouponRulesVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;

	/**规则名称*/
	private String ruleName;
	
	/**归属（1.创建规则，2.领取规则，3.使用规则）
	 *	{@link CouponRulesTypeEnum}
	 **/
	private Byte belongType;
	
	/**规则类型（1.直减.2.指定用户. 3.总量限制，4.数量限制）
	 * {@link CouponCreateRulesEnum CouponTakeRulesEnum CouponUseRulesEnum}
	 */
	private String ruleType;
	
	/**规则描述*/
	private String ruleDescription;
	
	/**状态(1.正常，0.删除)
	 * {@link StatusEnum}
	 * */
	private Byte status;
	
	/**创建人*/
	private Long createId;
	
	/**更新人*/
	private Long updateId;
	
	/**创建时间*/
	private Date createDate;
	
	/**更新时间*/
	private Date updateDate;
}
