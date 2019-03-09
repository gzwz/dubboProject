package cn.qumiandan.maintain.api;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.maintain.vo.MaintainRecordVO;

/**
 * 维护记录管理接口类
 * @author lrj
 *
 */
public interface IMaintainRecordService {

	/**
	 * 添加维护记录
	 * @param MaintainRecordVO
	 * @return
	 */
	MaintainRecordVO addMaintainRecord(MaintainRecordVO maintainRecordVO);
	
	/**
	 * 修改维护记录
	 * @param MaintainRecordVO
	 */
	void updateMaintainRecord(MaintainRecordVO maintainRecordVO);
	
	/**
	 * 删除维护记录
	 * @param id
	 * 
	 */
	void deleteMaintainRecord(Long id);
	
	/**
	 * 根据主键查询维护记录
	 * @param id
	 * @return
	 */
	MaintainRecordVO getMaintainRecordById(Long id);
	
	/**
	 * 根据条件查询维护记录
	 * @param maintainTypeId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<MaintainRecordVO> queryMaintainRecord(Long maintainTypeId, int pageNum,int pageSize);

	/**
	 * 根据维护类型查询维护记录
	 * @param id
	 * @return
	 */
	MaintainRecordVO getMaintainRecordByType(String type);
}
