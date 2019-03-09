package cn.qumiandan.pay.withdraw.enums;

import lombok.Getter;

/**
 * 提现申请状态枚举
 * @author yuleidian
 * @version 创建时间：2019年1月5日 下午1:52:21
 */
@Getter
public enum WithdrawStatusEnum {
	
//	状态（1. 审核中 2.未通过  3.等待打款  4.自动打款异常 5.打款完成  6.手动打款 ）

	/**审核中*/
	ApplyAuditing(new Byte("1"),"审核中"),
	/**未通过*/
	AuditUnPass(new Byte("2"),"未通过"),
	/**等待打款*/
	WaitingCashWithdraw(new Byte("3"),"等待打款中"),
	/**自动打款异常*/
	AbnormalAutomaticWithdraw(new Byte("4"),"自动打款异常"),
	/**打款完成*/
	FinishedWithdraw(new Byte("5"),"打款完成"),
	/**手动打款*/
	ManualWithdraw(new Byte("6"),"手动打款"),
	;
	
	private Byte code;
	
	private String name;

	WithdrawStatusEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
