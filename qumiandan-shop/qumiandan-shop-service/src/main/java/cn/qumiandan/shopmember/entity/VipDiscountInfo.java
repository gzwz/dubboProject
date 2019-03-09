package cn.qumiandan.shopmember.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 会员折扣实体类
 * @author lrj
 *
 */
@Data
@TableName(value = "qmd_vip_discount_info")
public class VipDiscountInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@TableId
	private Long id;
	
	/**
	 * 店铺id
	 */
	private Long shopId;
	
	/**
	 * 折扣金额
	 */
	private BigDecimal discountMoney;
	
	/**
	 * 1:discount_money为折扣；2：discount_money为金额
	 */
	private Byte type;
	
	
	/**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 更新人
     */
    private Long updateId;

    /**
     * 状态(1.可用，0.不可用)
     */
    private Byte status;
}
