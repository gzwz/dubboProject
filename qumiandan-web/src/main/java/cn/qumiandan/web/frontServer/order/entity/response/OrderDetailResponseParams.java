package cn.qumiandan.web.frontServer.order.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.qumiandan.order.constant.OrderStatusEnum;
import cn.qumiandan.order.vo.OrderDetailVO;
import cn.qumiandan.order.vo.OrderQueryVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 查询订单详情响应参数
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class OrderDetailResponseParams extends OrderQueryVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
     * 订单id
     */
    private String orderId;

    /**
     * 商店编号
     */
    private Long shopId;
    
    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 订单类型（1.普通支付，2.趣免单）
     */
    private Byte orderType;

    /**
     * 订单状态(1.未付款；2.支付中;3.游戏支付;4.已付款;5.交易完成(已消费);6.退款申请中;7.已退款;8.拒绝退款;9.取消交易;10.已删除;)
     * {@link OrderStatusEnum}
     */
    private Byte orderStatus;
    
    /**
     * 是否中奖；1未中奖；2.中奖；
     */
    private Byte win;

    /**
     * 订单名称
     */
    private String title;

    /**
     * 是否开票（是否开具发票）（1.是,2.否）
     */
    private Byte billing;
    
    /**
     * 订单应付金额
     */
    private BigDecimal needPayAmount;

    /**
     * 商品总价
     */
    private BigDecimal productAmountTotal;

    /**
     * 运费金额
     */
    private BigDecimal logisticsFee;

    /**
     * 游戏实付
     */
    private BigDecimal gameAmount;
    
    /**
     * 订单金额(实际付款金额)
     */
    private BigDecimal amountTotal;
    
    /**
     * 折扣比例
     */
    private BigDecimal discountRate;

    /**
     * 折扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 支付方式（010微信，020 支付宝，060 qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码，000趣免单支付）
     */
    private String payChannel;

    /**
     * 支付时间
     */
    private Date payDate;

    /**
     * 订单结算状态（1.在线支付，2.线下支付，3货到付款，4.分期付款）
     */
    private Byte orderSettlementStatus;

    /**
     * 订单结算时间
     */
    private Date orderSettlementTime;

    /**
     * 省
     */
    private String proCode;

    /**
     * 市
     */
    private String cityCode;

    /**
     * 县
     */
    private String countyCode;

    /**
     * 订单支付流水号
     */
    private String outTradeNo;
    
    /** 第三方交易订单号 通道订单号，微信订单号、支付宝订单号等*/
	private String channelTradeNo;

    /**
     * 业务员id
     */
    private Long salesmanId;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;
    
    /**
     * 订单商品list
     */
    private List<OrderDetailVO> orderDetailVOList;
    
	private String logo;	//店铺logo-url	
	
	private String longitude;	//经度

	private String latitude;	//纬度

	private String address;	//详细地址

	private String phone;	//店铺联系电话
	
    /**
     * 账号
     */
    private String userName;
    
    private Byte gameSwitch; //否开启游戏支付（1.是，0.否）
   
    
    /**游戏订单*/
//    private List<GameExtendVO> gameOrderExtendList;

}
