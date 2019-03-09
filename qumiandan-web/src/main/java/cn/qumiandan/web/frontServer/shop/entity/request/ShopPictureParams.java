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
public class ShopPictureParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "图片名称不能为空")
    private String imageName;

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
    private Integer imageSort;

    /**
     * 类型(1：营业执照（营业执照(三证合一)不能为空）；
    2：其他资质；
    3：门头照片；
    4：法人手持身份证照片；
    5：商家相册;
    6：logo；
    7：法人身份证正面照片；
    8：法人身份证反面照片；
    9：入账银行卡正面照片；
    10：门脸(内设)照片(前台)；
    11：入账非法人证明照片 ；
    12：对私账户身份证正面照片；
    13：对私账户身份证反面照片；)
     */
    @NotNull(message = "图片类型不能为空")
    private Byte imageType;
}
