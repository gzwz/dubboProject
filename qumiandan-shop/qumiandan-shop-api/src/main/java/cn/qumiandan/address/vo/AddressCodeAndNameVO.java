package cn.qumiandan.address.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 地址查询参数类
 * @author lrj
 *
 */
@Data
public class AddressCodeAndNameVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 地区编码
	 */
	private int areaCode;
	
	/**
	 * 地区名
	 */
	private String areaName;
	
	
	/**
	 * 父级code
	 */
	private int  parentCode ;
	
	/**
	 * 父级地区名
	 */
	private String parentName;
	
	/**
	 * 级别
	 */
    private Integer level;
}
