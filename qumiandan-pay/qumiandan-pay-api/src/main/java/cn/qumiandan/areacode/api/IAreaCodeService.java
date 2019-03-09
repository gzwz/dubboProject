package cn.qumiandan.areacode.api;

import cn.qumiandan.areacode.vo.AreaCodeVO;

/**
 * 扫呗省市县区管理接口
 * @author lrj
 *
 */
public interface IAreaCodeService {
	
	/**
	 * 根据国标省市区Code查询扫呗省市区Code
	 * @param Code
	 * @param type(1:省；2市；3：区、县)
	 * @return
	 */
	String getSbAreaCodeByAddressCode(Integer code,Integer level);
	
	/**
	 * 根据扫呗省市区Code查询国标省市区Code
	 * @param Code
	 * @param type(1:省；2市；3：区、县)
	 * @return
	 */
	Integer getAddressCodeBySbAreaCode(String code,Integer level);
	
	/**
	 * 根据国标 区信息 查询扫呗省市县
	 * @return
	 */
	AreaCodeVO getSaobeiAreaInfoByCountryCode(String CountryCode);
}
