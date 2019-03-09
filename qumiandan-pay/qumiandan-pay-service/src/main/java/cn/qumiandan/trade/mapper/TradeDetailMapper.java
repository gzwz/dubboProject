package cn.qumiandan.trade.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.trade.entity.TradeDetail;
import cn.qumiandan.trade.vo.TradeResultVO;

/**
 * 交易流水mapper
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午11:32:17
 */
@Mapper
public interface TradeDetailMapper extends BaseMapper<TradeDetail> {

	/**
	 * 根据账户id查询入账金额
	 * @return
	 */
	BigDecimal getInAccount(@Param("accountIds") List<Long> accountIds);
	
	/**
	 * 查询出账金额
	 * @return
	 */
	BigDecimal getOutAccount(@Param("accountIds") List<Long> accountIds);
	/**
	 * 获取流水状态
	 * @param id
	 * @return
	 */
	Byte getTradeStatus(@Param("id") Long id);
	
	/**
	 * 获取流水支付结果
	 * @param id
	 * @return
	 */
	TradeResultVO getTradeResult(@Param("id") Long id);
	
}
