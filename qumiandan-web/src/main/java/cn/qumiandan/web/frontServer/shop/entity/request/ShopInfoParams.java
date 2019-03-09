package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 获取店铺详情请求
 * @author yuleidian
 * @version 创建时间：2018年12月21日 下午7:35:33
 */
@Data
public class ShopInfoParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
	
}
