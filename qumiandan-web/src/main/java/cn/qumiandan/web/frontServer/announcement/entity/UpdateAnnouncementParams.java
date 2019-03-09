package cn.qumiandan.web.frontServer.announcement.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class UpdateAnnouncementParams implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull(message="公告id不能为空")
	private Long id;

//    /**
//     * 类型(1.店铺公告，2.平台公告)
//     */
//    private Byte type;

//    /**
//     * 归属平台则是地区code(不同地区，公告不同)，归属店铺则是店铺id
//     */
//    private String belongId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 排序
     */
    private Integer sort;

    
}
