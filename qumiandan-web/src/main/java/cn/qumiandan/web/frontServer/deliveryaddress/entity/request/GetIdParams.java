package cn.qumiandan.web.frontServer.deliveryaddress.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GetIdParams implements Serializable {
	private static final long serialVersionUID = 1L;
 
	@NotNull(message = "收货地址id不能为空")
	private Long id; 
}
