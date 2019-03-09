package cn.qumiandan.common.exception;

import cn.qumiandan.common.interfaces.IGenericEnum;
import lombok.Getter;

/**
 * 基础异常枚举
 * @author W
 *  
 */
@Getter
public enum OrErrorCode implements IGenericEnum {
	/** 成功*/
	OR0000("OR0000", "成功"),
	/** 系统异常*/
	OR0001("OR0001", "系统异常"),
	/** 数据库服务异常 */
	OR0002("OR0002", "数据库服务异常"),
	/** Redis服务器异常*/
	OR0003("OR0003", "Redis服务器异常"),
	
	
	/** 订单中心业务异常 */
	OR1000("OR1000", "订单中心业务异常"),
	/** 参数不能为空 */
	OR1001("OR1001", "参数不能为空"),
	
	// 创建优惠券
	/** 优惠券模板不能为空 */
	OR1002("OR1002", "优惠券模板不能为空"),
	
	// 领取优惠券
	/** 优惠券不存在 */
	OR1003("OR1003", "优惠券不存在"),
	/** 优惠券规则有误 */
	OR1004("OR1004", "优惠券规则有误"),
	/** 优惠券已领取 */
	OR1005("OR1005", "优惠券已领取"),
	/** 优惠券模板存在 */
	OR1006("OR1006", "优惠券模板存在"),
	/** 优惠券创建规则有误 */
	OR1007("OR1007", "优惠券创建规则有误"),
	/** 优惠券领取规则有误 */
	OR1008("OR1008", "优惠券领取规则有误"),
	/** 优惠券使用规则有误 */
	OR1009("OR1009", "优惠券使用规则有误"),
	
	
	
	/** 优惠券未使用*/
	OR1010("OR1010","优惠券未使用"),
	/** 优惠券已删除*/
	OR1011("OR1011","优惠券已删除"),
	/** 优惠券已使用*/
	OR1012("OR1012","优惠券已使用"),
	/** 优惠券已过期*/
	OR1013("OR1013","优惠券已过期"),
	/** 优惠券已销毁*/
	OR1014("OR1014","优惠券已销毁"),
	/** 优惠券未到生效时间*/
	OR1015("OR1015","优惠券未到生效时间"),
	/** 该优惠券已经失效*/
	OR1016("OR1016","该优惠券已经失效"),
	
	/** 非新用户不能领取该优惠券 */
	OR1020("OR1020","非新用户不能领取该优惠券"),
	/** 该优惠券包含指定不能使用的商品 */
	OR1021("OR1021","该优惠券包含指定不能使用的商品"),
	;
	
	private String code;
	private String msg;
	
	private OrErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
