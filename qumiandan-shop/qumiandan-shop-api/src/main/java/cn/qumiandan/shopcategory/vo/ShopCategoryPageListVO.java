package cn.qumiandan.shopcategory.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description：店铺分类分页列表参数对象
 * @author：zhuyangyong
 * @date: 2018/11/21 13:50
 */
@Data
public class ShopCategoryPageListVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long parentId;
    private Integer pageNum;
    private Integer pageSize;
}
