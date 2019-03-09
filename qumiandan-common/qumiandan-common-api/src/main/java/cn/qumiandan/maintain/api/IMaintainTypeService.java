package cn.qumiandan.maintain.api;

import java.util.List;

import cn.qumiandan.maintain.vo.MaintainTypeVO;
/**
 * 维护类型接口
 * @author lrj
 *
 */
public interface IMaintainTypeService {

	/**
	 * 查询所有维护类型
	 * @param type
	 * @return
	 */
	List<MaintainTypeVO> getAllMaintainType(Long maintainType);
	
	/**
	 * 根据type查询维护类型
	 * @param type
	 * @return
	 */
	MaintainTypeVO getMaintainTypeByType(String type);
}
