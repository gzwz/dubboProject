package cn.qumiandan.announcement.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AnnouncementVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 类型
	 * 1：店铺公告,
	 *	2:平台公告,
	 *	3:平台对业务员公告,
	 *	4:平台对店铺公告,
	 */
	private Byte type;

	/**
	 * 归属平台则是地区code(不同地区，公告不同)，归属店铺则是店铺id
	 */
	private String belongId;

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
	
	  /**
     * 1:正常；0：删除；（默认1）
     */
    private Byte status;

	/**
	 * 创建者
	 */
	private Long createId;

	/**
	 * 更新者
	 */
	private Long updateId;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date updateDate;
}
