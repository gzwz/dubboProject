package cn.qumiandan.coupon.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.coupon.api.IConponRulesService;
import cn.qumiandan.coupon.entity.CouponRules;
import cn.qumiandan.coupon.mapper.CouponRulesMapper;
import cn.qumiandan.coupon.vo.CouponRulesVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = IConponRulesService.class)
public class ConponRulesServiceImpl<T> extends ServiceImpl<CouponRulesMapper, CouponRules> implements IConponRulesService {
	
	@Override
	public CouponRulesVO addRules(CouponRulesVO vo) {
		AssertUtil.isNull(vo, "参数不能为空");
		CouponRules entity = CopyBeanUtil.copyBean(vo, CouponRules.class);
		entity.setStatus(StatusEnum.normal.getCode());
		entity.setCreateDate(new Date());
		this.save(entity);
		CouponRulesVO target = CopyBeanUtil.copyBean(entity, CouponRulesVO.class);
		System.out.println(entity);
		return target;
	}

	@Override
	public List<CouponRulesVO> getAllRules(StatusEnum status) {
		QueryWrapper<CouponRules> queryWrapper = new QueryWrapper<CouponRules>();
		if (status == null) {
			queryWrapper.eq("status", StatusEnum.normal.getCode());
		}else {
			queryWrapper.eq("status", status.getCode());
		}
		List<CouponRules> list = this.list(queryWrapper);
		if (list == null) {
			return null;
		}
		List<CouponRulesVO> target = CopyBeanUtil.copyList(list, CouponRulesVO.class);
		return target;
	}

	@Override
	public CouponRulesVO getOneRule(Long id,StatusEnum status) {
		QueryWrapper<CouponRules> queryWrapper = new QueryWrapper<>() ;
		if (status == null) {
			queryWrapper.eq("status", StatusEnum.normal.getCode());
		}else {
			queryWrapper.eq("status", status.getCode());
		}
		queryWrapper.eq("id", id);
		CouponRules rules = this.getOne(queryWrapper); 
		if (rules == null) {
			return null;
		}
		CouponRulesVO result = CopyBeanUtil.copyBean(rules, CouponRulesVO.class);
		return result;
	}

}
