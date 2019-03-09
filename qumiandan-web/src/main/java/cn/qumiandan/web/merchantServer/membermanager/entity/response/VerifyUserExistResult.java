package cn.qumiandan.web.merchantServer.membermanager.entity.response;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * 通过手机号验证账户是否存在
 * @author yuleidian
 * @version 创建时间：2018年11月29日 下午3:48:42
 */
@Data
public class VerifyUserExistResult implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 表示用户账号是否存在 true:不存在  false:存在*/
	@Expose
	private Boolean accountFlag = Boolean.FALSE;
	
	/** 用户是否已经添加过 店铺管理员信息   true:店铺成员中不存在  false:店铺成员中存在*/
	@Expose
	private Boolean shopMemberFlag = Boolean.FALSE;
	
	/** 用户id*/
	@Expose
	private Long userId;
	
	public VerifyUserExistResult() {}

	public VerifyUserExistResult(Boolean accountFlag, Boolean shopMemberFlag, Long userId) {
		super();
		this.accountFlag = accountFlag;
		this.shopMemberFlag = shopMemberFlag;
		this.userId = userId;
	}
}
