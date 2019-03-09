package cn.qumiandan.shopprofit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.ratecode.api.IRateCodeService;
import cn.qumiandan.shopprofit.api.IShopProfitService;
import cn.qumiandan.shopprofit.entity.ShopProfit;
import cn.qumiandan.shopprofit.mapper.ShopProfitMapper;
import cn.qumiandan.shopprofit.vo.ShopProfitDetailVO;
import cn.qumiandan.shopprofit.vo.ShopProfitVO;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = IShopProfitService.class)
public class ShopProfitServiceImpl implements IShopProfitService {

	@Autowired
	private ShopProfitMapper  shopProfitMapper;
	@Reference
	private IRateCodeService rateCodeService;
	
	/**
	 * 更新店铺分润信息(若分润表里已存在该店铺的分润信息，则更新该条信息，若不存在则新插入一条店铺分润数据)
	 * @param shopProfitVO
	 * @return
	 */
	@Override
	public int updateShopProfit(ShopProfitVO shopProfitVO) {
		ShopProfit shopProfit =  CopyBeanUtil.copyBean(shopProfitVO, ShopProfit.class);
		ShopProfit shopProfit2  = shopProfitMapper.selectOne(new QueryWrapper<ShopProfit>().eq("shop_id", shopProfitVO.getShopId()).eq("status", StatusEnum.normal.getCode()));
		int ret = 0;
		if (shopProfit2 != null) {
			shopProfit.setId(shopProfit2.getId());
			shopProfit.setRate(shopProfit2.getRate());
			ret = shopProfitMapper.updateById(shopProfit);
		} else {
			shopProfit.setUseDefaultFeeFlag(StatusEnum.TRUE.getCode());
			shopProfit.setStatus(StatusEnum.normal.getCode());
			ret = shopProfitMapper.insert(shopProfit);

		}
		if (!checkCUD(ret)) {
			throw new QmdException("修改费率失败");
		}
		return 1;
	}
	

	/**
	 * 查询店铺费率 
	 * @param shopId
	 * @return
	 */
	@Override
	public ShopProfitDetailVO getShopProfitByShopId(Long shopId) {
		ShopProfit profit  = shopProfitMapper.selectOne(new QueryWrapper<ShopProfit>().eq("shop_id", shopId).eq("status", StatusEnum.normal.getCode()));
		if(profit == null) {
			return null;
		}
		ShopProfitDetailVO shopProfitDetailVO = new ShopProfitDetailVO();
		shopProfitDetailVO.setShopId(shopId);
		shopProfitDetailVO.setRate(profit.getRate());
		shopProfitDetailVO.setRateCode(profit.getSbRateCode());
		return shopProfitDetailVO;
		//店铺分润表存在该条数据，则费率以店铺分润表为主
		/*
		 * if(shopProfitByShopId!=null) { RateCodeVO rateCodeVO =
		 * rateCodeService.getRateCode(shopProfitByShopId.getSbRateCode());
		 * shopProfitDetailVO.setRate(rateCodeVO.getRate());
		 * shopProfitDetailVO.setRateCode(rateCodeVO.getCode());
		 * shopProfitDetailVO.setRateRule(rateCodeVO.getRateRule()); }
		 *//*
			 * else{ //店铺分润表不存在该条数据，则费率以行业费率为主 ShopBasicVO shopBasicVO =
			 * shopMapper.getShopBasicById(shopId); IndustryVO industry =
			 * industryMapper.getIndustryVOById(shopBasicVO.getIndustryId());
			 * shopProfitDetailVO.setRateCode(industry.getRateCode());
			 * shopProfitDetailVO.setRate(industry.getFee()); }
			 */
		
	}

	/**
	 * 删除店铺分润表中店铺费率信息
	 * @param shopId
	 * @return
	 */
	@Override
	public int deleteShopProfit(Long shopId) {
		ShopProfit shopProfit  = shopProfitMapper.selectOne(new QueryWrapper<ShopProfit>().eq("shop_id", shopId).eq("status", StatusEnum.normal.getCode()));
		shopProfit.setStatus(StatusEnum.deleted.getCode());
		return shopProfitMapper.updateById(shopProfit);
	}

}
