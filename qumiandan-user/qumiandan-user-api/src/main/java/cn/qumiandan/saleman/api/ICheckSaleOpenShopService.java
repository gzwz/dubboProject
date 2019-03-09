package cn.qumiandan.saleman.api;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.saleman.vo.CheckSaleOpenShopVO;

public interface ICheckSaleOpenShopService {

	/**
	 * 检查业务员开店区域是否符合业务所在区域
	 * @param vo
	 * @return 符合条件返回true,不符合返回false
	 */
	Boolean checkSaleOpenShop(CheckSaleOpenShopVO vo) throws QmdException;
	
	
}
