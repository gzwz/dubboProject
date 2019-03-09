package cn.qumiandan.backgrounddata.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 查询用户、店铺、业务员、代理数量参数
 * @author lrj
 *
 */
@Data
public class NumberStatisticsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户数量
	 */
	private Integer userNum;
	
	/**
	 * 店铺数量
	 */
	private Integer shopNum;
	
	/**业务员数量
	 * 
	 */
	private Integer salemanNum;
	
	/**
	 * 代理数量
	 */
	private Integer agentNum;
}
