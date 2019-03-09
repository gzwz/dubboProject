package cn.qumiandan.shopmember.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.shopmember.api.IVipDiscountInfoService;
import cn.qumiandan.shopmember.entity.VipDiscountInfo;
import cn.qumiandan.shopmember.mapper.VipDiscountInfoMapper;
import cn.qumiandan.shopmember.vo.VipDiscountInfoVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 会员折扣管理实现类
 * @author lrj
 *
 */

@Slf4j
@Component
@Service(interfaceClass = IVipDiscountInfoService.class)
public class VipDiscountInfoServiceImpl   implements IVipDiscountInfoService{
	
	@Autowired
	private VipDiscountInfoMapper vipDiscountInfoMapper;
	/**
	 * 添加会员折扣信息
	 * @param discountInfoVO
	 * @return
	 */
	@Override
	public VipDiscountInfoVO addVipDiscountInfo(VipDiscountInfoVO discountInfoVO) {
		AssertUtil.isNull(discountInfoVO, "参数不能为空");
		VipDiscountInfo discountInfo = vipDiscountInfoMapper.selectOne(new QueryWrapper<VipDiscountInfo>().eq("shop_id", discountInfoVO.getShopId()));
		int i = 0;
		if(discountInfo != null) {
			discountInfo.setType(discountInfoVO.getType());
			discountInfo.setDiscountMoney(discountInfoVO.getDiscountMoney());
			discountInfo.setStatus(discountInfoVO.getStatus());
			i = vipDiscountInfoMapper.updateById(discountInfo);
			discountInfoVO = CopyBeanUtil.copyBean(discountInfo, VipDiscountInfoVO.class);
		}else {
			discountInfoVO.setCreateDate(new Date());
			discountInfo = CopyBeanUtil.copyBean(discountInfoVO, VipDiscountInfo.class);
			discountInfo.setStatus(StatusEnum.normal.getCode());
			i = vipDiscountInfoMapper.insert(discountInfo);
			discountInfoVO.setId(discountInfo.getId());
		}
		if(i!=1) {
			log.error("设置会员折扣信息失败，添加数量不等于1");
			throw new QmdException("设置会员折扣信息失败");
		}
		return discountInfoVO;
	}
	
	/**
	 * 设置会员折扣启用/禁用（status=1启用；status=0禁用）
	 */
	@Override
	public VipDiscountInfoVO setVipDiscountInfoStatus(Long shopId, Byte status) {
		AssertUtil.isNull(shopId, "店铺id不能为空");
		AssertUtil.isNull(status, "会员折扣信息状态不能为空");
		VipDiscountInfo discountInfo = vipDiscountInfoMapper.selectOne(new QueryWrapper<VipDiscountInfo>().eq("shop_id",shopId));
		if(discountInfo == null) {
			log.error("店铺会员折扣信息不存在");
			throw new QmdException("店铺会员折扣信息不存在");
		}
		discountInfo.setStatus(status);
		vipDiscountInfoMapper.updateById(discountInfo);
		VipDiscountInfoVO discountInfoVO = CopyBeanUtil.copyBean(discountInfo, VipDiscountInfoVO.class);
		return discountInfoVO;
	}

	/**
	 * 根据店铺id查询会员折扣信息
	 * @param shopId
	 * @return
	 */
	@Override
	public VipDiscountInfoVO getVipDiscountInfoByShopId(Long shopId) {
		AssertUtil.isNull(shopId, "店铺id不能为空");
		VipDiscountInfo discountInfo = vipDiscountInfoMapper.selectOne(new QueryWrapper<VipDiscountInfo>().eq("shop_id",shopId));
		if(discountInfo == null || discountInfo.getStatus().equals(StatusEnum.FALSE.getCode())) {
			return null;
		}
		VipDiscountInfoVO discountInfoVO = CopyBeanUtil.copyBean(discountInfo, VipDiscountInfoVO.class);
		return discountInfoVO;
	}

	/**
	 * 修改店铺会员折扣信息
	 * @param discountInfoVO
	 * @return
	 */
	@Override
	public VipDiscountInfoVO updateVipDiscountInfo(VipDiscountInfoVO discountInfoVO) {
		AssertUtil.isNull(discountInfoVO, "店铺id不能为空");
		VipDiscountInfo discountInfo = vipDiscountInfoMapper.selectOne(new QueryWrapper<VipDiscountInfo>().eq("shop_id",discountInfoVO.getShopId()));
		if(discountInfo == null) {
			log.error("店铺会员折扣信息不存在");
			throw new QmdException("店铺会员折扣信息不存在");
		}
		discountInfo.setDiscountMoney(discountInfoVO.getDiscountMoney());
		int i = vipDiscountInfoMapper.updateById(discountInfo);
		if(i!=1) {
			log.error("修改会员折扣信息失败，修改数量不等于1");
			throw new QmdException("修改会员折扣信息失败");
		}
		discountInfoVO = CopyBeanUtil.copyBean(discountInfo, VipDiscountInfoVO.class);
		return discountInfoVO;
	}

}
