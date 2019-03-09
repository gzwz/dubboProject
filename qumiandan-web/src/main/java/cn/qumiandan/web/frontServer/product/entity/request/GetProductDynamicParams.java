package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 动态查询商品列表参数（分页）
 * @author lrj
 *	@version 创建时间：2018年11月12日 11:12
 */
@Getter
@Setter
@ToString
public class GetProductDynamicParams extends CommonParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
     * 商品编号
     */
    private Long id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品型号
     */
    private Short marque;

    /**
     * 仓库条码
     */
    private String barcode;

    /**
     * 类型编号
     */
    private Short typeId;

    /**
     * 类别编号
     */
    private Long categoryId;

    /**
     * 品牌编号
     */
    private Long brandId;

    /**
     * 所属店铺编号
     */
    private Long shopId;

    /**
     * 上下架状态（1：上架；0：下架）,默认1
     */
    private Byte isShelf;
    
    /**
     * 商品状态
     *	1:创建待审核中；
	 *	2：创建审核未通过；
	 *	3：审核通过；
	 *	4：更新审核中；
	 *	5：更新审核未通过（审核通过为3）；
	 *	6：已删除；
	 *	默认：3
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 更新人
     */
    private Long updateId;
}
