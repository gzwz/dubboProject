package test;

import cn.qumiandan.common.exception.QmdException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
	

	public static void main(String[] args) {

		/*String str = "20181129870356915060736";
		long long1 = Long.parseLong(str);
		System.out.println(long1);*/
		QmdException e = new QmdException("报错了。。。");
		log.info("{ε=(´ο｀*)))唉}",e);
	}

}
