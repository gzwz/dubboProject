package cn.qumiandan.web.frontServer.role.entity.response;

import java.io.Serializable;
import java.util.List;

import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.role.vo.UserRoleVO;
import lombok.Data;
/**
 * 获取当前用户角色和所有角色结果
 * @author yuleidian
 * @date 2019年1月16日
 */
@Data
public class GetCurrentUserRoleAndAllRoleResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 所有角色信息*/
	private List<RoleVO> allRoles;
	
	/** 用户角色信息*/
	private List<UserRoleVO> userRoles;
	
}
