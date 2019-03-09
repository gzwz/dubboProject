package cn.qumiandan.web.frontServer.industry.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 获取行业id参数类
 * @author lrj
 *
 */
@Data
public class GetIdParams implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@NotNull(message="id不能为空")
	Long id;
}
