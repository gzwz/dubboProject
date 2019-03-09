package test.indexmenu;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.indexmenu.api.IIndexMenuService;
import cn.qumiandan.indexmenu.vo.IndexMenuVO;
import test.BaseTest;

public class IndexMenuTest extends BaseTest{

	@Autowired
	private IIndexMenuService indexMenuService;
	
	@Test
	public void addIndexMenu() {
		System.out.println("-----indexMenuService-----");
		IndexMenuVO indexMenuVO = new IndexMenuVO();
		indexMenuVO.setName("测试11");
		int i = indexMenuService.addIndexMenu(indexMenuVO);
		System.out.println(i);
	}
	
	@Test
	public void updateIndexMenu() {
		System.out.println("-----updateIndexMenu-----");
		IndexMenuVO indexMenuVO = new IndexMenuVO();
		indexMenuVO.setName("测试");
		indexMenuVO.setId(1L);
		int i = indexMenuService.updateIndexMenu(indexMenuVO);
		System.out.println(i);
	}
	
	
	@Test
	public void deleteIndexMenu() {
		System.out.println("-----deleteIndexMenu-----");
		int i = indexMenuService.deleteIndexMenu(1L);
		System.out.println(i);
	}
	
	@Test
	public void getIndexMenuById() {
		System.out.println("-----getIndexMenuById-----");
		IndexMenuVO indexMenuVO = indexMenuService.getIndexMenuById(1L);
		System.out.println(indexMenuVO);
	}
	
	@Test
	public void getAllIndexMenu() {
		System.out.println("-----getAllIndexMenu-----");
		List<IndexMenuVO> list = indexMenuService.getAllIndexMenu();
		System.out.println(list);
	}
}
