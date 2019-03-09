package cn.qumiandan.web.frontServer.deliveryaddress.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateDeliveryAddressParams implements Serializable{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "id不能为空")
	private Long id;

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
	 * 更新人id
	 */
	private Long updateId;

}
