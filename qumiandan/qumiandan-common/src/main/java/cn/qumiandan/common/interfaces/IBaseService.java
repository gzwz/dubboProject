package cn.qumiandan.common.interfaces;

/**
 * 公共service
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午10:41:32
 */
public interface IBaseService {

	/**
	 * 检查单表 影响单条数据时使用
	 * @param ret
	 * @return
	 */
	default boolean checkCUD(int ret) {
		return ret == 1 ? true : false;
	}
}
