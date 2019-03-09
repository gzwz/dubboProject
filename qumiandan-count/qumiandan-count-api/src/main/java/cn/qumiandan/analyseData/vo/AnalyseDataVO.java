package cn.qumiandan.analyseData.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
/**
 * 数据分析VO
 * @author W
 *
 */
@Data
public class AnalyseDataVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 实收金额数量
	 */
	private BigDecimal actualAmount;
	/**
	 * 访问量
	 */
	private Long visitNum;
	/**
	 * 支付笔数
	 */
	private Long payNum;
	/**
	 * 入驻商家数量
	 */
	private BigDecimal enterSeller;
	/**
	 * 新增用户数量
	 */
	private Long newUser;
	/**
	 * 新增手机号码数量
	 */
	private Long newPhone;
	/**
	 * 下单用户数量
	 */
	private BigDecimal orderUserNum;
	/**
	 *新增业务员数量
	 */
	private Integer saleManNum;
	
}
