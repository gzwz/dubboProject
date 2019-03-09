package cn.qumiandan.web.merchantServer.membermanager.entity.request;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求查询成员分页信息参数
 * @author yuleidian
 * @version 创建时间：2018年11月24日 下午1:52:38
 */
@Getter
@Setter
public class MemberInfoParams extends CommonParams {

	private static final long serialVersionUID = -4307871926611210152L;

	private String name;
	
	private String mobile;
	
	
}
