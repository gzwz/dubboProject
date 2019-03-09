package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GetIdParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "id不能为空")
	private Long id;
}
