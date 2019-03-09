package cn.qumiandan.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    private Long id;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 部门名称
     */
    private Long deptId;

    /**
     * 英文别名
     */
    private String ealias;

    private Long createId;

    private Long updateId;

    private Date createDate;

    private Date updateDate;

    /**
     * 1:正常； 0：已删除；（默认1）
     */
   private Byte status; 
}