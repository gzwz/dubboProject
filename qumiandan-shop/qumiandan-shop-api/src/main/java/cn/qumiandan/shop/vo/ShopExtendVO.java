package cn.qumiandan.shop.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 店铺拓展信息vo
 * @author W
 *
 */
@Data
public class ShopExtendVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id	
	 */
	private Long id;
	/**
	 * 店铺编号
	 */
	private Long shopId;
	/**
	 * 银行卡号
	 */
	private String bankAccount;
	/**
	 * 开户行id
	 */
	private Long bankId;
	/**
	 * 持卡人
	 */
	private String cardHolder;
	
	/**
	 * 法人身份证号
	 */
	private String legalIdNum;
	
	/**
	 * 法人
	 */
	private String corporation;
	/**
	 * 最大扫码付款金额
	 */
	private BigDecimal maxScan;
	/**
	 * 最大商品金额
	 */
	private BigDecimal maxSale;
	/**
	 * 最大免单金额
	 */
	private BigDecimal maxFree;
	/**
	 * 起送金额
	 */
	private BigDecimal dispatchBeginMoney;
	/**
	 * 店铺营业起始时间
	 */
	private Date openTime;
	/**
	 * 店铺营业结束时间
	 */
	private Date restTime;
	/**
	 * 特色信息
	 */
	private String item;
	/**
	 * 是否开启打烊(1:是；0：否)
	 */
	private int isOpenShut;
	/**
	 * 总营业额
	 */
	private BigDecimal totalIncome;
	/**
	 * 成员数量限制
	 */
	private int membersLimit;
	/**
	 * 支付方式
	 */
	private String supportPayment;
	/**
	 * 商户号
	 */
	private String merchantNo;
	/**
	 * 设备id
	 */
	private String terminalId;
	/**
	 * 访问令牌
	 */
	private String accessToken;
	/**
	 * 销量
	 */
	private Long salesVolume;
	/**
	 * 热门指数
	 */
	private BigDecimal hotExponent;
	
	/**
	 * 人均消费（单位：分）
	 */
	private BigDecimal perCapitaConsume;
	
	/**
	 * 游戏开关，表明该店铺是否开启游戏支付（1.是，0.否）
	 */
	private Byte gameSwitch;
}
