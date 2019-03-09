package cn.qumiandan.web.frontServer.deliveryaddress.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddDeliveryAddressParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 收件人姓名
	 */
	@NotBlank(message = "收件人姓名不能为空")
	private String realName;

	/**
	 * 联系电话
	 */
	@NotBlank(message = "联系电话不能为空")
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
	@NotBlank(message = "省份不能为空")
	private String province;

	/**
	 * 城市
	 */
	@NotBlank(message = "城市不能为空")
	private String city;
	
	/**
	 * 地区
	 */
	@NotBlank(message = "地区不能为空")
	private String area;

	/**
	 * 街道/详细收货地址
	 */
	@NotBlank(message = "街道/详细收货地址不能为空")
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
	 * 创建人id
	 */
	private Long createId;

}
