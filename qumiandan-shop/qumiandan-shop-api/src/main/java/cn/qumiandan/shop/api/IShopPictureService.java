package cn.qumiandan.shop.api;

import java.util.List;

import cn.qumiandan.shop.vo.ShopPictureVO;

/**
 * 店铺图片service
 * @author yuleidian
 * @version 创建时间：2018年12月10日 下午3:10:55
 */
public interface IShopPictureService {

	/**
	 * 根据店铺id和类型查询 店铺相关图片信息
	 * @param shopId
	 * @param types
	 * @return
	 */
	List<ShopPictureVO> getShopPictureByShopIdAndTypes(Long shopId, List<Byte> types);
	
	/**
	 * 根据店铺id查询店铺图片
	 * @param shopId
	 * @return
	 */
	List<ShopPictureVO> getShopPictureByShopId(Long shopId);
}
