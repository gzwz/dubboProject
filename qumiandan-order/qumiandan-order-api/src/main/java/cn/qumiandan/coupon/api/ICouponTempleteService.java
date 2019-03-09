package cn.qumiandan.coupon.api;

import java.util.List;

import cn.qumiandan.coupon.vo.CouponTempleteVO;

/**
 * 优惠券模板服务api
 * @author WLZ
 * 2018年12月3日
 */
public interface ICouponTempleteService {
	

	/**
	 * 添加优惠券模板
	 * @param vo
	 * @throws OrException
	 */
	CouponTempleteVO addTemplete(CouponTempleteVO vo);

	/**
	 * 根据id查询优惠券模板
	 * @param vo
	 * @throws OrException
	 */
	CouponTempleteVO getTempleteForId(Long id);

	/**
	 * 店铺查询可用优惠券模板
	 * @param vo
	 * @throws OrException
	 */
	List<CouponTempleteVO> getTempleteForShop();
	
	/**
	 * 查询所有优惠券模板
	 * @param vo
	 * @throws OrException
	 */
	List<CouponTempleteVO> getTempleteForAll();
}
