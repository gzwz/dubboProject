package test.apply;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.apply.api.IApplyService;
import cn.qumiandan.apply.vo.ApplyVO;
import cn.qumiandan.apply.vo.QueryApplyVO;
import test.BaseTest;

public class ApplyShopTest extends BaseTest{
	
	@Resource
	private IApplyService openShopApplyService ;

	@Test
	public void addApply() {
		ApplyVO applyVO = new ApplyVO();
		applyVO.setProCode("110000");
		applyVO.setCityCode("110101");
		applyVO.setCountryCode("110101");
//		applyVO.setIndustryId(1L);
		applyVO.setCityName("测试");
		applyVO.setType(new Byte("1"));
		applyVO.setMobile("18888888888");
		ApplyVO addApply = openShopApplyService.addApply(applyVO);
		System.out.println(addApply);
	}
	
	
	@Test
	public void queryApply() {
		QueryApplyVO applyVO = new QueryApplyVO();
		applyVO.setPageNum(1);
		applyVO.setPageSize(10);
		applyVO.setType(new Byte("5"));
		PageInfo<ApplyVO> queryApply = openShopApplyService.queryApply(applyVO);
		System.out.println(queryApply);
	}
	
	@Test
	public void queryApplyById() {
		ApplyVO queryApplyById = openShopApplyService.queryApplyById(10L);
		System.out.println(queryApplyById);
	}
}