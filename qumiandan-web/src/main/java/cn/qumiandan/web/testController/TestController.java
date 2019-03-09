package cn.qumiandan.web.testController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.qumiandan.utils.GsonHelper;
import cn.qumiandan.web.frontServer.user.entity.request.AccountUserParams;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/")
public class TestController {

	@RequestMapping(value = "createMerchantCallback", produces = "text/xml;charset=UTF-8")
	public Results createMerchantCallback(@RequestBody(required = false) CreateMerchantReq req) {
		if (Objects.isNull(req)) {
			req = new CreateMerchantReq();
		}
		System.out.println(GsonHelper.toJson(req));
		Results s = new Results();
		s.setTrace_no(req.getTrace_no());
		return s;
	}
	
	
	//@RequiresRoles("sys")
	//@RequiresPermissions({"/content/add", "/content/del"})
	@RequestMapping("init")
	public String test(@RequestBody(required = false) AccountUserParams params ) {
		//Subject subject = SecurityUtils.getSubject();
		/*cn.qumiandan.system.shiro.ShiroRealm.User u = (cn.qumiandan.system.shiro.ShiroRealm.User) subject.getPrincipal();
		log.info(u.getId());
		log.info(u.getPassword());
		log.info(u.getUsername());*/
		return "test成功";
	}
	
	//private String TOKEN = "weixin";
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request,
        HttpServletResponse response, BufferedReader br) throws IOException {
		//Header部分
		log.info(request.getHeaderNames().toString());
        Enumeration<?> enum1 = request.getHeaderNames();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            log.info(key + "\t" + value);
        }
        //body部分
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
        	log.info("IOException: " + e);
        }
        log.info("str:" + str);
        
        log.info("ok - ok - ok - ok - ok - ok - ok - ok - ok");
        return "ok";
	}
	
	
	/*
	 * @RequestMapping("login") public String login(String username, String
	 * password) { try { UsernamePasswordToken token = new
	 * UsernamePasswordToken(username, password); // 登录不在该处处理，交由shiro处理 Subject
	 * subject = SecurityUtils.getSubject(); subject.login(token); if
	 * (subject.isAuthenticated()) { log.info((String)
	 * subject.getSession().getId()); } else {
	 * 
	 * } } catch (IncorrectCredentialsException | UnknownAccountException e) {
	 * 
	 * } catch (LockedAccountException e) {
	 * 
	 * } catch (Exception e) {
	 * 
	 * } return "成功"; }
	 */
	@RequestMapping("MP_verify_MOtnIfWnj4WazbVJ.txt")
	public String consumer1() {
		return "MOtnIfWnj4WazbVJ";
	}
}
