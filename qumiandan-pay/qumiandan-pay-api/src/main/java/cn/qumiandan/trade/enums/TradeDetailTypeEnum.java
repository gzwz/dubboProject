package cn.qumiandan.trade.enums;

import lombok.Getter;

/**
 * 流水类型
 * @author yuleidian
 * @version 创建时间：2018年12月31日 下午6:11:10
 */
@Getter
public enum TradeDetailTypeEnum {

	/** 店铺普通订单类型*/
	SHOPORDER(new Byte("1"), "店铺普通订单"),
	/** 店铺已核销普通订单*/
	SHOPORDERVT(new Byte("2"), "店铺已核销普通订单入账"),
	/** 游戏订单类型*/
	SHOPGAMEORDER(new Byte("3"), "店铺游戏订单入账"),
	/** 业务员普通订单分润*/
	SALEMANORDER(new Byte("4"), "业务员普通订单分润"),
	/** 区级普通订单分润*/
	COUNTRYORDER(new Byte("5"), "区级代理普通订单分润"),
	/** 市级普通订单分润*/
	CITYORDER(new Byte("6"), "市级代理普通订单分润"),
	/** 省级普通订单分润*/
	PROVINCEORDER(new Byte("7"), "省级代理普通订单分润"),
	/** 平台普通订单分润*/
	PALFORMORDER(new Byte("8"), "平台扣除店铺普通订单手续费"),
	/** 业务员游戏订单分润*/
	SALEMANGAMEORDER(new Byte("9"), "业务员游戏订单分润"),
	/** 区级游戏订单分润*/
	COUNTRYGAMEORDER(new Byte("10"), "区级代理游戏订单分润"),
	/** 市级游戏订单分润*/
	CITYGAMEORDER(new Byte("11"), "市级代理游戏订单分润"),
	/** 省级游戏订单分润*/
	PROVINCEGAMEORDER(new Byte("12"), "省级代理游戏订单分润"),
	/** 平台游戏订单分润*/
	PALFORMGAMEORDER(new Byte("13"), "平台扣除店铺游戏订单手续费"),
	/** 普通订单退款类型*/
	SHOPORDERREFUND(new Byte("14"), "店铺已核销普通订单退款"),
	/** 游戏订单退款类型*/
	//GAMEORDERREFUND(new Byte("15"), "游戏订单退款"),
	/** 业务员分润退款类型*/
	SALEMANREFUND(new Byte("16"), "业务员分润退款"),
	/** 区级分润退款类型*/
	COUNTRYREFUND(new Byte("17"), "区级代理分润退款"),
	/** 市级分润退款类型*/
	CITYREFUND(new Byte("18"), "市级代理分润退款"),
	/** 省级分润退款类型*/
	PROVINCEREFUND(new Byte("19"), "省级代理分润退款"),
	/** 平台退款分润回账*/
	PALFORMORDERREFUND(new Byte("20"), "平台退回收取店铺已核销订单手续费"),
	/** 店铺提现*/
	SHOPWITHDRAWCASH(new Byte("21"), "店铺提现"),
	/** 业务员提现*/
	SALEMANWITHDRAWCASH(new Byte("22"), "业务员提现"),
	/** 区级提现*/
	COUNTRYWITHDRAWCASH(new Byte("23"), "区级代理提现"),
	/** 市级提现*/
	CITYWITHDRAWCASH(new Byte("24"), "市级代理提现"),
	/** 省级提现*/
	PROVINCEWITHDRAWCASH(new Byte("25"), "省级代理提现"),
	/** 平台收取提现手续费*/
	PLATFORMWITHDRAWFEE(new Byte("26"), "平台扣除提现手续费"),
	/** 业务员资格卷返现*/
	SALEMANCASHBACK(new Byte("27"), "业务员资格卷返现"),
	
	//----------------------- 实际入账金额 = 平台收取手续费 - 各级代理吃的钱   -----------------------------------
	/** 平台普通已核销订单实际入账*/
	PLATFORMORDERACTUAL(new Byte("28"), "平台普通核销订单实际入账"),
	/** 平台游戏订单实际入账*/
	PLATFORMGAMEORDERACTUAL(new Byte("29"), "平台游戏订单实际入账"),
	/** 平台普通已核销订单实际退款*/
	PLATFORMORDERACTUALREFUND(new Byte("30"), "平台普通已核销订单实际退款"),
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	TradeDetailTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
