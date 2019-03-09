package cn.qumiandan.web.frontServer.user.entity.response;

import com.google.gson.annotations.Expose;

import cn.qumiandan.user.vo.UserAddVO;
import cn.qumiandan.utils.CopyBeanUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户注册返回结果
 * @author yuleidian
 * @version 创建时间：2018年11月10日 下午2:49:47
 */
@Getter
@Setter
//@Slf4j
public class UserRegisterResult extends LoginToken {

	@Expose
	private UserInfo userInfo;			// 登录成功后携带给前端的用户信息
	
	public UserRegisterResult() {}
	
	public UserRegisterResult(UserAddVO user)  {
		if (user != null) {
			this.userInfo = CopyBeanUtil.copyBean(user, UserInfo.class);
		}
	}
}
