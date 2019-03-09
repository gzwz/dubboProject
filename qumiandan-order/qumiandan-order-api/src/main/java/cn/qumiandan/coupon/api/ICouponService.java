package cn.qumiandan.coupon.api;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.coupon.vo.CouponTakeRecordVO;
import cn.qumiandan.coupon.vo.CouponUseRecordVO;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.coupon.vo.GetCouponResponseParamsVO;
import cn.qumiandan.coupon.vo.QueryCouponParamsVO;
import cn.qumiandan.coupon.vo.QueryCouponTakeRecordParamsVO;
import cn.qumiandan.coupon.vo.QueryUsefulCouponParamsVO;

/**
 * 优惠券服务api
 * @author WLZ
 * 2018年11月22日
 */
public interface ICouponService {
	

	/**
	 * 创建优惠券
	 * @param vo
	 * @throws OrException
	 */
	CouponVO createCoupon(CouponVO vo);
	
	/**
	 * 店铺查询优惠券列表
	 * @param id 店铺id
	 * @return
	 */
	List<CouponVO> queryCouponForShop(Long shopId);
	
	/**
	 * 根据优惠券id查询优惠券
	 * @param id 优惠券id
	 * @return
	 */
	CouponVO queryCouponById(Long couponId);
	
	/**
	 * 领取优惠券
	 * @param id 优惠券id
	 * @param userId 用户id
	 * @return
	 */
	CouponTakeRecordVO takeCouponForUser(Long couponId,Long userId);
	
	/**
	 * 查询该用户是否持有对应的优惠券 <br>
	 * 已经使用过，或过期，或者删除的 的不计算在内
	 * @param id 优惠券id
	 * @param userId 用户id
	 * @return
	 */
	List<CouponTakeRecordVO> queryTakeCouponRecord(Long couponId,Long userId);
	
	/**
	 * 将对应的优惠券设置为已经使用 <br>
	 * @param id 优惠券id
	 * @param userId 用户id
	 * @return
	 */
	void useCouponRecord(CouponUseRecordVO recordVO);
	
	/**
	 * 增加优惠券数量
	 * @param couponId
	 * @param number
	 * @return
	 */
	CouponVO increaseCouponNum(Long couponId,Long number);
	
	/**
	 * 修改优惠券排除的商品集合
	 * @param couponId
	 * @param excludeProductIds
	 * @return
	 */
	CouponVO updateCouponProduct(Long couponId,List<Long> excludeProductIds);
	
	/**
	 * 根据店铺id和用户id查询优惠券
	 * @param shopId
	 * @param userId
	 * @return
	 */
	List<GetCouponResponseParamsVO> getCouponByShopIdAndUserId(Long shopId,Long userId); 

	/**
	 * 根据优惠券id删除优惠券
	 * @param couponId
	 * @param publisher
	 * @return
	 */
	Integer deleteCouponByCouponId(Long couponId, Long publisher);
	
	/**
	 * 根据条件查询领取记录
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<CouponTakeRecordVO> getCouponTakeRecord(QueryCouponTakeRecordParamsVO params,int pageNum,int pageSize);

	/**
	 * 总后台查询优惠券
	 * @param paramsVO
	 * @return
	 */
	PageInfo<CouponVO> backstageQueryCoupon( QueryCouponParamsVO paramsVO );
	
	/**
	 * 查询用户可使用的优惠券
	 * @param couponParamsVO
	 */
	PageInfo<CouponVO>  queryUsefulCoupon(QueryUsefulCouponParamsVO couponParamsVO);
	
}
