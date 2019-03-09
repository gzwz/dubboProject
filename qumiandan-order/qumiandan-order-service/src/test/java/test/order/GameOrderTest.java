package test.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.vo.GameExtendVO;
import test.BaseTest;

public class GameOrderTest extends BaseTest {

	@Autowired
	private IGameOrderService gservice;
	
	@Test
	public void teasdfas() {
		GameExtendVO game = gservice.getGameOrderByGameId("2019011419509222613975040");
		System.out.println(game);
	}

	
	@Test
	public void updateGameOrderById() {
		GameExtendVO gameOrderExtendVO = new GameExtendVO();
		gameOrderExtendVO.setId("2018121737227517924343862");
		gameOrderExtendVO.setOrderStatus(new Byte("3"));
		int i = gservice.updateGameOrderById(gameOrderExtendVO);
		System.out.println(i);
	}
}
