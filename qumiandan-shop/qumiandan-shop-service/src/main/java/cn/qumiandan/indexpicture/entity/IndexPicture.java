package cn.qumiandan.indexpicture.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_shop_index_picture")
public class IndexPicture implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId
    private Long id;

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
     * 区域编号
     */
    private String areaCode;

    /**
     * 创建时间
     */
    private Date effectiveTime;

    /**
     * 创建时间
     */
    private Date loseTime;

    /**
     * 排列次序
     */
    private Integer sort;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createDate;

}