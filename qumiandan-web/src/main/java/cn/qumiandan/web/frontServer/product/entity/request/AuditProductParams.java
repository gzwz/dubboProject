package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 审核商品参数
 * @author lrj
 *
 */
@Data
public class AuditProductParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 商品id
	 */
	@NotNull(message = "商品id不能为空")
	private Long productId;

	
	/**
	 * 商品状态
	 */
	@NotNull(message = "商品状态不能为空")
	private Byte status;
}
