package cn.qumiandan.shop.api;

import cn.qumiandan.shop.vo.ShopExtendVO;

public interface IShopGameSwitchService {
	/**
	 * 店铺游戏支付开关接口
	 * @param shopExtendVO 店铺扩展信息vo
	 * @return
	 */
	public Integer gameSwitch(Long shopId,Byte gameSwitch);
	/**
	 * 检查店铺游戏支付开关是否开启的接口
	 * @param extendVO 店铺扩展信息vo
	 * @return
	 */
	/* boolean checkSwitch(Long shopId); */
	/**
	 * 通过店铺id获取店铺扩展表信息
	 * @param shopId
	 * @return
	 */
	ShopExtendVO getShopExtendByShopId(Long shopId);
}
