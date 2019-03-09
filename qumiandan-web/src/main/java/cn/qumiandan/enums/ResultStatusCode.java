package cn.qumiandan.enums;
/**
 * 
 *  1000，表示成功（数字类） 参考
	cn.qumiandan.common.exception.UcErrorCode
	1:开头表示系统类异常(例如：1001，系统错误)
	2:开头表示权限类异常(例如：2001，用户无权限)
	3:开头表示普通检查异常(例如：3001，参数为空)
	4:开头用户中心业务异常
	5:支付中心业务异常
	6:商品中心业务异常
	7:
	9:
	更新中。。。
 * 统一返回状态码
 * @author yld
 *
 */
public enum ResultStatusCode {

	OK(1000, "OK"),
	LOGIN_OK(1001, "登录成功"),
	WAIT_REGISTER_OK(1002, "微信授权成功,等待注册"),
	// 5000开始为错误异常
	ERROR(5000, "系统异常"),
	NOT_EXIST_USER_OR_ERROR_PWD(5001, "该用户不存在或密码错误"),
	LOGIN_CHECK_ERROR(5002, "您还未登录或登录时间过长,请重新登录！"),
	UNAUTHO_ERROR(5003, "您没有该权限"),
	METHOD_NOT_ALLOWED_ERROR(5004, "不支持当前请求方法"),
	RELOGIN_CHECK_ERROR(5005, "您在账号已在另一个地方登录, 请确认是否是本人操作。"),
	//UNKNOWNACCOUNT_ERROR(5006, "该用户不存在或密码错误"),
	LOCKACCOUNT_ERROR(5007, "此账号已被冻结,无法使用。"),
	SHIRO_ERROR(5008, "登录异常"),
	REQUEST_PARAMS_ERROR(6001, "请求参数错误");
	
	private String message;
	
	private int code;

	ResultStatusCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}
}
