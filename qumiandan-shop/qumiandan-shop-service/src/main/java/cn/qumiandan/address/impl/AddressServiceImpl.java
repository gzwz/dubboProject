package cn.qumiandan.address.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.entity.Address;
import cn.qumiandan.address.enums.AddressLevelEnum;
import cn.qumiandan.address.mapper.AddressMapper;
import cn.qumiandan.address.vo.AddressAreaCode;
import cn.qumiandan.address.vo.AddressCodeAndNameVO;
import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;

/**
 * 省市县管理实现类
 * @author lrj
 * @date 创建时间：2018-11-20 13:51
 */
@Component
@Service(interfaceClass = IAddressService.class)
public class AddressServiceImpl implements IAddressService{

	@Autowired
    private AddressMapper addrMapper;
	/**
	 * 获取所有省份，直辖市信息
	 * @return
	 */
	@Override
	public List<AddressCodeAndNameVO> getProvince() {
		return addrMapper.getProvince();
	}

	/**
	 * 根据省份Code获取城市信息
	 * @param provinceCode
	 * @return
	 */
	@Override
	public List<AddressCodeAndNameVO> getCityByProvinceCode(int provinceCode) {
		return addrMapper.getCityByProvinceCode(provinceCode);
	}

	/**
	 * 
	 * 根据城市code获取区域信息
	 * @param cityCode
	 * @return
	 */
	@Override
	public List<AddressCodeAndNameVO> getDistrictByCityCode(int cityCode) {
		return addrMapper.getDistrictByCityCode(cityCode);
	}
	/**
	 * 根据城区code获取城镇、街道信息
	 * @param districtCode
	 * @return
	 */
	@Override
	public List<AddressCodeAndNameVO> getTownByDistrictCode(int districtCode) {
		return addrMapper.getTownByDistrictCode(districtCode);
	}

	/**
	 * 根据省/市/区code获取省市区信息
	 * @param code
	 * @param level
	 * @return
	 */
	@Override
	public AddressVO getAddressByCode(Integer code, Integer level) {
		Address address =  addrMapper.getAddressByCode(code, level);
		if(address == null) {
			return null;
		}
		AddressVO addressVO = CopyBeanUtil.copyBean(address, AddressVO.class);
		return addressVO;
	}

	/**
	 * 根据省/市/区name获取省市区信息
	 * @param areaCodeVO
	 * @param level
	 * @return
	 */
	@Override
	public AddressVO getAddressByName(AddressAreaCode areaCode, Integer level) {
		Address address =  addrMapper.getAddressByName(areaCode, level);
		if(address == null) {
			return null;
		}
		AddressVO addressVO = CopyBeanUtil.copyBean(address, AddressVO.class);
		return addressVO;
	}

	/**
	 * 当前城市是否开通
	 * @param cityCOde
	 * @return
	 */
	@Override
	public boolean getCurrentCityIsOpen(Integer cityCOde) {
		boolean flag=false;
		Address address  =  addrMapper.getCurrentCityIsOpen(cityCOde);
		if(address.getIsOpen()!=null&&address.getIsOpen().equals(StatusEnum.TRUE.getCode())) {
			flag = true; 
		}
		return flag;
	}

	/**
	 * 查询开通城市
	 * @return
	 */
	@Override
	public List<AddressCodeAndNameVO> getOpenCity() {
		List<AddressCodeAndNameVO> list =  addrMapper.getOpenCity();
		return list;
	}

	@Override
	public List<AddressVO> getAddressByProCodeList(List<Integer> proCodeList) {
		List<Address> addresses = addrMapper.selectList(new QueryWrapper<Address>().eq("level", AddressLevelEnum.Province.getCode()).in("province_code", proCodeList));
		if(ObjectUtils.isEmpty(addresses)) {
			return null;
		}
		return CopyBeanUtil.copyList(addresses, AddressVO.class);
	}

