package cn.qumiandan.utils;

import java.util.Collection;
import java.util.Map;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.utils.ObjectUtils;

/**
 * 订单中心断言工具
 * 
 * @author WLZ 2018年12月4日
 */
public class AssertUtil {
	
	/** 参数不能为空 */
	public static String ParamCanNotNULL = "参数不能为空";
	/** 对象不能为空 */
	public static String ObjCanNotNULL = "对象不能为空";
	
	/**
	 * 判断对象是否都为空，其中一个对象为空，则抛出异常
	 * @param objs 需要判空的对象。。。
	 */
	public static void ObjectsIsNull(Object ... objs) {
		for (Object object : objs) {
			if (ObjectUtils.isEmpty(object)) {
				throw new QmdException(object+" : "+ObjCanNotNULL);
			}
		}
	}
	
	public static void isNull(Object obj, String exceptionMsg) {
		if (ObjectUtils.isEmpty(obj)) {
			throw new QmdException(exceptionMsg);
		}
	}

	public static void isNull(String str, String exceptionMsg) {
		if (ObjectUtils.isEmpty(str)) {
			throw new QmdException(exceptionMsg);
		}
	}

	public static void isNull(Collection<?> obj, String exceptionMsg) {
		if (null == obj || obj.isEmpty()) {
			throw new QmdException(exceptionMsg);
		}
	}

	public static void isNull(Map<?, ?> str, String exceptionMsg) {
		if (ObjectUtils.isEmpty(str)) {
			throw new QmdException(exceptionMsg);
		}
	}

	public static void isNull(Object[] array, String exceptionMsg) {
		if (ObjectUtils.isEmpty(array)) {
			throw new QmdException(exceptionMsg);
		}
	}
}
