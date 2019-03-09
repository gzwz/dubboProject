package cn.qumiandan.payaccount.vo;

import java.io.Serializable;

import cn.qumiandan.payaccount.api.process.AccountTask;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import lombok.Data;

/**
 * 账户余额增加
 * @author yuleidian
 * @version 创建时间：2018年12月28日 上午9:45:38
 */
@Data
public class AccountExceptionTaskVO implements AccountTask<TradeDataVO> , Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private TradeDataVO tradeData;
	
	/** 尝试次数*/
	private int times;

	public AccountExceptionTaskVO() {
		this.times = 0;
	}

	@Override
	public void increaseTimes() {
		this.times++;
	}

	@Override
	public void setSourceData(TradeDataVO data) {
		this.tradeData = data;
	}

	@Override
	public TradeDataVO getSourceData() {
		return this.tradeData;
	}

}
