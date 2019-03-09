package test.gameOrder;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.vo.GameOrderDetailVO;
import test.BaseTest;

public class GameOrderTest extends BaseTest {
	
	@Autowired
	private IGameOrderService gameService;
	
	@Test
	public void addGameOrderTest() {
		gameService.addGameOrder("201901032785539929407488",new BigDecimal(1), 3);
	}
	
	@Test
	public void getOrderByGameIdTest() {
		GameOrderDetailVO detailVO = gameService.getOrderByGameId("201901033168645240848384");
		System.out.println(detailVO);
	}
	
	
}
