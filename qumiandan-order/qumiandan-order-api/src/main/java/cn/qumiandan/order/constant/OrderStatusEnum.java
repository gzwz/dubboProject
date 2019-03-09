package cn.qumiandan.order.constant;

/**
 * 使用范围（1.全平台，2.类目，3.店铺）
 * @author WLZ
 * 2018年12月5日
 */
public enum OrderStatusEnum {
 
	/** 未付款 */
	NotPay((byte)1, "未付款"),
	/** 支付中 */
	Paying((byte)2, "支付中"),
	/** 游戏支付中*/
	GamePayed((byte)3, "游戏支付"),
	/** 已付款 */
	Paid((byte)4, "已付款"),
	/** 交易完成  已消费 */
	TradeComplete((byte)5, "交易完成(已消费)"),
	/** 退款申请 */
	RefundApply((byte)6, "退款申请中"),
	/** 已退款 */
	Refunded((byte)7, "已退款"),
	/** 拒绝退款 */
	RefuseRefund((byte)8, "拒绝退款"),
	/** 取消交易 */
	TradeClose((byte)9, "取消交易"),
	/** 已删除 */
	Deleted((byte)10, "已删除"),
	
	/** 已发货 */
	// Delivered ((byte)5, "已发货"),
	/** 已退货 */
	//RefundComplete((byte)9, "已退货"),
	;
	
	private Byte code;
	
	private String desc;

	private OrderStatusEnum(Byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
