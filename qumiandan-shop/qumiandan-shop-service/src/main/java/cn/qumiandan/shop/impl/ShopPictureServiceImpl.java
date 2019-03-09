package cn.qumiandan.shop.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.qumiandan.shop.api.IShopPictureService;
import cn.qumiandan.shop.entity.ShopPicture;
import cn.qumiandan.shop.mapper.ShopPictureMapper;
import cn.qumiandan.shop.vo.ShopPictureVO;

@Component
@Service(interfaceClass = IShopPictureService.class)
public class ShopPictureServiceImpl extends ServiceImpl<ShopPictureMapper, ShopPicture> implements IShopPictureService {

	@Autowired
	private ShopPictureMapper shopPictureMapper;
	
	@Override
	public List<ShopPictureVO> getShopPictureByShopIdAndTypes(Long shopId, List<Byte> types) {
		return shopPictureMapper.getShopPictureByShopIdAndTypes(shopId, types);
	}
	
	
	/**
	 * 根据店铺id查询店铺图片
	 * @param shopId
	 * @return
	 */
	@Override
	public List<ShopPictureVO> getShopPictureByShopId(Long shopId) {
		return shopPictureMapper.getShopPictureByShopIdAndTypes(shopId, null);
	}
}
