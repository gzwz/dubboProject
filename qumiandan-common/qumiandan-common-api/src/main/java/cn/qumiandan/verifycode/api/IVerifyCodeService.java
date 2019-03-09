package cn.qumiandan.verifycode.api;

import cn.qumiandan.verifycode.vo.IdentifyingCodeVO;
import cn.qumiandan.verifycode.vo.VerifyCodeAndImgVO;


public interface IVerifyCodeService {

	/**
	 * 判断手机号是否发送频率过快
	 * @param mobile
	 * @return
	 * 过快返回 true
	 * 否则返回 false
	 */
	boolean validationByMobileAndIp(String mobile,String ip);
	
	/**根据手机号获取验证码*/
	String getVerifyCodeByMobile(String mobile, String ip);
	
	/**验证手机号和短信验证码是否匹配
	 * @return
	 * 验证匹配通过，返回true,
	 * 不通过，返回业务异常
	 */
	boolean validationVerifyCodeAndMobile(String mobile,String code); 
	
	/**获取验证码和验证码图片流*/
	VerifyCodeAndImgVO getVerifyCodeAndImg(String sign);

	/**
	 * 验证图片验证码是否正确
	 * @param sign 请求验证码时候的唯一标志
	 * @param code 
	 *  验证匹配通过，返回true,
	 *  不通过，返回 false
	 */
	boolean validationImgVerifyCode(String sign,String code);

	/**
	 * 在sys_identifying_code表中添加短信验证码记录
	 * @param identifyingCodeVO
	 * @return
	 */
	int addIdentifyingCode(IdentifyingCodeVO identifyingCodeVO );
}
