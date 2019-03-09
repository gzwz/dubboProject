package cn.qumiandan.product.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @description：商品分类传输对象
 * @author：zhuyangyong
 * @date: 2018/11/26 18:38
 */
@Data
public class ProductCategoryVO implements Serializable {
    
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Long id;

    /**
     * 类别名称
     */
    private String name;
    
    /**
     * 级别（1：一级；2：二级；）
     */
    private Byte level;

    /**
     * 父级编号
     */
    private Long parentId;

    /**
     * 排列次序
     */
    private Integer sort;

    /**
     * 状态( 1：正常；0：删除；)
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 更新人
     */
    private Long updateId;
}
