package cn.qumiandan.payaccount.api.process;

import org.springframework.boot.CommandLineRunner;

import cn.qumiandan.payaccount.vo.TradeDataVO;

/**
 * 实现此接口,处理并发下失败账户增减余额
 * @author yuleidian
 * @version 创建时间：2018年12月19日 上午11:19:54
 */
public interface IAccountProcess extends CommandLineRunner {

	/**
	 * 添加任务到任务队列
	 * @param task
	 */
	void add(AccountTask<TradeDataVO> task);
	
}
