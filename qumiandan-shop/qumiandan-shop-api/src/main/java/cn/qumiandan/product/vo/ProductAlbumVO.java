package cn.qumiandan.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description：商品图片传输对象
 * @author：zhuyangyong
 * @date: 2018/11/22 16:26
 */
@Data
public class ProductAlbumVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;	//商品id

	private String imageName;	//图片名称

	private String imageUrl;	//图片url

    private String skipUrl; //图片跳转链接

	private Long imageSize;	//图片大小(单位：B)

	private String suffix;	//图片后缀名

	private String intro;	//图片介绍

	private Integer imageSort;	//排序

	private Byte imageType;	//类型(1：商品主图；2：商品轮播图；3：其他（暂时未定）)

	private Long createId;	//创建人
}
