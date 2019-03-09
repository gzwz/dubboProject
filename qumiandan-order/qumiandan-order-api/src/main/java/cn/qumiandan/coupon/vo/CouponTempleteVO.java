package cn.qumiandan.coupon.vo;

import java.io.Serializable;
import java.util.Date;
import cn.qumiandan.coupon.constant.CouponEnum;
import cn.qumiandan.coupon.constant.CouponCreateRulesEnum;
import lombok.Data;

@Data
public class CouponTempleteVO implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	/**模板id*/
	private Long id;
	
	/**模板名称*/
	private String name;
	
	/**使用范围（1.平台，2.类目，3.指定店铺）
	 * {@link CouponEnum}
	 * */
	private Byte useRange;
	
	/**创建规则（创建规则Id的数组）
	 * {@link CouponCreateRulesEnum }
	 * */
	private String createRule;

	/**领取规则 （领取规则Id的数组）
	 * {@link CouponTakeRulesEnum}
	 */
	private String takeRule;
	
	/**使用规则（使用规则Id的数组）
	 * {@link CouponUseRulesEnum}
	 */
	private String useRule;
	
	/**状态(1.正常，0.删除) */
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
