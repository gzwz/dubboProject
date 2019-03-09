package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description: 创建订单参数对象
 * @author: zhuayngyong
 * @date: 2018/12/5 9:26
 */
@Data
public class OrderAddParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "店铺编号不能为空")
	private Long shopId;

//	@NotBlank(message = "店铺名称不能为空")
//	private String shopName;

	//@NotNull(message = "用户编号不能为空")
	private Long userId;

//	@NotNull(message = "订单类型不能为空")
//	private Byte orderType;

	private Byte billing;	//是否开票（是否开具发票）（1.是,2.否）

	private String payChannel;	//支付方式（010微信，020 支付宝，060 qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码，000趣免单支付）

	private Long createId;	//创建人id

	@NotEmpty(message = "订单商品信息列表不能为空")
	private List<OrderProductParams> orderProductList;

	private Long couponId;	//所使用优惠券id

}
