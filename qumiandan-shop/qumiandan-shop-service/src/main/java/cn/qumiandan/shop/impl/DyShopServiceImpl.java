package cn.qumiandan.shop.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.shop.api.IDyShopService;
import cn.qumiandan.shop.entity.Shop;
import cn.qumiandan.shop.enums.ShopStatusEnum;
import cn.qumiandan.shop.mapper.ShopMapper;
import cn.qumiandan.shop.vo.DLQueryShopVO;
import cn.qumiandan.shop.vo.ShopStatisticVO;
import cn.qumiandan.shop.vo.ShopVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.DateUtil;
import cn.qumiandan.utils.ObjectUtils;

@Component
@Service(interfaceClass = IDyShopService.class)
public class DyShopServiceImpl implements IDyShopService {

	
	@Autowired
	private ShopMapper shopMapper;
	
	@Reference
	private ISalemanService saleManService;
	
	@Autowired
	private IAddressService addrService;
	/**
	 * 业务员端店铺数量统计
	 */
	@Override
	public ShopStatisticVO getShopNumBySalemanId(Long salemanId) {
		ShopStatisticVO shopStatisticVO = new ShopStatisticVO();
		//查询各状态店铺数量
		shopStatisticVO.setCreateCommitShopNum(shopMapper.getShopNumBySalemanId(salemanId, ShopStatusEnum.CREATECOMMIT.getCode(),null,null));
		shopStatisticVO.setApplyCloseShopNum(shopMapper.getShopNumBySalemanId(salemanId, ShopStatusEnum.APPLYCLOSE.getCode(),null,null));
		shopStatisticVO.setLocalUpdateCommitShopNum(shopMapper.getShopNumBySalemanId(salemanId, ShopStatusEnum.LOCALUPDATECOMMIT.getCode(),null,null));
		shopStatisticVO.setLocalUpdateUnpassShopNum(shopMapper.getShopNumBySalemanId(salemanId, ShopStatusEnum.LOCALUPDATEUNPASS.getCode(),null,null));
		shopStatisticVO.setPassShopNum(shopMapper.getShopNumBySalemanId(salemanId, ShopStatusEnum.PASS.getCode(),null,null));
		shopStatisticVO.setPlatformCloseShopNum(shopMapper.getShopNumBySalemanId(salemanId, ShopStatusEnum.PLATFORMCLOSE.getCode(),null,null));
		shopStatisticVO.setUnpassShopNum(shopMapper.getShopNumBySalemanId(salemanId, ShopStatusEnum.UNPASS.getCode(),null,null));
		Date endCreateDate = new Date();
		//查询今日店铺
		shopStatisticVO.setTodayShopNum(shopMapper.getShopNumBySalemanId(salemanId, null, DateUtil.getDayBeginTime(endCreateDate), endCreateDate));
		//本周店铺
		shopStatisticVO.setCurrentWeekShopNum(shopMapper.getShopNumBySalemanId(salemanId, null, DateUtil.getWeekStart(endCreateDate), endCreateDate));
		//本月店铺
		shopStatisticVO.setCurrentMothShopNum(shopMapper.getShopNumBySalemanId(salemanId, null, DateUtil.getMonthStart(endCreateDate), endCreateDate));
		return shopStatisticVO;
	}

