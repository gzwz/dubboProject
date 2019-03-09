package cn.qumiandan.web.countServer.sellerIndexData.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SellerIndexDataParams implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 查询的时间类型（为0时表示近7天内，为1时表示当天，为2时表示昨天）
	 */
	@NotNull(message = "查询的时间段标识不能为空")
	private Byte timeStatus;
}
