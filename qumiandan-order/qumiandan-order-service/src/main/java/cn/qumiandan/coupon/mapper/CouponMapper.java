package cn.qumiandan.coupon.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.coupon.entity.Coupon;
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {

	/**
	 * 查询用户可使用的优惠券
	 * @param userId
	 * @param publisher
	 * @param currentDate
	 * @return
	 */
	List<Coupon> queryUsefulCoupon(@Param("userId")Long userId,@Param("publisherList")List<Long> publisherList, @Param("currentDate")Date currentDate);
}
