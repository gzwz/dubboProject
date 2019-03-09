package cn.qumiandan.address.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.address.entity.Address;
import cn.qumiandan.address.vo.AddressAreaCode;
import cn.qumiandan.address.vo.AddressCodeAndNameVO;
@Mapper
public interface AddressMapper extends BaseMapper<Address>{
	/**
	 * 获取所有省份，直辖市信息
	 * @return
	 */
	List<AddressCodeAndNameVO> getProvince();
	
	/**
	 * 根据省份Code获取城市信息
	 * @param ProvinceCode
	 * @return
	 */
	List<AddressCodeAndNameVO> getCityByProvinceCode(int provinceCode);
	
	/**
	 * 
	 * 根据城市code获取区域信息
	 * @param cityCode
	 * @return
	 */
	List<AddressCodeAndNameVO> getDistrictByCityCode(int cityCode) ;
	
	/**
	 * 根据城区code获取城镇、街道信息
	 * @param districtCode
	 * @return
	 */
	List<AddressCodeAndNameVO> getTownByDistrictCode(int districtCode);
	
	/**
	 * 根据省/市/县code和对应的级别查询省/市/县信息
	 * @param code
	 * @param level
	 * @return
	 */
	Address getAddressByCode(@Param("code")Integer code ,@Param("level")Integer level );


	/**
	 * 根据省/市/县name和对应的级别查询省/市/县信息
	 * @param name
	 * @param level
	 * @return
	 */
	Address getAddressByName(@Param("areaCode")AddressAreaCode areaCode ,@Param("level")Integer level );
	
	/**
	 * 当前城市是否开通
	 * @param cityCOde
	 * @return
	 */
	Address getCurrentCityIsOpen(Integer cityCOde);
	
	/**
	 * 查询开通城市
	 * @return
	 */
	List<AddressCodeAndNameVO> getOpenCity();
}