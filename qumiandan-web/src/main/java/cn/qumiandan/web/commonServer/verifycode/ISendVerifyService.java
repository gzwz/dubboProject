package cn.qumiandan.web.commonServer.verifycode;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.verifycode.vo.VerifyCodeAndImgVO;

/**
 * 内部调用发送验证码
 * @author WLZ
 * 2018年12月12日
 */
public interface ISendVerifyService {

	/**发送短信验证码内部自己调用*/
	Result<Void> getSmsVerifyCode(String mobile, String ip);
	
	/**
	 * 验证短信验证码
	 * @param code 验证码
	 * @param mobile 手机号
	 * @return
	 */
	Result<Void> validateSmsVerifyCode(String code, String mobile) ;
	
	/**
	 * 获取图片验证码
	 * @param sign 当前唯一标志
	 * @return
	 */
	Result<VerifyCodeAndImgVO> getImgVerifyCode(String sign);
	
	/**
	 * 验证图片验证码
	 * @param sign 唯一标志
	 * @param code 验证码
	 * @return
	 */
	Result<Void> validateImgVerifyCode(String sign, String code);
}
