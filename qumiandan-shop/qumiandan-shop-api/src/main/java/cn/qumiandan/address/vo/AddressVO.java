package cn.qumiandan.address.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 省市县传输类
 * @author lrj
 * 创建时间：2018-11-20 13:36
 *
 */
@Data
public class AddressVO implements Serializable{
	private static final long serialVersionUID = 1L;
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
