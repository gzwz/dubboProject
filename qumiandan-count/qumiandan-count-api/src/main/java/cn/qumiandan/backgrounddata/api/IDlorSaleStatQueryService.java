package cn.qumiandan.backgrounddata.api;

import java.util.List;

import cn.qumiandan.backgrounddata.vo.DlorSaleStatQueryVO2;

/**
 * 代理和业务员利润接口
 * @author lrj
 *
 */
public interface IDlorSaleStatQueryService {
	
	/**
	 * 查询代理和业务员利润接口
	 * @return
	 */
	List<DlorSaleStatQueryVO2> queryDlorSaleStat();
}
