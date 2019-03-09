package cn.qumiandan.pay.enums;

import cn.qumiandan.common.interfaces.IStringEnum;

/**
 * 扫呗提供的支付方式
 * @author WLZ
 * 2018年12月12日
 */
public enum PayTypeEnum implements IStringEnum {
	
	//010微信，020 支付宝，060 qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码
	/**趣免单支付*/
	QMDPay("000","趣免单支付"),
	/**微信*/
	WeChatPay("010","微信"),
	/**支付宝*/
	AliPay("020","支付宝"),
	/**qq钱包*/
	QQPay("060","qq钱包"),
	/**京东钱包*/
	JDPay("080","京东钱包"),
	/**口碑*/
	KBPay("090","口碑"),
	/**翼支付*/
	Bestpay("100","翼支付"),
	/**银联二维码*/
	UnionQRPay("110","银联二维码"),
	/**平台内部转账*/
	PlatformPay("999", "平台内部转账"),
	/**连连付款*/
	LianLianPay("888", "连连付款"),
	;
	 
	private String code;
	
	private String name;

	PayTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
