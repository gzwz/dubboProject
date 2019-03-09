package cn.qumiandan.permission.api;

import java.util.List;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.permission.vo.PermissionVO;

/***
 * 管理系统菜单增加，更新
 * @author WLZ
 * 2018年11月19日
 */
public interface IPermissionService {

	/**增加*/
	int addPermission(PermissionVO param) throws QmdException;
	
	/**更改*/
	int updatePermission(PermissionVO param) ;

	/**
	 * 删除菜单
	 * @param id
	 * @return
	 * @throws QmdException
	 */
	int deletePermission(Long id) ;
	
	/**
	 * 获取所有菜单
	 * @return
	 */
	List<PermissionVO> getAllPermission(Byte type);
	
}
