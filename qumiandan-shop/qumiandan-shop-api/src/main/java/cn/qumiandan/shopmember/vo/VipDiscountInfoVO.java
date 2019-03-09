package cn.qumiandan.shopmember.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 会员折扣
 * @author lrj
 *
 */
@Data
public class VipDiscountInfoVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
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
