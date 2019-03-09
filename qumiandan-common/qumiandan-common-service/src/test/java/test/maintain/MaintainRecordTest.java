package test.maintain;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.maintain.api.IMaintainRecordService;
import cn.qumiandan.maintain.vo.MaintainRecordVO;
import test.BaseTest;

public class MaintainRecordTest extends BaseTest{

	@Resource
	private IMaintainRecordService maintainRecordService;
	
	@Test
	public void getAllMaintainRecord() {
		PageInfo<MaintainRecordVO> queryMaintainRecord = maintainRecordService.queryMaintainRecord(null, 1, 10);
		System.out.println(queryMaintainRecord);
	}
}
