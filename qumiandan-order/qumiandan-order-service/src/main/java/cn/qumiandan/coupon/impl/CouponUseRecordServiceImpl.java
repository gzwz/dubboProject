package cn.qumiandan.coupon.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.coupon.api.ICouponUseRecordService;
import cn.qumiandan.coupon.entity.CouponUseRecord;
import cn.qumiandan.coupon.mapper.CouponUseRecordMapper;
import cn.qumiandan.coupon.vo.CouponUseRecordVO;
import cn.qumiandan.utils.CopyBeanUtil;
/**
 * 优惠券使用记录管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = ICouponUseRecordService.class)
public class CouponUseRecordServiceImpl implements ICouponUseRecordService{

	@Autowired
	private CouponUseRecordMapper useRecordMapper;
	
	/**
	 * 根据订单id查询优惠券使用记录
	 * @param orderId
	 * @return
	 */
	@Override
	public CouponUseRecordVO getCouponUseRecordByOrderId(String orderId) {
		CouponUseRecord couponUseRecord = useRecordMapper.selectOne(new QueryWrapper<CouponUseRecord>().eq("order_id", orderId)); 
		if(couponUseRecord == null) {
			return null;
		}
		return CopyBeanUtil.copyBean(couponUseRecord, CouponUseRecordVO.class);
	}

}
