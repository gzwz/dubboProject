package cn.qumiandan.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 配置属性
 * @author yuleidian
 * @version 创建时间：2018年11月26日 下午5:07:22
 */
@Data
@TableName("sys_properties")
public class SysProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;					// 编号
	
	private String name;				// 配置名
	
	private String value;				// 配置值
	
}
