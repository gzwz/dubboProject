package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：店铺图片传输对象
 * @author：zhuyangyong
 * @date: 2018/11/19 16:26
 */
@Data
public class ShopAddPictureParams implements Serializable {
	private static final long serialVersionUID = 1L;

    @NotBlank(message = "图片名称不能为空")
    private String name;

    @NotBlank(message = "图片url不能为空")
    private String imageUrl;

    @NotBlank(message = "图片跳转链接不能为空")
    private String skipUrl;

    /**
     * 图片后缀名
     */
    private String suffix;

    /**
     * 图片大小
     */
    private Long imageSize;

    //图片介绍
    private String intro;

    //排序
    private Integer sort;

    // 类型(1：营业执照；2：其他资质；3：门头照片：4：营业执照法人手持身份证照片；5：商家相册)
    @NotNull(message = "图片类型不能为空")
    private Byte type;

}
