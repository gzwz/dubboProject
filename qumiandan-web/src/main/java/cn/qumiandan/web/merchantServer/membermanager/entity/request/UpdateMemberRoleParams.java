package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改店铺成员请求参数
 * @author yuleidian
 * @version 创建时间：2018年12月3日 上午10:49:04
 */
@Data
public class UpdateMemberRoleParams implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 店铺成员编号 (主表id)*/
	@NotNull(message = "店铺成员编号不能为空")
	private Long shopUserId;
	
	/** 店铺管理员备注名 */
	private String remarkName;
	
	/** 角色编号*/
	@NotNull(message = "角色编号不能为空")
	private Long roleId;
	
	private Long updateId;
	
}
