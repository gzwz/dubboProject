package cn.qumiandan.user.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户店铺关联分页vo
 * @author yuleidian
 * @version 创建时间：2018年11月24日 下午4:03:19
 */
@Data
public class ShopUserPageVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 店铺id
	 */
	private Long shopId;

	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户角色id
	 */
	private Long userRoleId;
	
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

	/**
	 * 状态(1:正常；0：删除)
	 */
	private Byte status;
}
