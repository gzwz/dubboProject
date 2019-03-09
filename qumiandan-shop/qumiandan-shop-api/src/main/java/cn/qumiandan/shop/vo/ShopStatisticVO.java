package cn.qumiandan.shop.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 业务员端首页统计数据
 * @author lrj
 *
 */
@Data
public class ShopStatisticVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 营业员提交待审核店铺数量
	 */
	private Integer createCommitShopNum;
	
	/**
	 * 店铺审核未通过店铺数量
	 */
	private Integer unpassShopNum;
	
	/**
	 * 店铺审核通过店铺数量
	 */
	private Integer passShopNum;
	
	/**
	 * 更新提交待审核
	 */
	private Integer localUpdateCommitShopNum;
	
	/**
	 * 更新审核未通过(审核通过为7)
	 */
	private Integer localUpdateUnpassShopNum;
	
	/**
	 * 主动申请关闭
	 */
	private Integer applyCloseShopNum;
	
	/**
	 * 平台强制关闭
	 */
	private Integer platformCloseShopNum;
	
	/**
	 * 今日店铺数量
	 */
	private Integer todayShopNum;
	
	/**
	 * 本周店铺数量
	 */
	private Integer currentWeekShopNum;
	
	/**
	 * 当月店铺数量
	 */
	private Integer currentMothShopNum;
	
}
