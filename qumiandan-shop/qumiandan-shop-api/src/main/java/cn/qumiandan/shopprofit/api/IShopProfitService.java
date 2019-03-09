package cn.qumiandan.shopprofit.api;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.shopprofit.vo.ShopProfitDetailVO;
import cn.qumiandan.shopprofit.vo.ShopProfitVO;

/**
 * 店铺分润信息管理
 * @author lrj
 *
 */
public interface IShopProfitService extends IBaseService {

	
	/**
	 * 更新店铺分润信息
	 * @param shopProfitVO
	 * @return
	 */
	int updateShopProfit(ShopProfitVO  shopProfitVO);
	
	
	/**
	 * 查询店铺费率 
	 * @param shopId
	 * @return
	 */
	ShopProfitDetailVO getShopProfitByShopId(Long shopId);
	
	/**
	 * 删除店铺分润表中店铺费率信息
	 * @param shopId
	 * @return
	 */
	int deleteShopProfit(Long shopId);
	
}
