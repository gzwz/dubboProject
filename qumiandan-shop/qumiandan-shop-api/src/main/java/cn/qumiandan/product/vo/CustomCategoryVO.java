package cn.qumiandan.product.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class CustomCategoryVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long customCategoryId;//自定义分类id

	private String customCategoryName;	//自定义分类名称(菜单名称)
}
