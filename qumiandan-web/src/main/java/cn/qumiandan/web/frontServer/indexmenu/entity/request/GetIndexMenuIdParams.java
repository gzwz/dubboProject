package cn.qumiandan.web.frontServer.indexmenu.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 获取首页菜单id参数类
 * @author lrj
 *
 */
@Data
public class GetIndexMenuIdParams implements Serializable{
		
	private static final long serialVersionUID = 1L;
	
	/**
	 * 首页菜单id
	 */
	@NotNull(message="首页菜单id不能为空")
	private Long id;
}
