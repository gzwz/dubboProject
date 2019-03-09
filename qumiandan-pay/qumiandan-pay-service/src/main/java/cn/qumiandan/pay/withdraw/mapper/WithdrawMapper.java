package cn.qumiandan.pay.withdraw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.pay.withdraw.entity.WithdrawCash;
import cn.qumiandan.pay.withdraw.vo.QueryWithdrawCashParamsVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashVo;

@Mapper
public interface WithdrawMapper extends BaseMapper<WithdrawCash>{
	
	List<WithdrawCashVo> queryWithdrawCash(@Param("paramsVO")QueryWithdrawCashParamsVO paramsVO);
	
}
