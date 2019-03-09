package cn.qumiandan.shop.api;

import java.util.List;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;

/**
 * 平台审核记录查询
 * @author yuleidian
 * @version 创建时间：2018年12月10日 下午2:21:44
 */
public interface IShopAuditRecordService extends IBaseService {

	/**
	 * 添加审核记录
	 * @param vo
	 * @return
	 */
	ShopAuditRecordVO addShopAuditRecord(ShopAuditRecordVO vo);
	
	
	/**
	 * 根据shopId获取最新的审核记录
	 * @param shopId
	 * @return
	 */
	ShopAuditRecordVO getNewestAuditRecord(Long shopId);
	
	/**
	 * 根据店铺id查询店铺审核记录
	 * @param shopId
	 * @return
	 */
	List<ShopAuditRecordVO> getShopAuditRecordByShopId(Long shopId);
}
