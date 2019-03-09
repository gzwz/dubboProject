package cn.qumiandan.user.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 查询店铺相关人员返回参数
 * @author lrj
 *
 */
@Data
public class ShopUserRoleVO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户店铺关联表主键
	 */
	private Long id;
	
	/**
	 * 用户编号
	 */
	private Long userId;
	
	/**
	 * 用户名（手机号）
	 */
	private String userName;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 角色名
	 */
	private String roleName;
	
    /**
     * 备注名
     */
    private String remarkName;
}
