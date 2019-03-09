package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 成员详细信息请求参数
 * @author yuleidian
 * @version 创建时间：2018年11月27日 上午10:03:34
 */
@Data
public class MemberInfoDetailParams {

	@NotNull(message = "必须传入id")
	private Long id;
	
}
