package cn.qumiandan.utils;

/**
 * 核销卷Utils
 * @author yuleidian
 * @version 创建时间：2019年1月2日 上午11:42:36
 */
public final class ValidationTicketUtils {

	private ValidationTicketUtils() {}
	
	/**
     * 创建核销码
     * @param userId
     * @param shopId
     * @param orderId
     * @return
     */
	public static String getValidationTicket(Long userId, Long shopId, String orderId) {
		String code = userId + orderId + shopId;
		code = MD5Utils.encode(code);
		// 加密后的字符串
		return code;
	}
}
