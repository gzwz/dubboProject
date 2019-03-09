package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 模糊查询商品列表（分页）参数
 * @author lrj
 *	@version 创建时间：2018年11月12日 11:12
 */
@Getter
@Setter
@ToString
public class GetProductLikeParams extends CommonParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@NotBlank(message="查询条件不能为空")
	private String inputInfo;
	
}
