package cn.qumiandan.address.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 省市县实体类
 * @author lrj
 *
 */
@Data
@TableName(value = "address")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer code;

    private Integer provinceCode;

    private String provinceName;

    private Integer cityCode;

    private String cityName;

    private Integer districtCode;

    private String districtName;

    private Integer townCode;

    private String townName;

    private Integer level;

    private String fullName;

    private String name;
    
    /**
     * 是否开通
     */
    private Byte isOpen;
}