package cn.qumiandan.system.api;

import cn.qumiandan.system.vo.SysPropertiesVO;

/**
 * 用户中心配置信息service
 * @author yuleidian
 * @version 创建时间：2018年11月26日 下午5:11:26
 */
public interface ISysPropertiesService {

	/**
	 * 根据id获取配置信息
	 * @param id
	 * @return
	 */
	public SysPropertiesVO getSysPropertiesInfoById(Long id);
	
}
