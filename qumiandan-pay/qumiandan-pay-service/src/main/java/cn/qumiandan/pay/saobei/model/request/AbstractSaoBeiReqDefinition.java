package cn.qumiandan.pay.saobei.model.request;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.SignatureException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.annotations.SerializedName;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.RequestDefinition;
import cn.qumiandan.utils.GsonHelper;
import lombok.Getter;

/**
 * 扫呗请求定义
 * @author yuleidian
 * @version 创建时间：2018年12月4日 下午6:15:46
 */
@Getter
public abstract class AbstractSaoBeiReqDefinition implements Serializable, RequestDefinition {

	private static final long serialVersionUID = 1L;
	protected static final String AND = "&";
	protected static final String EQ = "=";
	
	/** 签名检验串,拼装所有必填参数+令牌，32位md5加密转换*/
	protected String keySign;
	
	/** 请求参数*/
	protected Map<String, String> reqCondition;
	
	@Override
	public Map<String, String> getRequestParameter() {
		if (reqCondition == null || reqCondition.size() == 0) {
			getSign();
		}
		return reqCondition;
	}
	
	@Override
	public String toJson() {
		return GsonHelper.toJson(getRequestParameter());
	}
	
	protected void createResolveMap() {
		reqCondition = new TreeMap<String, String>((key, key2) -> key.compareTo(key2));
		Class<?> clazz = this.getClass();
		SerializedName paramName;
		Object value;
		while (clazz != null) {
			try {
				for (Field field : clazz.getDeclaredFields()) {
					if (Objects.nonNull(field)) {
						field.setAccessible(true);
						paramName = field.getAnnotation(SerializedName.class);
						if (Objects.nonNull(paramName)) {
							value = field.get(this);
							if (Objects.nonNull(value)) {
								reqCondition.put(paramName.value(), value.toString());
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new QmdException("获取请求签名失败" + e.getMessage());
			}
			clazz = clazz.getSuperclass();
		}
	}
	
	@Override
	public String encrypt(String content) {
		return doEncrypt(content, "UTF-8");
	}
	
	protected String createTranNo() {
		return UUID.randomUUID().toString().replace("-", "");
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
