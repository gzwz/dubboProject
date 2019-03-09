package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 根据区code查询店铺
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class GetCountyCodeParams extends CommonParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 区code
	 */
	@NotNull(message = "省市区code不能为空")
	private Integer code;
	
	
	@NotNull(message = "级别不能为空")
	private Integer level;
	
}
