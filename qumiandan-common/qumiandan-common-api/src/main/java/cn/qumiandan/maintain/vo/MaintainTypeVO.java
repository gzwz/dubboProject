package cn.qumiandan.maintain.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class MaintainTypeVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
    
    /**	
     * 模块类型（U：用户端；M：商家端；D：代理端；S：业务员端）
     */
    private String moduleType;

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
