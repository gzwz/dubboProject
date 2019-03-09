package cn.qumiandan.saleman.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 店铺省市区代理参数类
 * @author lrj
 *
 */
@Data
public class ShopAgentVO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 省代理
	 */
	private SalemanVO proAgent;
	
	/**
	 * 市代理
	 */
	private SalemanVO cityAgent;
	
	/**
	 * 区代理
	 */
	private SalemanVO countryAgent;
	
	private SalemanVO saleman;
}
