
package cn.qumiandan.shopcategory.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @description：店铺自定义分类传输对象
 * @author：lrj
 * @date: 2018/11/10 14:17
 */
@Data
public class ShopCustomCategoryVO implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 自定义分类id
     */
    private Long id;

    /**
     * 店铺编号
     */
    private Long shopId;
    
    /**
     * 排序数
     */
    private int sort;
    
    /**
     * 名称
     */
    private String name;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * 创建人Id
	 */
	private Long createId;

	/**
	 * 更新人Id
	 */
	private Long updateId;
	
	/**
	 * 状态（1：正常；0：已删除）
	 */
	private Byte status;
}
