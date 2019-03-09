package cn.qumiandan.web.frontServer.announcement.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class AddShopAnnouncementParams implements Serializable{

	
	private static final long serialVersionUID = 1L;

    /**
     * 归属平台则是地区code或0(code不为零时不同地区，公告不同)，归属店铺则是店铺id
     */
	@NotBlank(message="创建店铺公告归属id(店铺id)不能为空")
    private String belongId;
	
    /**
     * 标题
     */
	@NotBlank(message="标题不能为空")
    private String title;

    /**
     * 内容
     */
	@NotBlank(message="内容不能为空")
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
