package cn.qumiandan.apply.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.enums.AddressLevelEnum;
import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.apply.api.IApplyService;
import cn.qumiandan.apply.entity.Apply;
import cn.qumiandan.apply.mapper.ApplyMapper;
import cn.qumiandan.apply.vo.ApplyVO;
import cn.qumiandan.apply.vo.QueryApplyVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IApplyService.class)
public class ApplyServiceImpl implements IApplyService {

	@Autowired
	private ApplyMapper mapper;
	@Autowired
	private IAddressService addressService;
	@Reference
	private ISalemanService salemanService;

	@Override
	public ApplyVO addApply(ApplyVO applyVO) {
		AssertUtil.isNull(applyVO, "参入不能为空");
		switch (applyVO.getType()) {
		case 1:
			if(applyVO.getCityCode() == null || applyVO.getCountryCode() == null) {
				log.error("addApply|申请成为业务员时市、区code不能为空；cityCode:" + applyVO.getCityCode() + ";countryCode:" +applyVO.getCountryCode());
				throw new QmdException("申请成为业务员时市、区code不能为空");
			}
			break;
		case 2:
			if(applyVO.getCityCode() == null || applyVO.getCountryCode() == null) {
				log.error("addApply|申请成为区代理市时市、区code不能为空；cityCode:" + applyVO.getCityCode() + ";countryCode:" +applyVO.getCountryCode());
				throw new QmdException("申请成为区代理市时市、区code不能为空");
			}
			SalemanVO countryAgent = salemanService.getAgentByCodeAndType(applyVO.getCountryCode(), SalemanTypeEnums.CountryAgent.getCode());
			if(countryAgent != null) {
				log.error("addApply|该市已存在省代理；proCode:" + applyVO.getProCode());
				throw new QmdException("该市已存在省代理");
			}
			break;
		case 3:
			if(applyVO.getCityCode() == null) {
				log.error("addApply|申请成为市代理市时市code不能为空；cityCode:" + applyVO.getCityCode());
				throw new QmdException("申请成为市代理市时市code不能为空");
			}
			SalemanVO cityAgent = salemanService.getAgentByCodeAndType(applyVO.getCityCode(), SalemanTypeEnums.CityAgent.getCode());
			if(cityAgent != null) {
				log.error("addApply|该市已存在省代理；proCode:" + applyVO.getProCode());
				throw new QmdException("该市已存在省代理");
			}
			break;
		case 4:
			SalemanVO proAgent = salemanService.getAgentByCodeAndType(applyVO.getProCode(), SalemanTypeEnums.ProAgent.getCode());
			if(proAgent != null) {
				log.error("addApply|该省已存在省代理；proCode:" + applyVO.getProCode());
				throw new QmdException("该省已存在省代理");
			}
			break;
		case 5:
			if(applyVO.getIndustryId() == null ) {
				log.error("addApply|申请开店时行业id不能为空");
				throw new QmdException("申请开店时行业id不能为空");
			}
			break;
		default:
			log.error("addApply|申请类型参数错误；type:" + applyVO.getType());
			throw new QmdException("申请类型参数错误");
		}
		Apply copyBean = CopyBeanUtil.copyBean(applyVO, Apply.class);
		copyBean.setStatus(StatusEnum.UnDeal.getCode());
		copyBean.setCreateDate(new Date());
		mapper.insert(copyBean);
		applyVO.setId(copyBean.getId());
		return applyVO;
	}

	@Override
	public ApplyVO queryApplyById(Long id) {
		Apply apply = mapper.selectById(id);
		if (null == apply) {
			return null;
		}
		ApplyVO copyBean = CopyBeanUtil.copyBean(apply, ApplyVO.class);
		// 查询申请记录对应省市县信息
		AddressVO addressVO ;
		switch (apply.getType()) {
		case 3:
			//市代理
			addressVO = addressService.getAddressByCode(Integer.parseInt(copyBean.getCityCode()),
					AddressLevelEnum.City.getCode());
			break;
		case 4:
			//省代理
			addressVO = addressService.getAddressByCode(Integer.parseInt(copyBean.getProCode()),
					AddressLevelEnum.Province.getCode());
			break;
		default:
			addressVO = addressService.getAddressByCode(Integer.parseInt(copyBean.getCountryCode()),
					AddressLevelEnum.District.getCode());
			break;
		}
		if (addressVO != null) {
			copyBean.setProName(addressVO.getProvinceName());
			copyBean.setCityName(addressVO.getCityName());
			copyBean.setCountryName(addressVO.getDistrictName());
		}
		return copyBean;
	}

	@Override
	public ApplyVO dealApplyById(Long id, Byte status) {
		Apply apply = mapper.selectById(id);
		if (null == apply) {
			log.error("dealApplyById|该申请不存在,id:" + id);
			throw new QmdException("该申请不存在！");
		}
		switch (status) {
		case 1:
			// 已处理
			apply.setStatus(StatusEnum.Deal.getCode());
			break;
		case 2:
			// 拒绝处理
			apply.setStatus(StatusEnum.RefuseDeal.getCode());
			break;
		default:
			log.error("dealApplyById|处理状态参数错误,id:" + id);
			throw new QmdException("处理状态参数错误！");
		}
		apply.setUpdateDate(new Date());
		mapper.updateById(apply);
		ApplyVO copyBean = CopyBeanUtil.copyBean(apply, ApplyVO.class);
		return copyBean;
	}

	@Override
	public PageInfo<ApplyVO> queryApply(QueryApplyVO applyVO) {
		PageHelper.startPage(applyVO.getPageNum(), applyVO.getPageSize());
		List<ApplyVO> applys = mapper.queryApply(applyVO);
		PageInfo<ApplyVO> pageInfo = new PageInfo<>(applys);
		return pageInfo;
	}

}
