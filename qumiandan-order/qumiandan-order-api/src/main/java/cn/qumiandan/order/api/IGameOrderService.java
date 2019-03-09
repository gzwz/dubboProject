package cn.qumiandan.order.api;

import cn.qumiandan.order.vo.GameOrderDetailVO;

import java.math.BigDecimal;
import java.util.List;

import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.order.vo.GameOrderVO;

public interface IGameOrderService {
	
	
	/**
	 * 获取游戏单价，规则写死
	 * @param orderPaidPrice 订单实付
	 * @param orderTotalAmount 订单总价
	 * @return
	 */
	BigDecimal getGamePrice(BigDecimal orderNeedPay);
	
	/**
	 * 计算游戏是否中奖
	 * @param gameOrder
	 */
	void startShakeGame(GameExtendVO gameOrder);
	
	
	/**
	 * 根据订单id创建游戏订单
	 * @param orderId 主订单id
	 * @param times 倍数
	 */
	GameOrderVO addGameOrder(String orderId, BigDecimal price,Integer times);
	
	/**
     * 根据游戏扩展表编号查询订单以及游戏相关的详情
     * @param orderId
     * @return
     */
    GameOrderDetailVO getOrderByGameId(String gameId);
    
    /**
     * 根据游戏扩展表编号查询游戏相关的详情
     * @param orderId
     * @return
     */
    GameExtendVO getGameOrderByGameId(String gameId);
    
    /**
     * 更改游戏订单状态
     * @param gameId
     * @param status
     * @return
     */
    Integer setGameOrderStatus(String gameId,Byte status);
    
    /**
     * 根据id修改游戏订单
     * @param gameOrderExtendVO
     * @return
     */
    Integer updateGameOrderById(GameExtendVO gameOrderExtendVO);
    
    /**
     * 根据订单id查询游戏订单集合
     * @param orderId
     * @return
     */
    List<GameExtendVO> getGameExtendByOrderId(String orderId);
}
