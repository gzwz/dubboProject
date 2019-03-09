package cn.qumiandan.maintain.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 功能维护参数传输类
 * @author 林荣娇
 * @version 创建时间：2018年11月26日 下午5:07:22
 */
@Data
public class MaintainRecordVO implements Serializable {
    private static final long serialVersionUID = 1L;

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