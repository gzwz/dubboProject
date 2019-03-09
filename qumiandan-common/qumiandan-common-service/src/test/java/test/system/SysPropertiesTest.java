package test.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.system.api.ISysPropertiesService;
import cn.qumiandan.system.enums.SysPropertiresEnums;
import cn.qumiandan.system.vo.SysPropertiesVO;
import test.BaseTest;

public class SysPropertiesTest extends BaseTest{
	
	@Autowired
	private ISysPropertiesService sysPropertiesService;
	
	@Test
	public void getSysPropertiesInfoById() throws Exception {
		SysPropertiesVO vo = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.SHOPMAXMEMBERS.getId());
		System.out.println(vo);
	}
	
}
