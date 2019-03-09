package cn.qumiandan.pay;

/**
 * 支付体系工厂接口
 * @author yuleidian
 * @version 创建时间：2018年12月4日 下午2:18:04
 */
public interface IPayFactory {
	
	/**
	 * 解密
	 * @param cryptograph
	 * @return
	 */
	String decrypt(String cryptograph);
}
