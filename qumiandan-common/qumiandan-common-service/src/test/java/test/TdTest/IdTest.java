package test.TdTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.idfactory.api.IdFactory;
import test.BaseTest;

public class IdTest extends BaseTest{
	
	@Autowired
	private IdFactory idfactory;
	
	@Test
	public void getId() throws InterruptedException{
		String id = idfactory.getId();
		System.out.println(id);
		Thread.sleep(1000);
		id = idfactory.getId();
		System.out.println(id);
		Long of = Long.valueOf(id);
		System.out.println(of);

	}
	
}
