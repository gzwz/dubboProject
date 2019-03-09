package cn.qumiandan.role.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户角色关联信息VO
 * @author yuleidian
 * @version 创建时间：2018年11月29日 下午5:50:13
 */
@Data
public class UserRoleVO implements Serializable {

	private static final long serialVersionUID = 79749915573143152L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 用户编号
	 */
	private Long sysUserId;
	 
	
	/**
	 * 角色编号
	 */
	private Long sysRoleId;
	
	/**
	 * 状态默认1（1：正常； 0：已删除）
	 */
	private Byte status;
}
