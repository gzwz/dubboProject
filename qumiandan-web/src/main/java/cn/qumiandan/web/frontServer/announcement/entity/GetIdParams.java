package cn.qumiandan.web.frontServer.announcement.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class GetIdParams  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 公告id
	 */
	@NotNull(message="公告id不能为空")
	private Long id;
}
