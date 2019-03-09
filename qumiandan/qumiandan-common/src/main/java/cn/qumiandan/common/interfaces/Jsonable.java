package cn.qumiandan.common.interfaces;

import cn.qumiandan.utils.GsonHelper;

/**
 * 实现此接口的对象
 * 可以把对象本身转换成json字符串
 * @author yuleidian
 * @version 创建时间：2018年12月7日 下午12:04:54
 */
public interface Jsonable {

	/**
	 * 转换json
	 * @return
	 */
	default String toJson() {
		return GsonHelper.toJson(this);
	}
}
