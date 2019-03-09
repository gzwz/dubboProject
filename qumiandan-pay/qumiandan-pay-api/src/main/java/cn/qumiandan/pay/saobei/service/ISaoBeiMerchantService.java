package cn.qumiandan.pay.saobei.service;

import cn.qumiandan.pay.IPayFactory;
import cn.qumiandan.pay.saobei.vo.SaoBeiShopAllInfoVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.BaseMerchantResVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.CreateMerchantResVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.CreateTerminalResVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.QueryTerminalResVO;

/**
 * 扫呗商户service
 * @author yuleidian
 * @version 创建时间：2018年12月4日 下午2:25:42
 */
public interface ISaoBeiMerchantService extends IPayFactory {

	/**
	 * 商户名验证重复
	 * @param req
	 * @return
	 */
	BaseMerchantResVO checkMerchantName(String merchantName);
	
	/**
	 * 创建商户
	 * @param req
	 * @return
	 */
	CreateMerchantResVO createMerchant(SaoBeiShopAllInfoVO vo);
	
	/**
	 * 更新商户
	 * @param vo
	 */
	CreateMerchantResVO updateMerchant(SaoBeiShopAllInfoVO vo);
	
	/**
	 * 创建终端
	 * @param req
	 * @return
	 */
	CreateTerminalResVO createTerminal(String merchantNo);
	
	/**
	 * 查询终端
	 * @param req
	 * @return
	 */
	QueryTerminalResVO queryTerminal(String terminalId);
	
}
