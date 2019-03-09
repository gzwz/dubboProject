package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 解除店铺和成员的绑定关系请求参数
 * @author yuleidian
 * @version 创建时间：2018年12月3日 下午12:07:56
 */
@Data
public class UnbindShopUserParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 店铺用户id*/
	@NotNull(message = "店铺用户编号不能为空")
	private Long shopUserId;
	
	/** 操作用户id*/
	private Long updateId;
}
