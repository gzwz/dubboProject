package cn.qumiandan.user.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 解除店铺成员VO
 * @author yuleidian
 * @version 创建时间：2018年12月3日 上午11:32:46
 */
@Data
public class UnbindShopUserVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 店铺用户id*/
	private Long shopUserId;
	
	/** 操作用户id*/
	private Long updateId;
}
