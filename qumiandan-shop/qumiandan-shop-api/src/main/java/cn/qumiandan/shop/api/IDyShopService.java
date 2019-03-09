package cn.qumiandan.shop.api;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.shop.vo.DLQueryShopVO;
import cn.qumiandan.shop.vo.ShopStatisticVO;
import cn.qumiandan.shop.vo.ShopVO;

public interface IDyShopService {

	/**
	 * 根据业务员id查询该业务员的店铺列表
	 * @param salemanId
	 * @return
	 */
	ShopStatisticVO getShopNumBySalemanId(Long salemanId);

	/**
	 * 代理查询自己管辖下的店铺,返回分页
	 * @param dyUserId
	 */
	PageInfo<ShopVO> getShopPageByDyId(DLQueryShopVO vo);
	
	/**
	 * 代理查询自己管辖下的店铺，返回list
	 * @param dyUserId
	 */
	List<ShopVO> getShopListByDyId(Long dyUserId);
}
