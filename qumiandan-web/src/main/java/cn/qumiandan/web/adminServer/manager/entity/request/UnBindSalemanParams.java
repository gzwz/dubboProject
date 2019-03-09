package cn.qumiandan.web.adminServer.manager.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 解除管理员权限信息
 * @author yuleidian
 * @date 2019年1月22日
 */
@Data
public class UnBindSalemanParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "id编号不能为空")
	private Long id;
	
}
