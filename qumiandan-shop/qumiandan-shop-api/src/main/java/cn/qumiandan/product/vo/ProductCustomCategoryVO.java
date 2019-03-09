package cn.qumiandan.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description：商品自定义分类传输对象
 * @author：zhuyangyong
 * @date: 2018/11/24 17:07
 */
@Data
public class ProductCustomCategoryVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;	//自定义分类id
    private String name;	//自定义分类名称

}
