package cn.qumiandan.analyseData.mapper;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.analyseData.entity.ActualAmountNum;
@Mapper
public interface AnalyseDataMapper extends BaseMapper<ActualAmountNum>{
	
	/**
	 * 根据起始时间和结束时间统计时间区域内的总收入
	 * @param startTime起始时间
	 * @param endTime结束时间
	 * @return
	 */

	
	BigDecimal actualAmount(Date startTime,Date endTime); 
	/**
	 * 统计支付笔数接口
	 * @param startTime起始时间
	 * @param endTime结束时间
	 * @return
	 */
	Long totalPayNum();
}
