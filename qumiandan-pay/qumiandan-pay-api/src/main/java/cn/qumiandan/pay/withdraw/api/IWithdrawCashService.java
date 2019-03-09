package cn.qumiandan.pay.withdraw.api;

import java.util.List;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.pay.withdraw.vo.QueryResponseParamsVO;
import cn.qumiandan.pay.withdraw.vo.QueryWithdrawCashParamsVO;
import cn.qumiandan.pay.withdraw.vo.SalemanLevelApplyWithdrawCaseResultVO;
import cn.qumiandan.pay.withdraw.vo.ShopApplyWithdrawCashResultVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashResultVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashVo;

public interface IWithdrawCashService extends IBaseService {
	
	/**
	 * 店铺提现申请api
	 * @param withdrawCashVo（withdrawCashVo模型）
	 * @return
	 */
	ShopApplyWithdrawCashResultVO shopApplyCash(Long shopId, Long createId);
	
	/**
	 * 店铺提现申请api
	 * @param
	 * @return
	 */
	List<ShopApplyWithdrawCashResultVO> shopOneKeyApplyCash(List<Long> shopIds, Long createId);
	
	/**
	 * 业务员 市 省 申请提现
	 * @param shopId
	 * @param createId
	 * @return
	 */
	SalemanLevelApplyWithdrawCaseResultVO salemanLevelApplyCash(Long userId);
	
	
	/**
	 * 提现申请审批
	 * @return
	 */
	WithdrawCashResultVO applyAudit(WithdrawCashVo withdrawCashVo);
	/**
	 * 支付成功回调接口
	 */
	WithdrawCashVo payCallback(WithdrawCashVo withdrawCashVo);
	
	/**
	 * 根据id查询提现申请信息
	 * @param id
	 * @return
	 */
	WithdrawCashVo getWithdrawCashInfoById(Long id);
	
	/** 
	 * 查询体现记录
	 * @param withdrawCashVo
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	QueryResponseParamsVO queryWithdrawCash(QueryWithdrawCashParamsVO paramsVO);

	/**
	 * 
	 * @param withdrawCashVo
	 */
	void updateWithdrawCashById(WithdrawCashVo withdrawCashVo);
	
	/**
	 * 
	 * 目前为自动打款失败时 线下手动打款成功使用
	 * 
	 * 修改提现申请记录为手动打款
	 * @param id	提现id
	 * @param operator	操作人
	 */
	void manualWithdrawCash(Long id, Long operator);
}
