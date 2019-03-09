package cn.qumiandan.web.frontServer.role.entity.request;

import java.io.Serializable;

import lombok.Data;
/**
 * 获取菜单类型参数
 * @author lrj
 *
 */
@Data
public class GetTypeParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Byte type;
}
