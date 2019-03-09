package cn.qumiandan.role.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 用户角色关联
 * @author yuleidian
 * @version 创建时间：2018年11月27日 下午4:08:58
 */
@Data
@TableName("sys_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 编号
     */
    private Long id;

    /**
     * 用户id
     */
    private Long sysUserId;

    /**
     * 角色id
     */
    private Long sysRoleId;

    /**
     * 状态（1：正常；0：已删除）
     */
    private Byte status;

}