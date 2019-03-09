package cn.qumiandan.sellerIndexData.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.qumiandan.sellerIndexData.vo.GetAllDataParamsVO;
import cn.qumiandan.sellerIndexData.vo.GetAllDataVO;


@Mapper
public interface SellerBackGroundDataMapper {
	/**
	 * 商家后台统计数据
	 * @param allDataParamsVO
	 * @return
	 */
	GetAllDataVO getAllData (@Param ("allDataParamsVO") GetAllDataParamsVO allDataParamsVO);

	/**
	 * 根据店铺id集合查询账户id集合
	 * @param shopIds
	 * @return
	 */
	List<Long> getAccountIdsByShopIds(@Param ("shopIds") List<Long> shopIds);
}
