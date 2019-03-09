package cn.qumiandan.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 用户-角色关联表实体类
 * @author lrj
 *
 */
@Data
@TableName(value = "sys_user_role")
public class UserRole implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long  id;
	
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
	private byte status;
}
