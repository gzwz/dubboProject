package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * id、分页参数
 * @author lrj
 * @version 创建时间：2018年11月12日 10:38
 */
@Getter
@Setter
@ToString
public class GetIdAndPagingParams extends CommonParams implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@NotNull(message="id不能为空")
	private Long id;
}