	@Override
	public PageInfo<ShopVO> getShopPageByDyId(DLQueryShopVO vo) {
		AssertUtil.isNull(vo, "代理用户id不能为空");
		SalemanVO salemanVO = saleManService.getSalemanByUserId(vo.getDyUserId());
		if (salemanVO == null) {
			throw new QmdException("该代理不存在");
		}
		// 如果是省代理
		List<Shop> list = null ;
		Page<Shop> startPage = PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
		if (SalemanTypeEnums.ProAgent.getCode().equals(salemanVO.getType())) {
			list = shopMapper.selectList(new QueryWrapper<Shop>()
					.eq("pro_code", salemanVO.getProCode()).eq("status", ShopStatusEnum.PASS.getCode()));
		} else if(SalemanTypeEnums.CityAgent.getCode().equals(salemanVO.getType())) {
			list = shopMapper.selectList(new QueryWrapper<Shop>()
					.eq("city_code", salemanVO.getCityCode()).eq("status", ShopStatusEnum.PASS.getCode()));
		}else if(SalemanTypeEnums.CountryAgent.getCode().equals(salemanVO.getType())) {
			list = shopMapper.selectList(new QueryWrapper<Shop>()
					.eq("county_code", salemanVO.getCountryCode()).eq("status", ShopStatusEnum.PASS.getCode()));
		}else {
			return new PageInfo<ShopVO>();
		}
		if (ObjectUtils.isEmpty(list)) {
			return new PageInfo<ShopVO>();
		}
		@SuppressWarnings("unchecked")
		PageInfo<ShopVO> info = CopyBeanUtil.copyBean(startPage, PageInfo.class);
		List<ShopVO> shopList = CopyBeanUtil.copyList(list, ShopVO.class);
		// 获取地区中文名
		Set<Integer> addrcode = new HashSet<Integer>();
		for (ShopVO sp : shopList) {
			addrcode.add(Integer.valueOf(sp.getProCode() != null ? sp.getProCode():"0"));
			addrcode.add(Integer.valueOf(sp.getCityCode() != null ? sp.getCityCode():"0"));
			addrcode.add(Integer.valueOf(sp.getCountyCode() != null ? sp.getCountyCode():"0"));
		}
		List<AddressVO> addressList = addrService.getAddressListByCode(addrcode);
		for (int i = 0; i < shopList.size(); i++) {
			ShopVO shopVO = shopList.get(i);
			for (int j = 0; j < addressList.size(); j++) {
				AddressVO addressVO = addressList.get(j);
				if (StringUtils.equals(shopVO.getProCode(), addressVO.getCode().toString())) {
					shopVO.setProName(addressVO.getProvinceName());
				}
				if (StringUtils.equals(shopVO.getCityCode(), addressVO.getCode().toString())) {
					shopVO.setProName(addressVO.getCityName());
				}
				if (StringUtils.equals(shopVO.getCountyCode(), addressVO.getCode().toString())) {
					shopVO.setProName(addressVO.getDistrictName());
				}
				
			}
				
		}
		info.setList(shopList);
		return info;
	}

	@Override
	public List<ShopVO> getShopListByDyId(Long dyUserId) {
		AssertUtil.isNull(dyUserId, "代理用户dyUserId不能为空");
		SalemanVO salemanVO = saleManService.getSalemanByUserId(dyUserId);
		if (salemanVO == null) {
			throw new QmdException("该代理不存在");
		}
		// 如果是省代理
		List<Shop> list = null ;
		if (SalemanTypeEnums.ProAgent.getCode().equals(salemanVO.getType())) {
			list = shopMapper.selectList(new QueryWrapper<Shop>()
					.eq("pro_code", salemanVO.getProCode()).eq("status", ShopStatusEnum.PASS.getCode()));
		} else if(SalemanTypeEnums.CityAgent.getCode().equals(salemanVO.getType())) {
			list = shopMapper.selectList(new QueryWrapper<Shop>()
					.eq("city_code", salemanVO.getCityCode()).eq("status", ShopStatusEnum.PASS.getCode()));
		}else if(SalemanTypeEnums.CountryAgent.getCode().equals(salemanVO.getType())) {
			list = shopMapper.selectList(new QueryWrapper<Shop>()
					.eq("county_code", salemanVO.getCountryCode()).eq("status", ShopStatusEnum.PASS.getCode()));
		}else {
			return null;
		}
		List<ShopVO> result = CopyBeanUtil.copyList(list, ShopVO.class);
		return result;
	}

}
