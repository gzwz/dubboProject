package cn.qumiandan.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public abstract class ShiroEncrypt {

	private static final String MD5 = "md5";
	
	/**
	 * 
	 * @param passsword 加密密码
	 * @param salt	盐
	 * @param times	加密次数
	 * @return
	 */
	public static String encrypt(String passsword, String salt, int times) {
		SimpleHash result = new SimpleHash(MD5, passsword, salt, times);
		return result.toString();
	}
}
