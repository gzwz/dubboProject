package cn.qumiandan.user.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 店铺用户详细信息VO
 * @author yuleidian
 * @version 创建时间：2018年11月27日 上午10:05:31
 */
@Getter
@Setter
public class ShopUserDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 店铺用户表id*/
	private Long id;
	
	/** 店铺id*/
	private Long shopId;
	
	/** 用户id*/
	private Long userId;
	
	/** 用户名*/
	private String userName;
	
	/** 用户角色关联id*/
	private Long userRoleId;
	
	
	/** 用户昵称*/
	private String nickName;
	
	/** 角色id*/
	private String roleId;
	
	/** 角色名称*/
	private String roleName;
	
    /**
     * 备注名
     */
    private String remarkName;
	
	/** 状态*/
	private Byte status;
	
	/** 管理店铺列表*/
	private List<ShopInfoVO> shopList;
	
}
