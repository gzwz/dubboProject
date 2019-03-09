package cn.qumiandan.address.api;

import java.util.List;
import java.util.Set;

import cn.qumiandan.address.vo.AddressAreaCode;
import cn.qumiandan.address.vo.AddressCodeAndNameVO;
import cn.qumiandan.address.vo.AddressVO;

/**
 * 省市县管理接口
 * @author lrj
 * 创建时间：2018-11-20 13:51
 *
 */
public interface IAddressService {
	
	/**
	 * 根据code集合查询
	 * @param codeList
	 * @return
	 */
	List<AddressVO> getAddressListByCode(Set<Integer> codeList);
	
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
	List<AddressCodeAndNameVO> getCityByProvinceCode(int ProvinceCode);
	
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
	 * 根据省/市/区code获取省市区信息
	 * @param code
	 * @param level
	 * @return
	 */
	AddressVO getAddressByCode(Integer code,Integer level);
	
	
	/**
	 * 根据省/市/区name获取省市区信息
	 * @param areaCode
	 * @param level
	 * @return
	 */
	AddressVO getAddressByName(AddressAreaCode areaCode, Integer level);
	
	/**
	 * 当前城市是否开通
	 * @param cityCOde
	 * @return
	 */
	boolean getCurrentCityIsOpen(Integer cityCOde);
	
	/**
	 * 查询开通城市
	 * @return
	 */
	List<AddressCodeAndNameVO> getOpenCity();
	
	/**
	 * 根据省code集合查询
	 * @param proCodeList
	 * @return
	 */
	List<AddressVO> getAddressByProCodeList(List<Integer> proCodeList);
	
	/**
	 * 根据市code集合查询
	 * @param cityCodeList
	 * @return
	 */
	List<AddressVO> getAddressByCityCodeList(List<Integer> cityCodeList);
	
	/**
	 * 根据区code集合查询
	 * @param districtCodeList
	 * @return
	 */
	List<AddressVO> getAddressByDistrictCodeList(List<Integer> districtCodeList);
	
	/**
	 * 设置是否开通服务
	 * @param code 地址code
	 * @param level 级别：1省；2市；3区；4乡镇
	 * @param status 0关闭；1开通
	 * @return
	 */
	Boolean addressOpenOrCloseService(Integer code , Integer level , Byte status);
	
}
