package cn.qumiandan.shoppingcart.api;

import java.util.List;

import cn.qumiandan.shoppingcart.vo.ShopingCartDetailVO;
import cn.qumiandan.shoppingcart.vo.ShoppingCartVO;

public interface IShoppingCartService { 

	/**
	 * 添加购物车
	 * @param shoppingCartVO
	 * @return
	 */
	int addShopingCart(ShoppingCartVO shoppingCartVO);
	
	/**
	 * 更新购物车
	 * @param shoppingCartVO
	 * @return
	 */
//	int updateShopingCart(ShoppingCartVO shoppingCartVO);
	
	/**
	 * 根据主键删除购物车
	 * @param id
	 * @return
	 */
	int deleteShopingCart(Long id);
	
	
	/**
	 * 根据用户id查询购物车
	 * @param userId
	 * @return
	 */
	List<ShopingCartDetailVO> getShopingCartByUserId(Long userId);
	
}
