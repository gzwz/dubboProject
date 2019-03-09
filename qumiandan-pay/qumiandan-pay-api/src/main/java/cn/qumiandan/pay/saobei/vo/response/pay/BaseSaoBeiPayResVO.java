package cn.qumiandan.pay.saobei.vo.response.pay;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

import cn.qumiandan.pay.saobei.vo.response.AbstractSaoBeiResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 扫呗基础返回信息
 * @author yuleidian
 * @version 创建时间：2018年12月8日 下午2:36:59
 */
@Getter
@Setter
public class BaseSaoBeiPayResVO extends AbstractSaoBeiResponse {
	
	private static final long serialVersionUID = 1L;
	
	protected String encrypt(String content) {
		return doEncrypt(content, "UTF-8");
	}
	
	 /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param input_charset 编码格式
     * @return 签名结果
     */
    private static String doEncrypt(String text, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}
