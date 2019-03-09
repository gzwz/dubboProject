package cn.qumiandan.agentdata.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.agentdata.api.IAgentDataService;
import cn.qumiandan.agentdata.enums.TradeDetailTypeEnum;
import cn.qumiandan.agentdata.mapper.AgentDataMapper;
import cn.qumiandan.agentdata.vo.AgentIndexParamsVO;
import cn.qumiandan.agentdata.vo.AgentIndexVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.utils.ObjectUtils;
/**
 * 代理端统计实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass=IAgentDataService.class)
public class AgentDataServiceImpl implements IAgentDataService{

	@Autowired
	private AgentDataMapper agentMapper;
	
	/**
	 * 根据代理类型组装流水类型
	 * @param type
	 * @return
	 */
	private List<Byte> getTradeDetailTypeList(Byte type) {
		List<Byte> typeList = new ArrayList<>();
		switch (type) {
		case 1:
			//业务员
			typeList.add(TradeDetailTypeEnum.SALEMANORDER.getCode());
			typeList.add(TradeDetailTypeEnum.SALEMANGAMEORDER.getCode());
			break;
		case 2:
			//区代理端
			typeList.add(TradeDetailTypeEnum.COUNTRYORDER.getCode());
			typeList.add(TradeDetailTypeEnum.COUNTRYGAMEORDER.getCode());
			break;
		case 3:
			//市代理端
			typeList.add(TradeDetailTypeEnum.CITYORDER.getCode());
			typeList.add(TradeDetailTypeEnum.CITYGAMEORDER.getCode());
			break;
		case 4:
			//省代理
			typeList.add(TradeDetailTypeEnum.PROVINCEORDER.getCode());
			typeList.add(TradeDetailTypeEnum.PROVINCEGAMEORDER.getCode());
			break;
		default:
			throw new QmdException("IAgentDataService|getTradeDetailTypeList|代理或业务员类型数据出错");
		}
		return typeList;
	}

	@Override
	public AgentIndexVO getAgentIndexData(AgentIndexParamsVO agentIndexParamsVO) {
		AgentIndexVO agentIndexVO = new AgentIndexVO();
		Byte agentType = agentMapper.getAgentTypeByUserId(agentIndexParamsVO.getUserId());
		if(agentType == null) {
			return null;
		}
		// 查询交易额、店铺手续费、订单数量
		List<Long> shopIds = agentMapper.getShopIdsByAgentUserId(agentIndexParamsVO.getUserId(), agentType);
		if(!ObjectUtils.isEmpty(shopIds)) {
			agentIndexParamsVO.setShopIds(shopIds);
			agentIndexVO = agentMapper.getAgentIndexByShopIds(agentIndexParamsVO);
			
		}
		//根据店铺代理类型组装流水类型
		List<Byte> tradeDetailTypeList = getTradeDetailTypeList(agentType);
		agentIndexParamsVO.setTradeTypeList(tradeDetailTypeList);
		//查询分润
		BigDecimal shareProfit = agentMapper.getShareProfit(agentIndexParamsVO);
		agentIndexVO.setShareProfit(shareProfit != null ? shareProfit :new BigDecimal(0));
		//店铺数量
		agentIndexVO.setShopNum(shopIds != null ? shopIds.size() :  0);
		//查询
		List<Long> userIdList = agentMapper.getUserIdList(agentIndexParamsVO.getUserId(), agentType);
		//查询资格券数量
		userIdList.add(agentIndexParamsVO.getUserId());
		Integer ticketNum = agentMapper.getTicketNum(userIdList);
		agentIndexVO.setTicketNum(ticketNum != null ? ticketNum :0);
		//查询代理数量
		Integer agentNum = agentMapper.getAgentNum(agentIndexParamsVO.getUserId(), agentType);
		agentIndexVO.setAgentNum(agentNum != null ? agentNum :0);
		//查询业务员数量
		Integer salemanNum = agentMapper.getSalemanNum(agentIndexParamsVO.getUserId(), agentType);
		agentIndexVO.setSalemanNum(salemanNum != null ? salemanNum :0);
		return agentIndexVO;
	}

}
