package cn.qumiandan.maintain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
@Data
@TableName("sys_maintain_type")
public class MaintainType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Long id;
    
    /**	
     * 模块类型（U：用户端；M：商家端；D：代理端；S：业务员端）
     */
    private String moduleType;


    /**
     * 维护类型
     */
    private String type ;

    /**
     * 维护类型名
     */
    private String typeName;

    /**
     * 1:正常；0：删除；（默认1）
     */
    private Byte status;

    /**
     * 创建者
     */
    private Long createId;

    /**
     * 更新者
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

}
