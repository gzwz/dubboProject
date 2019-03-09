package cn.qumiandan.shopmember.api;

import cn.qumiandan.shopmember.vo.VipDiscountInfoVO;

/**
 * 会员折扣管理接口
 * @author lrj
 *
 */
public interface IVipDiscountInfoService {

	/**
	 * 添加会员折扣信息
	 * @param discountInfoVO
	 * @return
	 */
	VipDiscountInfoVO addVipDiscountInfo(VipDiscountInfoVO discountInfoVO);
	
	/**
	 * 设置店铺会员折扣状态
	 * @param shopId
	 * @param status
	 * @return
	 */
	VipDiscountInfoVO setVipDiscountInfoStatus(Long shopId,Byte status);
	
	/**
	 * 根据店铺id查询会员折扣信息
	 * @param shopId
	 * @return
	 */
	VipDiscountInfoVO getVipDiscountInfoByShopId(Long shopId);
	
	/**
	 * 修改店铺会员折扣信息
	 * @param discountInfoVO
	 * @return
	 */
	VipDiscountInfoVO updateVipDiscountInfo(VipDiscountInfoVO discountInfoVO);
}
