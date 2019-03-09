package cn.qumiandan.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.shop.entity.Shop;
import cn.qumiandan.shop.mapper.ShopMapper;
import cn.qumiandan.shop.service.IShopService;

/**
 * 店铺service
 * @author yuleidian
 * @date 2019年1月25日
 */
@Service
public class ShopServiceImpl implements IShopService {

	@Autowired
	private ShopMapper shopMapper;
	
	@Override
	public List<Shop> getAllShop() {
		return shopMapper.selectList(new QueryWrapper<Shop>());
	}
	
}
