package cn.qumiandan.agentdata.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.qumiandan.agentdata.vo.AgentIndexParamsVO;
import cn.qumiandan.agentdata.vo.AgentIndexVO;
@Mapper
public interface AgentDataMapper {

	/**
	 * 查询交易额、店铺手续费、订单数量
	 * @param agentIndexParamsVO
	 * @return
	 */
	AgentIndexVO getAgentIndexByShopIds(@Param("agentIndexParamsVO")AgentIndexParamsVO agentIndexParamsVO);
	
	/**
	 * 查询代理分润
	 * @param userId
	 * @param tradeTypeList
	 * @return
	 */
	BigDecimal getShareProfit(@Param("agentIndexParamsVO")AgentIndexParamsVO agentIndexParamsVO);
	
	
	/**
	 * 查询代理相关店铺
	 * @param userId
	 * @param agentType
	 * @return
	 */
	List<Long> getShopIdsByAgentUserId(@Param("userId")Long userId ,@Param("agentType") Byte agentType);

	/**
	 * 查询代理类型
	 * @param userId
	 * @return
	 */
	Byte getAgentTypeByUserId(@Param("userId")Long userId );
	
	/**
	 * 查询当前用户以下用户id集合
	 * @param userId
	 * @param agentType
	 * @return
	 */
	List<Long> getUserIdList(@Param("userId")Long userId ,@Param("agentType") Byte agentType);
	
	/**
	 * 查询资格券数量
	 * @param userIds
	 * @return
	 */
	Integer getTicketNum(@Param("userIds")List<Long> userIds) ;
	
	/**
	 * 查询业务员数量
	 * @param userId
	 * @param agentType
	 * @return
	 */
	Integer getSalemanNum(@Param("userId")Long userId ,@Param("agentType") Byte agentType);
 
	/**
	 * 代理数量
	 * @param userId
	 * @param agentType
	 * @return
	 */
	Integer getAgentNum(@Param("userId")Long userId ,@Param("agentType") Byte agentType);
	
}
