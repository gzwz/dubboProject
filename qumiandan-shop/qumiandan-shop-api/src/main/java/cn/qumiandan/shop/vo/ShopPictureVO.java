package cn.qumiandan.shop.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @description：店铺图片传输对象
 * @author：zhuyangyong
 * @date: 2018/11/19 16:26
 */
@Data
public class ShopPictureVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    
    /**
     * 店铺id
     */
    private Long shopId;
    
    /**
     * 图片名称
     */
    private String imageName;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 图片跳转链接
     */
    private String skipUrl;

    /**
     * 图片后缀名
     */
    private String suffix;

    /**
     * 图片大小
     */
    private Long imageSize;

    /**
     * 图片介绍
     */
    private String intro;

    /**
     * 排列次序
     */
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
    private Byte imageType;
    
    /**
     * 状态
     */
    private Byte status;
    
    /** 创建时间*/
    private Date createDate;
}
