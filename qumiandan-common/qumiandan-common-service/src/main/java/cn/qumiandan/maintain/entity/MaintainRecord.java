package cn.qumiandan.maintain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 维护记录表实体
 * @author 林荣娇
 * @version 创建时间：2018年11月26日 下午5:07:22
 */
@Data
@TableName("sys_maintain_record")
public class MaintainRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /**
     * 维护模块id
     */
    private Long maintainTypeId;

    /**
     * 升级说明
     */
    private String description;

    /**
     * 维护开始时间
     */
    private Date startDate;
    
    /**
     * 结束时间
     */
    private Date endDate;

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