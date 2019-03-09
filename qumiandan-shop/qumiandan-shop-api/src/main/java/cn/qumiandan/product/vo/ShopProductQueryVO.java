package cn.qumiandan.product.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description：店铺商品查询条件对象
 * @author：zhuyangyong
 * @date: 2018/11/22 10:06
 */
@Getter
@Setter
@ToString
public class ShopProductQueryVO  implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long shopId;	//店铺id

	private String name;	//商品名称

	private Long typeId;	//类型编号(1：团购商品；2：商超商品)

	private Long brandId;	//品牌编号

	private Long categoryId;	//分类id

	private Byte isQmd;	//是否趣免单

	private Long customCategoryId;	//自定义分类id

	private Byte status;	//状态
	
	private String inputInfo; //模糊查询条件

	private List<Byte> statusList;//商品状态集合
	
	private Integer pageSize;
	
	private Integer pageNum;
}
