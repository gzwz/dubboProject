package cn.qumiandan.indexpicture.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @description：首页轮播图片传输对象
 * @author：zhuyangyong
 * @date: 2018/11/10 16:26
 */
@Data
public class IndexPictureVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
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
     * 区
     */
    private String area;

    /**
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 失效时间
     */
    private Date loseTime;

    /**
     * 排列次序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 状态
     */
    private Byte status;
}
