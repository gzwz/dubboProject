package cn.qumiandan.constant;

import lombok.Getter;
/**
 * 时间段标识枚举
 * @author lrj
 *
 */
@Getter
public enum TimeStatusEnum {

	
	
	/**近7天*/
	NearlySevenDays((byte)0,"近7天"),
	
	/**当天*/
	Today((byte)1,"当天"),
	
	/**当天*/
	Yesterday((byte)2,"昨天"),

	
	;
	private Byte code;
	
	private String desc;

	private TimeStatusEnum(Byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
