package cn.qumiandan.orderreturn.enums;

import lombok.Getter;

/**
 * 退款申请状态
 * @author yuleidian
 * @version 创建时间：2018年12月30日 下午2:44:12
 */
@Getter
public enum RefundStatusEnum {

	/** 创建退款申请*/
	CREATE(new Byte("1"), "创建退款申请"),
	/** 审批通过*/
	PASS(new Byte("2"), "审批通过"),
	/** 审批不通过*/
	UNPASS(new Byte("3"), "审批不通过"),
	/** 等待打款*/
	WAIT(new Byte("4"), "等待打款"),
	/** 退款完成*/
	FINISH(new Byte("5"), "退款完成"),
	/** 平台介入*/
	PLATFORM(new Byte("6"), "平台介入"),
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	RefundStatusEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
