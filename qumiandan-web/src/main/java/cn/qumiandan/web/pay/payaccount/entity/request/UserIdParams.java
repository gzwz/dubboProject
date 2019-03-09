package cn.qumiandan.web.pay.payaccount.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 用户id参数类
 * @author lrj
 *
 */
@Data
public class UserIdParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空")
	private Long userId;
}