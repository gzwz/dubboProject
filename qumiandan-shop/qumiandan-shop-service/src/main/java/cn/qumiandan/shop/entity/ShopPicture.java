package cn.qumiandan.shop.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 店铺图片实体类
 * @author lrj
 *
 */
@Data
@TableName(value = "qmd_shop_picture")
public class ShopPicture implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 店铺编号
     */
    private Long shopId;

    /**
     * 图片名称
     */
    private String name;

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
     * 排序
     */
    private Integer sort;

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
    13：对私账户身份证反面照片；
    14: 食品或卫生许可证
    15: 特种行业许可证
    16: 其他)
    
    
    
     */
    private Byte imageType;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 状态（1：正常；0：已删除）
     */
    private Byte status;

}