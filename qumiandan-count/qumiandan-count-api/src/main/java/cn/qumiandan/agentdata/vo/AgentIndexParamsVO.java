package cn.qumiandan.agentdata.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
/**
 * 代理端首页统计参数类
 * @author lrj
 *
 */
@Data
public class AgentIndexParamsVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 代理用户id
	 */
	private Long userId;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 *  结束时间
	 */
	private Date endTime;
	
	private List<Long> shopIds;
	
	/**
	 * 代理类型：1：业务员;2:区代理；3：市代理；4：省代理；
	 */
	private Byte type ;
	
	/**
	 * 流水类型
	 */
	private List<Byte> tradeTypeList;
	
	
	
}
