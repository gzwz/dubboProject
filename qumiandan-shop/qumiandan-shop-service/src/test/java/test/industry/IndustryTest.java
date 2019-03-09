package test.industry;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.industry.api.IIndustryService;
import cn.qumiandan.industry.vo.GetAllIndustryVO;
import cn.qumiandan.industry.vo.IndustryVO;
import test.BaseTest;

public class IndustryTest extends BaseTest{
	
	@Resource
    private IIndustryService industryService;
	
	
	@Test
	public void addIndustry() throws QmdException {
		System.out.println("---------addIndustry-----------");
		IndustryVO industry=  new IndustryVO();
		industry.setName("医疗1");
		industry.setParentId(1L);
		industry.setLevel(3);
		industry.setRateCode("code");
		int i = industryService.addIndustry(industry);
		System.out.println(i+"添加成功");
	}
	

	@Test
	public void getAllIndustry()  {
		System.out.println("---------getAllIndustry-----------");
		List<GetAllIndustryVO> list = industryService.getAllIndustry();
		System.out.println(list);
	}
	
	@Test
	public void updateIndustry() throws QmdException {
		System.out.println("---------updateIndustry-----------");
		IndustryVO industry=  new IndustryVO();
		industry.setName("医疗1");
		industry.setId(213L);
		int i = industryService.updateIndustry(industry);
		System.out.println(i+"成功");
	}
	
	@Test
	public void deleteIndustry() throws QmdException {
		System.out.println("---------deleteIndustry-----------");
		int i = industryService.deleteIndustry(13L);
		System.out.println(i+"成功");
	}

	@Test
	public void getIndustryVOById()  {
		System.out.println("---------getIndustryVOById-----------");
		IndustryVO industryVO= industryService.getIndustryVOById(13L);
		System.out.println(industryVO+"成功");
	}
	
	@Test
	public void getIndustryByLevelAndParentId()  {
		System.out.println("---------getIndustryByLevelAndParentId-----------");
		List<IndustryVO> industryVO= industryService.getIndustryByLevelAndParentId(1L);
		System.out.println(industryVO+"成功");
	}
}
