package cn.qumiandan.web.commonServer.address.entity.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class GetCodeParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * （省/市/县/区）编号
	 */
	private Integer code;
}
