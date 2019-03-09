package cn.qumiandan.coupon.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.qumiandan.coupon.constant.CouponEnum;
import lombok.Data;
@Data
@TableName("qmd_coupon_templete")
public class CouponTemplete {
	
	/**模板id*/
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/**模板名称*/
	private String name;
	
	/**使用范围（1.平台，2.类目，3.指定店铺）
	 * {@link CouponEnum}
	 * */
	private Byte useRange;
	
	/**创建规则 (Id的数组)*/
	private String createRule;

	/**领取规则 (Id的数组)*/
	private String takeRule;
	
	/**使用规则（使用规则Id的数组）*/
	private String useRule;
	
	/**状态(1.正常，0.删除)*/
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
