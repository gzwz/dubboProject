package cn.qumiandan.areacode.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 扫呗省市区传输类
 * @author lrj
 *
 */
@Data
public class AreaCodeVO implements Serializable{
	 /**
     * 编号
     */
    private Long id;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    private static final long serialVersionUID = 1L;

    
}
