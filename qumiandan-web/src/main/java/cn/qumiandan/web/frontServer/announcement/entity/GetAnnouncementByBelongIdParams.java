package cn.qumiandan.web.frontServer.announcement.entity;

import java.io.Serializable;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetAnnouncementByBelongIdParams extends CommonParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 归属id（归属平台则是地区code(不同地区，公告不同)，归属店铺则是店铺id）
	 */
//	@NotBlank(message="归属id不能为空")
	private String belongId;
	
	/**
	 * 类型(1.店铺公告，2.平台公告)
	 */
//	@NotNull(message="类型不能为空")
	private Byte type;
	
	/**
	 * 查询的数据量
	 */
	private Integer count;
}
