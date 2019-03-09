package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：商品图片传输对象
 * @author：zhuyangyong
 * @date: 2018/11/26 16:26
 */
@Data
public class ProductPictureParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "商品图片名称不能为空")
    private String imageName;	//图片名称

    @NotBlank(message = "商品图片url不能为空")
    private String imageUrl;	//图片url

    private String skipUrl; //图片跳转链接

    private Long imageSize;	//图片大小(单位：B)

    private String suffix;	//图片后缀名

    private String intro;	//图片介绍

    private Integer imageSort;	//排序

    @NotNull(message = "商品图片类型不能为空")
    private Byte imageType;	//类型(1：商品主图；2：商品轮播图；3：其他（暂时未定）)

    private Long createId;	//创建人
}