	@Override
	public List<AddressVO> getAddressByCityCodeList(List<Integer> cityCodeList) {
		List<Address> addresses = addrMapper.selectList(new QueryWrapper<Address>().eq("level", AddressLevelEnum.City.getCode()).in("city_code", cityCodeList));
		if(ObjectUtils.isEmpty(addresses)) {
			return null;
		}
		return CopyBeanUtil.copyList(addresses, AddressVO.class);
	}

	@Override
	public List<AddressVO> getAddressByDistrictCodeList(List<Integer> districtCodeList) {
		List<Address> addresses = addrMapper.selectList(new QueryWrapper<Address>().eq("level", AddressLevelEnum.District.getCode()).in("district_code", districtCodeList));
		if(ObjectUtils.isEmpty(addresses)) {
			return null;
		}
		return CopyBeanUtil.copyList(addresses, AddressVO.class);
	}

	
	/**
	 * 设置是否开通服务
	 * @param code 地址code
	 * @param level 级别：1省；2市；3区；4乡镇
	 * @param status 0关闭；1开通
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public Boolean addressOpenOrCloseService(Integer code, Integer level, Byte isOpen) {
		
		Address address = addrMapper.selectOne(new QueryWrapper<Address>() .eq("code", code));
		if (address == null) {
			throw new QmdException("地址编码级别错误参数错误");
		}	
		address.setIsOpen(isOpen);
		int i = addrMapper.updateById(address);
		if (1 == i) {
			return true;
		}
		return false;
		/*
		 * switch (level) { case 1: address = addrMapper.selectOne(new
		 * QueryWrapper<Address>() .eq("province_code", code).eq("level", level));
		 * if(address != null) { addrMapper.update(updateAddress , new
		 * UpdateWrapper<Address>() .eq("province_code", code).eq("level", level)); }
		 * break; case 2: address = addrMapper.selectOne(new QueryWrapper<Address>()
		 * .eq("city_code", code).eq("level",level)); if(address != null) { //开通城市
		 * addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("city_code", address.getCityCode()).eq("level",level)); //开通对应省
		 * addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("province_code",
		 * address.getProvinceCode()).eq("level",AddressLevelEnum.Province.getCode()));
		 * } break; case 3: address = addrMapper.selectOne(new
		 * QueryWrapper<Address>().eq("district_code", code)); if(address != null) {
		 * //开通区 addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("district_code", address.getDistrictCode()).eq("level",level)); //开通对应市
		 * addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("city_code",
		 * address.getCityCode()).eq("level",AddressLevelEnum.City.getCode())); //开通对应省
		 * addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("province_code",
		 * address.getProvinceCode()).eq("level",AddressLevelEnum.Province.getCode()));
		 * } break; case 4: address = addrMapper.selectOne(new
		 * QueryWrapper<Address>().eq("town_code", code)); if(address != null) { //开通乡镇
		 * addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("town_code", address.getTownCode()).eq("level",level)); //开通区
		 * addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("district_code", address.getDistrictCode())
		 * .eq("level",AddressLevelEnum.District.getCode())); //开通对应市
		 * addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("city_code", address.getCityCode())
		 * .eq("level",AddressLevelEnum.City.getCode())); //开通对应省
		 * addrMapper.update(updateAddress, new UpdateWrapper<Address>()
		 * .eq("province_code", address.getProvinceCode())
		 * .eq("level",AddressLevelEnum.Province.getCode())); } break;
		 * 
		 * default: log.error("addressOpenOrCloseService|地址编码级别错误参数错误 level:" + level );
		 * throw new QmdException("地址编码级别错误参数错误"); }
		 */
	}

	@Override
	public List<AddressVO> getAddressListByCode(Set<Integer> codeList) {
		AssertUtil.isNull(codeList, "地区code不能为空");
		List<Address> addrList = addrMapper.selectList(new QueryWrapper<Address>().in("code", codeList));
		if (null == addrList) {
			return null;
		}
		List<AddressVO> addressVOs  = CopyBeanUtil.copyList(addrList, AddressVO.class);
		return addressVOs;
	}



}
