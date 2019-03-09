package cn.qumiandan.shoppingcart.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.product.vo.ProductBasicVO;
import cn.qumiandan.shoppingcart.api.IShoppingCartService;
import cn.qumiandan.shoppingcart.entity.ShoppingCart;
import cn.qumiandan.shoppingcart.mapper.ShoppingCartMapper;
import cn.qumiandan.shoppingcart.vo.ShopingCartDetailVO;
import cn.qumiandan.shoppingcart.vo.ShoppingCartVO;
import cn.qumiandan.utils.CopyBeanUtil;

/**
 * 购物车管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IShoppingCartService.class)
public class ShoppingCartServiceImpl implements IShoppingCartService
{
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;

	/**
	 * 添加购物车
	 * @param shoppingCartVO
	 * @return
	 */
	@Override
	public int addShopingCart(ShoppingCartVO shoppingCartVO) {
		ShoppingCart shoppingCart = shoppingCartMapper.getProductByUserIdAndProductId(shoppingCartVO.getUserId(), shoppingCartVO.getProductId());
		int i = 0;	
		if(shoppingCart==null) {
			//用户的购物车中不存在该商品，则在新加一条数据
			shoppingCartVO.setCreateDate(new Date());
			shoppingCart = CopyBeanUtil.copyBean(shoppingCartVO, ShoppingCart.class);
			i = shoppingCartMapper.insert(shoppingCart);
		}else {
			//用户的购物车中存在该商品，则该商品的数量=原有的数量+新加的数量
			shoppingCart.setCreateDate(new Date());
			shoppingCart.setNumber(shoppingCart.getNumber()+shoppingCartVO.getNumber());
			i = shoppingCartMapper.updateById(shoppingCart);
		}
		return i;
	}

	/**
	 * 更新购物车
	 * @param shoppingCartVO
	 * @return
	 */
//	@Override
//	public int updateShoppingCart(ShoppingCartVO shoppingCartVO) {
//		ShoppingCart shopingCart = new ShoppingCart();
//		CopyBeanUtil.copyBean(shoppingCartVO, shoppingCart);
//		return shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
//	}

	/**
	 * 根据主键删除购物车
	 * @param id
	 * @return
	 */
	@Override
	public int deleteShopingCart(Long id) {
		return shoppingCartMapper.deleteById(id);
	}

	/**
	 * 根据用户id查询购物车
	 * @param userId
	 * @return
	 */
	@Override
	public List<ShopingCartDetailVO> getShopingCartByUserId(Long userId) {
		//查询购物车中所有店铺
		List<ShopingCartDetailVO> shopingCartDetailList =  shoppingCartMapper.getShopListByUserId(userId);
		//查询购物车中所有商品
		List<ProductBasicVO> allProductList = shoppingCartMapper.getProductByUserId(userId);
		if(shopingCartDetailList.size() <= 0) {
			return null;
		}
		//给商品匹配店铺
		for(ShopingCartDetailVO shopingCartDetailVO : shopingCartDetailList) {
			List<ProductBasicVO> productList = new ArrayList<ProductBasicVO>();
			for(ProductBasicVO productVO : allProductList) {
				if(shopingCartDetailVO.getId().equals(productVO.getShopId()) ) {
					productList.add(productVO);
				}
			}
			shopingCartDetailVO.setProductList(productList);
		}
		return shopingCartDetailList;
	}

}
