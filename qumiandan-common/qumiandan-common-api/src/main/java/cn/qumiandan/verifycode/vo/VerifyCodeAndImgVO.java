package cn.qumiandan.verifycode.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 验证码
 * @author WLZ
 * 2018年11月8日
 */
@Data
public class VerifyCodeAndImgVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**本次请求验证码的唯一标志*/
	private String sign;
	
	/**文件图片流*/
	private byte[] imgBytes;


}
