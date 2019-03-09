package cn.qumiandan.web.frontServer.shopmember.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class PurchaseShopMemberParams implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
     * 店铺id
     */
	@NotNull(message = "店铺id不能为空")
    private Long shopId;

    /**
     * 用户id
     */
	@NotNull(message = "用户id不能为空")
    private Long userId;
    
    /**
     * 购买天数
     */
	@NotNull(message = "购买天数不能为空")
    private Integer days;
}
