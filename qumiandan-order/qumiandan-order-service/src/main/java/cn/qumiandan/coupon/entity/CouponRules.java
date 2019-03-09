package cn.qumiandan.coupon.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.qumiandan.constant.StatusEnum;
import lombok.Data;

@Data
@TableName("qmd_coupon_rule")
public class CouponRules {

	@TableId(type = IdType.AUTO)
	private Long id;
	
	/**规则名称*/
	private String ruleName;
	
	/**归属（1.创建规则，2.领取规则，3.使用规则）*/
	private Byte belongType;
	
	/**规则类型（1，满多少减多少。2.指定用户. 3.总量限制，4.数量限制）*/
	private String ruleType;
	
	/**规则描述*/
	private String ruleDescription;
	
	/**状态 1.正常，0.删除
	 * {@link StatusEnum}
	 * */
	private Byte status;
	
	/**创建人*/
	private Long createId;
	
	/**更新人*/
	private String updateId;
	
	/**创建时间*/
	private Date createDate;
	
	/**更新时间*/
	private Date updateDate;
	
	
}
