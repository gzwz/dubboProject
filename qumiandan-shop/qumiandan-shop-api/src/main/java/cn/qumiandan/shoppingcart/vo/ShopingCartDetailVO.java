package cn.qumiandan.shoppingcart.vo;

import java.io.Serializable;
import java.util.List;

import cn.qumiandan.product.vo.ProductBasicVO;
import lombok.Data;

@Data
public class ShopingCartDetailVO implements Serializable {
    
	private static final long serialVersionUID = 1L;

	//=====================店铺基础信息=======================
	private Long id;//店铺id
	
  	private String name;	//店铺名称

  	private String description;	//店铺简介

  	private Long shopTypeId;	//店铺类型

  	private String license;	//营业执照编号

  	private String logo;	//店铺logo-url

  	private String longitude;	//经度

  	private String latitude;	//纬度

  	private String proCode;	//省编号

  	private String cityCode;	//市编号

  	private String countyCode;	//区/县编号

  	private String townCode;	//乡镇编号

  	private String address;	//详细地址

  	private String phone;	//店铺联系电话
  	
  	private String status; //店铺状态
  	
  	//========商品集合=========
  	
  	private List<ProductBasicVO> productList;
}
