package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 店铺提现参数
 * @author lrj
 *
 */
@Data
public class ShopApplyCashParams implements Serializable{


	  private static final long serialVersionUID = 1L;
	  
	  @NotNull(message="店铺id不能为空")
	  private Long shopId;
	
}