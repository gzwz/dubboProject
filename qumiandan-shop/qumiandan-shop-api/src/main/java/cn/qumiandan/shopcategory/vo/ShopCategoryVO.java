package cn.qumiandan.shopcategory.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * @description：店铺分类传输对象
 * @author：zhuyangyong
 * @date: 2018/11/8 13:57
 */
@Data
public class ShopCategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;
	/**
     * 编号
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

	/**
	 * 状态（1：正常；0：已删除）
	 */
	private Byte status;

}
