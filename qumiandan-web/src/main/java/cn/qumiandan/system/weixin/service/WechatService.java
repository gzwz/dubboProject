package cn.qumiandan.system.weixin.service;

import cn.qumiandan.system.weixin.entity.OALoginToken;
import cn.qumiandan.system.weixin.entity.WechatUserInfo;

/**
 * 微信请求service
 * @author yuleidian
 * @version 创建时间：2018年11月3日 下午7:17:53
 */
public interface WechatService {
	
	/**
	 * 
	 * 通过code获取access_token
	 * @param code
	 * @return
	 */
	OALoginToken getAccessTokenByCode(String code); 
	
	/**
	 * 通过token信息获取用户列表
	 * @param token
	 * @return
	 */
	WechatUserInfo getWeiXinUserInfo(OALoginToken token);
	
}
