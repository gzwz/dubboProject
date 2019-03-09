package cn.qumiandan.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_shop_extend")
public class ShopExtend implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
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
     * 开户行
     */
    private Long bankId;

    /**
     * 持卡人
     */
    private String cardHolder;

    /**
     * 法人
     */
    private String corporation;
    
	/**
	 * 法人身份证号
	 */
	private String legalIdNum;

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
    private Byte isOpenShut;

    /**
     * 总营业额
     */
    private BigDecimal totalIncome;

    private Integer membersLimit;	//成员数量限制
    
	private String supportPayment;	//支付方式
	
	private String merchantNo;//商户号
	
	private String terminalId; //设备id
	
	private String accessToken;//访问令牌
	
	private  Long salesVolume;//销量
	
	private BigDecimal hotExponent;//热门指数
	
	/**
	 * 人均消费（单位：分）
	 */
	private BigDecimal perCapitaConsume;
	
	/**
	 * 游戏支付开关（1表示开启，0表示关闭）
	 */
	private Byte gameSwitch;
	
}