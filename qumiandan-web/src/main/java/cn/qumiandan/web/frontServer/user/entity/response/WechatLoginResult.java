package cn.qumiandan.web.frontServer.user.entity.response;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.annotations.Expose;

import cn.qumiandan.system.weixin.entity.WechatUserInfo;
import cn.qumiandan.user.vo.UserVO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信授权登返回结果
 * @author yuleidian
 * @version 创建时间：2018年11月6日 上午10:16:40
 */
@Getter
@Setter
@Slf4j
public class WechatLoginResult extends LoginToken {

	@Expose
	private WechatUserInfo wechatUserInfo;		// 微信端登录	
	
	@Expose
	private UserInfo userInfo;			// 登录成功后携带给前端的用户信息
	
	public WechatLoginResult() {}
	
	public WechatLoginResult(UserVO user){
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
