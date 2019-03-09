package cn.qumiandan.saobeishopinfo.api;

import cn.qumiandan.saobeishopinfo.vo.SaoBeiShopInfoVO;

/**
 * 扫呗商户管理接口
 * @author lrj
 *
 */
public interface ISaoBeiShopInfoServicce {

	/**
	 * 添加扫呗商户信息
	 * @param saoBeiShopInfoVO
	 * @return
	 */
	int addSaoBeiShopInfo(SaoBeiShopInfoVO saoBeiShopInfoVO);
	
	/**
	 * 修改扫呗商户信息(根据店铺id修改)
	 * @param saoBeiShopInfoVO
	 * @return
	 */
	int updateSaoBeiShopInfo(SaoBeiShopInfoVO saoBeiShopInfoVO);
	
	/**
	 * 删除扫呗商户信息
	 * @param shopId
	 * @return
	 */
	int deleteSaoBeiShopInfo(Long shopId);
	
	/**
	 * 根据店铺id查询扫呗商户信息(最新一条商户记录)
	 * @param shopId
	 * @return
	 */
	SaoBeiShopInfoVO getSaoBeiShopInfo(Long shopId);
	
}
