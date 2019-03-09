package cn.qumiandan.sellerIndexData.api;

import cn.qumiandan.sellerIndexData.vo.GetAllDataParamsVO;
import cn.qumiandan.sellerIndexData.vo.GetAllDataVO;

public interface ISellerBackGroundDataService {
	
	/**
	 * 商家后台统计数据
	 * @param allDataParamsVO
	 * @return
	 */
	GetAllDataVO getAllData (GetAllDataParamsVO allDataParamsVO);
}
