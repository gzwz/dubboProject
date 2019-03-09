package test;

import java.util.ArrayList;
import java.util.List;

import cn.qumiandan.utils.CopyBeanUtil;
import lombok.Data;

public class Test {

	public static void main(String[] args) throws Exception {
		AAA a = new AAA();
		a.setAaa("aaaa");
		List<String> list = new ArrayList<String>();
		list.add("asdfa");
		list.add("wtwtwe");
		a.setList(list );
		BBB bbb = CopyBeanUtil.copyBean(a, BBB.class);
		System.out.println(bbb);
		
	}
	@Data
	static class AAA{
		private List<String> list;
		private String aaa;
	}
	
	@Data
	static class BBB{
		private List<String> list;
		private String aaa;
	}
	

}
