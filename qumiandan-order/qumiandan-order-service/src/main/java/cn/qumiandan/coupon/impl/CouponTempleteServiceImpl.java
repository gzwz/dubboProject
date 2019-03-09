package cn.qumiandan.coupon.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.coupon.api.ICouponTempleteService;
import cn.qumiandan.coupon.constant.CouponEnum;
import cn.qumiandan.coupon.entity.CouponTemplete;
import cn.qumiandan.coupon.mapper.CouponTempleteMapper;
import cn.qumiandan.coupon.vo.CouponTempleteVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = ICouponTempleteService.class)
public class CouponTempleteServiceImpl extends ServiceImpl<CouponTempleteMapper, CouponTemplete> implements ICouponTempleteService {

	@Autowired
	private CouponTempleteMapper mapper;

	@Override
	public CouponTempleteVO addTemplete(CouponTempleteVO vo) {
		AssertUtil.isNull(vo, "参数不能为空");
		CouponTemplete entity = CopyBeanUtil.copyBean(vo, CouponTemplete.class);
		entity.setStatus(StatusEnum.normal.getCode());
		entity.setCreateDate(new Date());
		// 处理名字
		/*if (StringUtil.isEmpty(entity.getName())) {
			entity.setName(entity.get);
		}*/
		this.save(entity);
		vo.setId(entity.getId());
	    return vo;
	}

	@Override
	public List<CouponTempleteVO>  getTempleteForShop() {
		QueryWrapper<CouponTemplete> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("use_range", CouponEnum.Shop.getCode());
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		
		List<CouponTemplete> selectList = mapper.selectList(queryWrapper);
		List<CouponTempleteVO> target = CopyBeanUtil.copyList(selectList, CouponTempleteVO.class);
		return target;
	}

	@Override
	public List<CouponTempleteVO>  getTempleteForAll() {
		QueryWrapper<CouponTemplete> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("use_range", CouponEnum.Shop.getCode(),
				CouponEnum.All.getCode(),
				CouponEnum.Category.getCode());
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		
		List<CouponTemplete> selectList = mapper.selectList(queryWrapper);
		if (null == selectList) {
			return null;
		}
		List<CouponTempleteVO> target = CopyBeanUtil.copyList(selectList, CouponTempleteVO.class);
		return target;
	}

	@Override
	public CouponTempleteVO getTempleteForId(Long id) {
		QueryWrapper<CouponTemplete> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		CouponTemplete selectOne = mapper.selectOne(queryWrapper);
		if (null == selectOne) {
			return null;
		}
		CouponTempleteVO target = CopyBeanUtil.copyBean(selectOne, CouponTempleteVO.class );
		return target;
	}
}
