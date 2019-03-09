package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 总店管理员查询自己管理的店铺参数
 * @author lrj
 *
 */
@Data
public class GetShopByManagerParams implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	@NotNull(message="用户id不能为空")
	private Long userId;
	
	/**
	 * 角色id
	 */
//	@NotNull(message="角色id不能为空")
//	private Long roleId;
	
}
