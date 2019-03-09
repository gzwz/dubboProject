package cn.qumiandan.coupon.service.takeimpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.OrErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.coupon.entity.Coupon;
import cn.qumiandan.coupon.service.ICouponTakeRulesReader;
import cn.qumiandan.coupon.service.beanname.TakeRuleBeanNames;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.ObjectUtils;

@Component("TakeLimitNewUser")
public class CouponUseRulesLimitNewUserReaderImpl implements ICouponTakeRulesReader {
	
	@Reference
	private IUserService userService;
	
	@Autowired
	private IOrderService orderService;
	
	
	public void readRules(Coupon coupon, Long userId) {
		AssertUtil.isNull(coupon, "优惠券参数不能为空");
		AssertUtil.isNull(userId, "用户id不能为空");
		if (ObjectUtils.isBlank(coupon.getTakeRule())) {
			return ;
		}
		// 如果包含了 新用户领取规则,则继续后面的操作，否则返回
		if (!coupon.getTakeRule().contains(TakeRuleBeanNames.TakeLimitNewUser.name())) {
			return ;
		}
		// 判断优惠券是否在有效期内
		Date date = new Date();
		// 优惠券开始时间大于当前系统时间，说明该优惠券还未生效， 则直接抛异常
		if (coupon.getStartTime().getTime() > date.getTime()) {
			throw new QmdException(OrErrorCode.OR1015);
		}
		// 优惠券结束时间小于当前系统时间，说明该优惠券已经消失，无法领取， 则直接抛异常
		if (coupon.getEndTime().getTime() < date.getTime()) {
			throw new QmdException(OrErrorCode.OR1016);
		}
				
		// 获取用户信息，查看用户是否是新用户，此处只需要判断用户数是否已经有完成的订单
		UserVO userVO = userService.getUserById(userId);
		if (null == userVO) {
			throw new QmdException("用户不存在");
		}
		// 如果不是新用户则直接抛出异常，阻断领取
		if (!orderService.estimateUserHasOrder(userId)) {
			throw new QmdException(OrErrorCode.OR1020);
		}
		
		
	}
 

}
