package cn.qumiandan.pay.cashback.api;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.pay.cashback.vo.CashbackVO;
import cn.qumiandan.pay.cashback.vo.QueryCashbackRequestParamsVO;
import cn.qumiandan.pay.cashback.vo.QueryCashbackResPonseParamsVO;

/**
 * 返现操作管理接口
 * @author lrj
 *
 */
public interface ICashbackService {
	
	/**
	 * 添加返现信息
	 * @param cashbackVO
	 * @return
	 */
	CashbackVO addCashback(CashbackVO cashbackVO);
	
	/**
	 * 更新返现信息
	 * @param cashbackVO
	 */
	void updateCashback(CashbackVO cashbackVO);
	
	/**
	 * 查询提现记录
	 * @param params
	 * @return
	 */
	PageInfo<QueryCashbackResPonseParamsVO> queryCashback(QueryCashbackRequestParamsVO params);
	
	void auditCashback(CashbackVO cashbackVO);
}
