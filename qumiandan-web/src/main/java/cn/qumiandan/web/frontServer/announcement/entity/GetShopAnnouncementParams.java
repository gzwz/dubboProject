package cn.qumiandan.web.frontServer.announcement.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GetShopAnnouncementParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 归属id（归属平台则是地区code(不同地区，公告不同)，归属店铺则是店铺id）
	 */
	@NotBlank(message="归属id不能为空")
	private String belongId;
	
	/**
	 * 查询的数据量
	 */
	private Integer count;
}
