package cn.qumiandan.permission.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "sys_permission")
public class Permission implements Serializable {
    /**
     * 编号
     */
    private Long id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父级菜单
     */
    private Long pId;

    /**
     * 菜单
     */
    private String permission;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 是否是菜单（1：菜单，2：按钮）
     */
    private Byte type;

    /**
     * 菜单状态 :  1:启用   0:不启用
     */
    private Byte status;

    /**
     * 创建者id
     */
    private Long createId;

    /**
     * 修改者id
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;

}