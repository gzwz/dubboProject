package cn.qumiandan.shop.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 店铺、店铺扩展表传输类
 * @author lrj
 *
 */
@Data
public class ShopBasicInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

		private Long id;	//店铺编号

		private String name;	//店铺名称

		private Long createId;	//创建人
		
		private String proCode;	//省编号

		private String cityCode;	//市编号

		private String countyCode;	//区/县编号
		
		private String logo;//店铺logo

}
