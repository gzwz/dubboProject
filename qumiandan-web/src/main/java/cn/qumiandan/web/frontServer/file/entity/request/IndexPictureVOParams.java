package cn.qumiandan.web.frontServer.file.entity.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @description：文件存储参数对象
 * @author：zhuyangyong
 * @date: 2018/11/13 14:06
 */
@Data
public class IndexPictureVOParams implements Serializable {
    private static final long serialVersionUID = 1L;

    //@NotBlank(message = "本地文件路径不能为空")
    //private String localFilePath;

    @NotBlank(message = "图片名称不能为空")
    private String name;

    /**
     * 图片url
     */
    @NotBlank(message = "图片url不能为空")
    private String imageUrl;

    /**
     * 图片跳转链接
     */
    private String skipUrl;

    /**
     * 图片后缀名
     */
    @NotBlank(message = "图片后缀名不能为空")
    private String suffix;

    /**
     * 图片大小
     */
    @NotNull(message = "图片大小不能为空")
    private Long imageSize;

    /**
     * 图片介绍
     */
    private String intro;

    @NotNull(message = "区域编号不能为空")
    private String areaCode;

    @NotNull(message = "生效时间不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTime;

    @NotNull(message = "失效时间不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loseTime;

}
