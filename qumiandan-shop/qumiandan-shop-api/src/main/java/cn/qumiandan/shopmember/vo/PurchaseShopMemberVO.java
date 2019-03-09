package cn.qumiandan.shopmember.vo;

import java.io.Serializable;

import lombok.Data;
@Data
public class PurchaseShopMemberVO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 店铺id
     */
    private Long shopId;

    /**
     * 用户id
     */
    private Long userId;
    
    /**
     * 购买天数
     */
    private Double days;
}
