package cn.qumiandan.product.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 商品品牌传输类
 * @author lrj
 *
 */
@Data 
public class ProductBrandVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 排列次序
     */
    private Integer sort;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private Long updateId;

    /**
     * 更新时间
     */
    private Date updateDate;
}
