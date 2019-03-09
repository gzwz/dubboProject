package cn.qumiandan.web.frontServer.apply.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 根据主键查询开店申请详情参数
 * @author lrj
 *
 */
@Data
public class QueryByIdParams implements Serializable{


	private static final long serialVersionUID = 1L;

	@NotNull(message = "申请id不能为空")
	private Long id;
}
