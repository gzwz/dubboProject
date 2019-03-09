package cn.qumiandan.idfactory.api;

/**
 * id生成器
 * @author WLZ
 * 2018年11月8日
 * 固定长度为32位
 */
public interface IdFactory {
	
	/**
	 * @return 返回时间戳为前缀的id 
	 * 固定长度为32位
	 */
	//long getLongId();
	
	/**
	 * @return 返回时间戳为前缀的id 
	 * 固定长度为32位
	 */
	String getId();
	
	
	/**
	 * 根据自定义前缀返回固定长度为32位的字符串id
	 * 不足32位，中间填充0
	 * @param pre 长度 > 0 & < 5
	 * @return
	 */
	//String getPrefixId(String pre)throws CsException ;
	
	
}
