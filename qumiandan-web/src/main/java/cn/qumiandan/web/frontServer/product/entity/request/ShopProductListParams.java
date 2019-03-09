package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description：店铺商品列表参数对象
 * @author：zhuyangyong
 * @date: 2018/11/26 17:27
 */
@Getter
@Setter
@ToString
public class ShopProductListParams extends CommonParams implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @NotNull(message="店铺id不能为空")
    private Long shopId;

    private String name;	//商品名称

    private Long typeId;	//类型编号(1：团购商品；2：商超商品)

    private Long brandId;	//品牌编号

    private Long categoryId;	//分类id

    private Byte isQmd;	//是否趣免单

    private Long customCategoryId;	//自定义分类id

    private Byte status;	//状态（1：待审核；2：审核未通过；3：审核通过，上架；4：下架；5：强制下架；6：已删除）
}
