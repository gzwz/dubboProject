package cn.qumiandan.web.testController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.qumiandan.utils.GsonHelper;

@RestController
@RequestMapping("/test/")
public class ParamTest {

	@RequestMapping("paraminit")
	@ResponseBody
	public String param(@RequestBody(required = false)AVO params) {
		System.out.println(params);
		return GsonHelper.toJson(params);
	}
	
	public static void main(String[] args) {
		AVO params = new AVO();
		params.setName("想考米");
		List<BVO> list = new ArrayList<>();
		BVO e = new BVO();
		e.setAge(12);
		list.add(e );
		e = new BVO();
		e.setAge(45);
		list.add(e );
		params.setList(list );
		String json = GsonHelper.toJson(params);
		System.out.println(json);
	}
}
