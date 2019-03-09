package cn.qumiandanweb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import cn.qumiandan.MutualWebApplication;
import cn.qumiandan.web.frontServer.user.entity.request.AccountUserParams;
import cn.qumiandan.web.frontServer.user.entity.request.WechatCodeParams;

/**
 * 单元测试controller
 * @author yuleidian
 * @version 创建时间：2018年11月10日 下午4:39:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutualWebApplication.class)
public class QumiandanWebApplicationTests {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void beforeStep() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testControllerAccountLogin() throws Exception {
		AccountUserParams params = new AccountUserParams();
		params.setUsername("13087804778");
		params.setPassword("123456");
		mockMvc.perform(post("/login/accountLogin").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(params))
				).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testControllerWebChatLogin() throws Exception {
		WechatCodeParams params = new WechatCodeParams();
		params.setCode("djahkjdshkajshdajk");
		mockMvc.perform(post("/login/webChatLogin").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(params))
				).andDo(MockMvcResultHandlers.print());
	}
}
