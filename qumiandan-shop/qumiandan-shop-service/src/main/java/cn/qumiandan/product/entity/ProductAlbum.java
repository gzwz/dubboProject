package cn.qumiandan.product.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 相册
 * @author unascribed
 */
@Data
@TableName(value = "qmd_product_album")
public class ProductAlbum implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long productId;

    /**
     * 图片名称
     */
    private String imageName;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 跳转链接
     */
    private String skipUrl;

    /**
     * 图片大小(单位：B)
     */
    private Long imageSize;

    /**
     * 图片后缀名
     */
    private String suffix;

    /**
     * 图片介绍
     */
    private String intro;

    /**
     * 排序
     */
    private Integer imageSort;

    /**
     * 图片类型(1：商品主图；2：商品轮播图；3：其他（暂时未定）)
     */
    private Byte imageType;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private Long createId;


}