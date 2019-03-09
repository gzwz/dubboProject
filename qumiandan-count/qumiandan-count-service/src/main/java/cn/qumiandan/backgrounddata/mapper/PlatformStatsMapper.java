package cn.qumiandan.backgrounddata.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.qumiandan.backgrounddata.vo.StatVO;

@Mapper
public interface PlatformStatsMapper {

	/**
	 * 实收金额（已核销）
	 */
	List<StatVO> queryReceivedAmount(@Param("startTime") Date startTime,@Param("endTime") Date endTime );
	

	/**
	 * 商家成本
	 * @return
	 */
	List<StatVO> queryMerchantCost(@Param("startTime") Date startTime,@Param("endTime") Date endTime );
	
	/**
	 * 平台利润
	 * @return
	 */
	List<StatVO> queryPlatformProfit(@Param("startTime") Date startTime,@Param("endTime") Date endTime );
	
	/**
	 * 手续费
	 * @return
	 */
	List<StatVO> queryServiceFee(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	/**
	 * 游戏支付金额
	 * @return
	 */
	List<StatVO> queryGameAmount(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	
	/**
	 * 游戏中奖金额
	 * @return
	 */
	List<StatVO> queryGameWinAmount(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	
	/**
	 * 提现金额
	 * @return
	 */
	List<StatVO> queryGameWithdrawAmount(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

}
