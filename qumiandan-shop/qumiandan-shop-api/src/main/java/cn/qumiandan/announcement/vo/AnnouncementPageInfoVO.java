package cn.qumiandan.announcement.vo;

import java.io.Serializable;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class AnnouncementPageInfoVO extends CommonParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 公告类型
	 */
	private Byte type;
	
	
	/**
	 * 归属id
	 */
	private String belongId;
	
}
