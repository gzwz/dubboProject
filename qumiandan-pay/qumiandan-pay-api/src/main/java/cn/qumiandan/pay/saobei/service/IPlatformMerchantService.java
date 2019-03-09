package cn.qumiandan.pay.saobei.service;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.pay.saobei.vo.PlatformMerchantVO;

/**
 * 平台创建的商户
 * @author yuleidian
 * @version 创建时间：2018年12月18日 下午2:40:29
 */
public interface IPlatformMerchantService extends IBaseService {

	/**
	 * 随机获取一条商户信息
	 * @return
	 */
	PlatformMerchantVO randomGetMerchant();
	
	/**
	 * 根据编号查询平台商户信息
	 * @param id
	 * @return
	 */
	PlatformMerchantVO getPlatformMerchantById(Long id);
	
	/**
	 * 根据商户号查询merchantNo
	 * @param merchantNo
	 * @return
	 */
	PlatformMerchantVO getPlatformMerchantByMerchantNo(String merchantNo);
	
}
