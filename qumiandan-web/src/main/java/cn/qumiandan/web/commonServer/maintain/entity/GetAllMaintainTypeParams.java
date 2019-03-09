package cn.qumiandan.web.commonServer.maintain.entity;

import java.io.Serializable;

import lombok.Data;
/**
 * 查询所有维护类型参数
 * @author lrj
 *
 */
@Data
public class GetAllMaintainTypeParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 模块类型（1：用户端；2：商家端；3：代理端；4：业务员端）
	 */
	private Long moduleType;
}
