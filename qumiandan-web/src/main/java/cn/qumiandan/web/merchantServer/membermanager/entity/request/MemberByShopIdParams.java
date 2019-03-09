package cn.qumiandan.web.merchantServer.membermanager.entity.request;


import javax.validation.constraints.NotNull;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;

/**
 * 根据店铺id获取店铺用户信息请求参数
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午4:19:14
 */
@Getter
@Setter
public class MemberByShopIdParams extends CommonParams {

	private static final long serialVersionUID = 1L;
	
	/** 店铺id*/
	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
}
