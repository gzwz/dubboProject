package cn.qumiandan.validationticket.process;

/**
 * 账户任务
 * @author yuleidian
 * @version 创建时间：2018年12月19日 上午11:22:57
 */
public interface TicketTask<T> {

	/**
	 * 增加重试次数
	 */
	void increaseTimes();

	/**
	 * 获取重试次数
	 * @return
	 */
	int getTimes();
	
	/**
	 * 设置数据源
	 */
	void setSourceData(T data);
	
	/**
	 * 获取数据源
	 * @return
	 */
	T getSourceData();
	
}
