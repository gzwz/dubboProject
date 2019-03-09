package cn.qumiandan.web.frontServer.ticket.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 创建资格券参数
 * @author lrj
 *
 */
@Data
public class CreateTicketParams implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/** 归属用户id */
	@NotNull(message = "归属用户id不能为空")
	private Long userId;
	
	/** 名字 */
	@NotBlank(message = "资格券名不能为空")
	private String name;
	
	/** 现价 */
	@NotNull(message = "资格券现价不能为空")
	private BigDecimal presentPrice;
	
	/** 返现金额 */
	@NotNull(message = "资格券返现金额不能为空")
	private BigDecimal returnMoney;
	
	/**
	 * 返现门槛
	 */
	@NotNull(message = "返现门槛不能为空")
	private BigDecimal cashbackDoorsill;
	
	/**
	 * 创建数量 
	 */
	@NotNull(message = "创建数不能为空")
	private Long num;
	
	/** 创建者id */
	private Long createId;
	
}
