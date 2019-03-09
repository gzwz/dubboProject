package cn.qumiandan.web.frontServer.user.entity.response;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.annotations.Expose;

import cn.qumiandan.user.vo.UserVO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录返回结果
 * @author yuleidian
 * @version 创建时间：2018年11月7日 下午12:17:13
 */
@Setter
@Getter
@Slf4j
public class AccountLoginResult extends LoginToken {

	@Expose
	private UserInfo userInfo;			// 登录成功后携带给前端的用户信息
	
	public AccountLoginResult() {}
	
	public AccountLoginResult(UserVO user){
		if (user != null) {
			userInfo = new UserInfo();
			try {
				BeanUtils.copyProperties(userInfo, user);
			} catch (Exception e) {
				log.error("",e);
				
			}
		}
	}
}
