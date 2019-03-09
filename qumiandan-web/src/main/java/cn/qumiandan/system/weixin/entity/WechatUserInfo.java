package cn.qumiandan.system.weixin.entity;

import java.util.List;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信用户信息user
 * @author yuleidian
 * @version 创建时间：2018年11月3日 下午7:22:53
 * 
 * 微信字段和系统字段不统一，请使用set copy 内容
 */
@Getter
@Setter
@ToString
public class WechatUserInfo extends WechatBase {

	@Expose
	private String openid;				// 用户的唯一标识
	@Expose
	private String nickname;			// 用户昵称
	@Expose
	private String sex;					// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	@Expose
	private String province;			// 用户个人资料填写的省份
	@Expose
	private String city;				// 普通用户个人资料填写的城市
	@Expose
	private String country;				// 国家，如中国为CN
	@Expose
	private String headimgurl;			// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	@Expose
	private List<String> privilege;		// 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	@Expose
	private String unionid;				// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	
	public WechatUserInfo() {
		super();
	}
}
