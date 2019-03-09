package cn.qumiandan.system.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 配置信息VO
 * @author yuleidian
 * @version 创建时间：2018年11月26日 下午5:17:51
 */
@Data
public class SysPropertiesVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;					// 编号
	
	private String name;				// 配置名
	
	private String value;				// 配置值
	
}
