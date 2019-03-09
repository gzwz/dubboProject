package cn.qumiandan.web.commonServer.sms.entity;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 测试短信demo
 * 不同的短信建立不同的VO、
 * 
 */
@Getter 
@Setter 
@ToString
public class TestSmsVO extends BaseSmsVO {
	private static final long serialVersionUID = 1L;
	
	@Expose
	private String code;
	
	public static void main(String[] args) {
		TestSmsVO vo = new TestSmsVO();
		vo.setCode("12324324");
		vo.setPhoneNumbers("15089898988");
		System.out.println(vo.toString());
	}
	
	public TestSmsVO() {
		setSignName("智售客");
	}

}
