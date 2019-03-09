package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;
import java.util.List;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class QueryProductParams extends PageInfoParams implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long shopId;	//店铺id

	private Long typeId;	//类型编号(1：团购商品；2：商超商品)

	private Long brandId;	//品牌编号

	private Long categoryId;	//分类id

	private Byte isQmd;	//是否趣免单
	
	private String inputInfo; //模糊查询条件

	private List<Byte> statusList;//商品状态集合
	

}
