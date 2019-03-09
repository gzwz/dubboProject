package cn.qumiandan.common;

/**
 * @author WLZ
 * 2018年11月6日
 */
public enum LogInfo {
	
	/**网关接口*/
	GW("GateWayService","网关接口"),

	/**用户中心*/
	UC("UserCenter","用户中心"),
	
	/**商城中心*/
	SC("ShopCenter","商城中心"),
	
	/**公共服务*/
	CS("CommonServer","公共服务"),
	
	/**订单服务*/
	OR("OrderServer","订单服务"),
	
	/**支付服务*/
	PAY("PayServer","支付服务"),
	
	/**统计服务*/
	Count("CountServer","统计服务"),
	
	
	/**接口入参*/
	InterfaceInParam("InterfaceInParam","接口输入参数："),
	/**接口入参*/
	InterfaceOutParam("InterfaceOutParam","接口输出结果："),
	/**接口发生异常*/
	InterfaceException("InterfaceException","接口发生异常："),
	
	
	;
	
	/** 前缀 */
	private String preFix;
	/** 说明 */
	private String name;
	
	private LogInfo(String preFix, String name) {
		this.preFix = preFix;
		this.name = name;
	}

	/** 前缀 */
	public String getPreFix() {
		return preFix;
	}

	/** 前缀 */
	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}

	/** 说明 */
	public String getName() {
		return name;
	}

	/** 说明 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
