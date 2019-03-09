package cn.qumiandan.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.qumiandan.order.constant.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 根据店铺id集合查询店铺参数类
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class OrderQueryParamsVO   implements Serializable{
	  
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
    /**
     * 用户手机号
     */
    private String userName;
    
    /**
     * 店铺名
     */
    private String shopName;
    
    /**
     * 支付流水号
     */
    private String outTradeNo;
	
	/**
	 * 店铺id集合
	 */
	private List<Long> shopIdList;
	
    /**
     * 订单状态(1.未付款；2.支付中;3.游戏支付;4.已付款;5.交易完成(已消费);6.退款申请中;7.已退款;8.拒绝退款;9.取消交易;10.已删除;)
     * {@link OrderStatusEnum}
     */
    private List<Byte> statusList;
	
	
	/**
     * 是否中奖；1未中奖；2.中奖；
     */
    private List<Byte> winList ;
    
    /**
     * 是否玩过游戏；1.未玩过；2玩过
     */
    private List<Byte> orderTypeList;
    
    /**
     * 支付方式（010微信，020 支付宝，060 qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码，000趣免单支付）
     */
    private List<String> payChannelList;
	
	/**
	 * 订单创建时间查询条件（开始时间）
	 */
	private Date startDate;
	
	/**
	 * 订单创建时间查询条件（结束时间）
	 */
	private Date endDate;
}
