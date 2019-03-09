package cn.qumiandan.coupon.api;

import java.util.List;

import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.coupon.vo.CouponRulesVO;

public interface IConponRulesService {

	/**
	 * 添加优惠券规则
	 * @param vo
	 * @return
	 */
	CouponRulesVO addRules(CouponRulesVO vo);
	
	/**
	 * 根据id获取优惠券规则
	 * @param status 1.正常，0.删除 不传默认返回所有 
	 * @return
	 * {@link StatusEnum}
	 */
	CouponRulesVO getOneRule(Long id,StatusEnum status);
	
	/**
	 * 获取优惠券规则 
	 * @param status 1.正常，0.删除 不传默认返回所有
	 * @return 
	 * {@link StatusEnum}
	 */
	List<CouponRulesVO> getAllRules(StatusEnum status);
}
