package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;
import java.util.List;

import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.order.constant.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 根据用户id查询订单参数
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class GetOrderByUserIdParams extends CommonParams implements Serializable{

	private static final long serialVersionUID = 1L;


	/**
	 * 店铺id
	 */
	private Long shopId;
	
    /**
     * 订单状态(1.未付款；2.支付中;3.游戏支付;4.已付款;5.交易完成(已消费);6.退款申请中;7.已退款;8.拒绝退款;9.取消交易;10.已删除;)
     * {@link OrderStatusEnum}
     */
    private List<Byte> statusList;
    
    /**
     * 是否中奖；1未中奖；2.中奖；
     */
    private Byte win;
	
	/**
     * 店铺名称
     */
    private String shopName;
    
	/**
     * 订单id
     */
    private String orderId;
    
    /**
     * 是否玩过游戏；1.未玩过；2玩过
     */
    private Byte orderType;
    
    /**
     * 支付方式（010微信，020 支付宝，060 qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码，000趣免单支付）
     */
    private String payChannel;
}
