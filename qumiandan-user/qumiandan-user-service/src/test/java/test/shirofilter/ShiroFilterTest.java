package test.shirofilter;

import javax.annotation.Resource;
import org.junit.Test;
import cn.qumiandan.shirofilter.api.IShiroFilterService;
import cn.qumiandan.shirofilter.vo.ShiroFilterVO;
import test.BaseTest;

public class ShiroFilterTest  extends BaseTest {
	@Resource
	private IShiroFilterService shiroFilterService;
	
	@Test
	public void addShiroFilter() throws Exception {
		System.out.println("---addShiroFilter---");
		ShiroFilterVO shiroFilterVO=new ShiroFilterVO();
		shiroFilterVO.setName("测试权限2");
		shiroFilterVO.setPerms("测试perms2");
		shiroFilterVO.setSort(1);
		shiroFilterService.addShiroFilter(shiroFilterVO);
	}
	@Test
	public void updateShiroFilterVOById() throws Exception {
		System.out.println("---updateShiroFilterVOById---");
		ShiroFilterVO shiroFilterVO=new ShiroFilterVO();
		shiroFilterVO.setId(1l);
		shiroFilterVO.setName("测试权限1...");
		shiroFilterVO.setPerms("测试perms...");
		shiroFilterVO.setSort(1);
		shiroFilterService.updateShiroFilterVOById(shiroFilterVO);
	}
	@Test
	public void deleteShiroFilterVOById() throws Exception {
		System.out.println("---updateShiroFilterVOById---");
		System.out.println(shiroFilterService.deleteShiroFilterVOById(1l));
	}
	@Test
	public void getShiroFilterVOById() {
		System.out.println("---getShiroFilterVOById---");
		System.out.println(shiroFilterService.getShiroFilterVOById(2l));
	}
	
	@Test
	public void getShiroFilterVOOrderBySort() {
		System.out.println("---getShiroFilterVOOrderBySort---");
		System.out.println(shiroFilterService.getShiroFilterVOOrderBySort());
	}
}
