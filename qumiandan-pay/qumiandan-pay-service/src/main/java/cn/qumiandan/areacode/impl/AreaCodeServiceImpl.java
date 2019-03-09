package cn.qumiandan.areacode.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.vo.AddressAreaCode;
import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.areacode.api.IAreaCodeService;
import cn.qumiandan.areacode.entity.AreaCode;
import cn.qumiandan.areacode.mapper.AreaCodeMapper;
import cn.qumiandan.areacode.vo.AreaCodeVO;
import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;

/**
 * 
 * 扫呗省市县区管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IAreaCodeService.class)
public class AreaCodeServiceImpl implements IAreaCodeService{

	@Autowired
	private AreaCodeMapper areaCodeMapper;
	@Reference()
	private IAddressService addressService;
	
	/**
	 * 根据国标省市区Code查询扫呗省市区Code
	 * @param Code
	 * @param type(1:省；2市；3：区、县)
	 * @return
	 */
	@Override
	public String getSbAreaCodeByAddressCode(Integer code, Integer level) {
		String returnCode = null;
		//根据国标code以及对应要查询的省市县级别level查询国标省市县信息
		AddressVO address = addressService.getAddressByCode(code, level);
		//根据国标省市县名以及对应要查询的省市县级别level查询扫呗省市县信息
		AreaCode areaCode = areaCodeMapper.getAreaCodeByAddress(address, level);
		if(areaCode!=null) {
			switch (level) {
			case 1:
				returnCode = areaCode.getProvinceCode();
				break;
			case 2:
				returnCode = areaCode.getCityCode();
				break;
			case 3:
				returnCode = areaCode.getAreaCode();
				break;
			default:
				throw new QmdException("级别参数错误,只有1，2，3级");
			}
		}
		return returnCode;
	}

	/**
	 * 根据扫呗省市区Code查询国标省市区Code
	 * @param Code
	 * @param type(1:省；2市；3：区、县)
	 * @return
	 */
	@Override
	public Integer getAddressCodeBySbAreaCode(String code, Integer level) {
		Integer returnCode = null;
		//根据扫呗code以及对应要查询的省市县级别level查询扫呗省市县信息
		AreaCode areaCode = areaCodeMapper.getAreaCodeByCode(code, level);
		//根据扫呗省市县名以及对应要查询的省市县级别level查询国标省市县信息
		if(areaCode!=null) {
			AddressAreaCode addressAreaCode = CopyBeanUtil.copyBean(areaCode, AddressAreaCode.class);
			AddressVO addressVO = addressService.getAddressByName(addressAreaCode, level);
			if(addressVO!=null) {
				switch (level) {
				case 1:
					returnCode = addressVO.getProvinceCode();
					break;
				case 2:
					returnCode = addressVO.getCityCode();
					break;
				case 3:
					returnCode = addressVO.getDistrictCode();
					break;
				default:
					throw new QmdException("级别参数错误,只有1，2，3级");
				}
			}
		}
		return returnCode;
	}

	@Override
	public AreaCodeVO getSaobeiAreaInfoByCountryCode(String CountryCode) {
		AssertUtil.isNull(CountryCode, "AreaCodeServiceImpl|getSaobeiAreaInfoByCountryCode|传入参数CountryCode为空");
		// 获取国标信息
		AddressVO address = addressService.getAddressByCode(Integer.parseInt(CountryCode), 3);
		AreaCodeVO reuslt = areaCodeMapper.getAreaInfoByProvinceNameAndcountryName(address.getProvinceName(), address.getDistrictName());
		if (Objects.isNull(reuslt)) {
			throw new QmdException(PayErrorCode.PAY1000.getCode(), "查询的扫呗省市县地址不存在");
		}
		return reuslt;
	}
}
