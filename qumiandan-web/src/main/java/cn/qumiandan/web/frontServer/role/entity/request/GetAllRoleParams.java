package cn.qumiandan.web.frontServer.role.entity.request;

import java.io.Serializable;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class GetAllRoleParams extends PageInfoParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 角色名
	 */
	private String roleName;
	
	
	public GetAllRoleParams() {
		super();
	}
}
