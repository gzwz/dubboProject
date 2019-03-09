package cn.qumiandan.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.coupon.entity.CouponTakeRecord;
import cn.qumiandan.coupon.vo.CouponTakeRecordVO;
import cn.qumiandan.coupon.vo.QueryCouponTakeRecordParamsVO;
import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface CouponTakeRecordMapper extends BaseMapper<CouponTakeRecord> {

	List<CouponTakeRecordVO> test(@Param("paramsVO")QueryCouponTakeRecordParamsVO paramsVO);
}
