package cn.qumiandan.user.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 修改店铺用户角色VO
 * @author yuleidian
 * @version 创建时间：2018年12月3日 上午11:12:55
 */
@Data
public class UpdateShopUserRoleVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 店铺用户编号*/
	private Long shopUserId;
	
	/** 角色编号*/
	private Long roleId;
	
	/** 修改人用户编号*/
	private Long updateId;
	
}
