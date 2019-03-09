package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 店铺审核参数
 * @author lrj
 *
 */
@Data
public class AuditUpdateShopParams implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
	@NotNull(message = "状态不能为空")
	private Byte status;
}
