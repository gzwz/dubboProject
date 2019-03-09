package cn.qumiandan.shop.service;

import java.util.List;

import cn.qumiandan.shop.entity.Shop;

/**
 * shop service
 * @author yuleidian
 * @date 2019年1月25日
 */
public interface IShopService {

	/**
	 * 获取所有店铺信息
	 * @return
	 */
	List<Shop> getAllShop();
}
