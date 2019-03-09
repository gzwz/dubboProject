package cn.qumiandan.deliveryaddress.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 收货地址实体类
 * @author lrj
 *
 */
@Data
@TableName(value = "sys_delivery_address")
public class DeliveryAddress implements Serializable {
    private Long id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 收件人姓名
     */
    private String realName;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 备用联系电话
     */
    private String mobileBack;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;
    
    /**
     * 地区
     */
    private String area;

    /**
     * 街道/详细收货地址
     */
    private String street;

    /**
     * 邮政编码
     */
    private String zip;

    /**
     * 是否默认收货地址(1,是，0.不是)
     */
    private Byte isDefaultAddress;

 

    /**
     * 0:已删除；1：正常
     */
    private Byte status;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 更新人id
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

    private static final long serialVersionUID = 1L;

    
}