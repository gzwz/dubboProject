package cn.qumiandan.shop.enums;

import lombok.Getter;

/**
 * 店铺状态
 * 1: 创建待审核中；
 * 2：创建审核未通过；
 * 3：审核通过正常营业；
 * 4：更新审核中；
 * 5：更新审核未通过（审核通过为3）；
 * 6：主动申请关闭；
 * 7：平台强制关闭
 */
/**
 * 
 * 店铺状态 枚举
 * @author yuleidian
 * @version 创建时间：2018年12月11日 上午11:48:52
 */
@Getter
public enum ShopStatusEnum {

	//CREATING(new Byte("1"), "店铺创建中待审核"),
	// ----------------------店铺创建中和扫呗相关--------------------------------
	CREATECOMMIT(new Byte("2"), "营业员提交待审核"),
	//PASSCOMMIT(new Byte("3"), "审核通过待扫呗审核"),
	//UPDATENUPASS(new Byte("4"), "扫呗审核未通过 待用户修改"),
	//UPDATECOMMIT(new Byte("5"), "用户修改后待审核"),
	UNPASS(new Byte("6"), "店铺审核未通过"),
	PASS(new Byte("7"), "店铺审核通过"),
	//UPDATING(new Byte("8"), "审核更新"),
	
	// ----------------------店铺更新信息--------------------------------
	LOCALUPDATECOMMIT(new Byte("9"), "更新提交待审核"),
	LOCALUPDATEUNPASS(new Byte("10"), "更新审核未通过(审核通过为7)"),
	
	// ------------------------关闭店铺相关---------------------------------------
	APPLYCLOSE(new Byte("11"), "主动申请关闭"),
	PLATFORMCLOSE(new Byte("12"), "平台强制关闭"),
	;
	/** 状态*/
	private Byte code;
	/** 状态说明*/
	private String name;
	
	ShopStatusEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
