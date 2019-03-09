package test.announcement;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.announcement.api.IAnnouncementService;
import cn.qumiandan.announcement.vo.AnnouncementVO;
import cn.qumiandan.utils.ToolUtil;
import test.BaseTest;

/**
 * 公告单元测试
 * @author lrj
 *
 */
public class AnnouncementTest extends BaseTest {

    @Resource
	private IAnnouncementService  announcementService;
	
	@Test
	public void addAnnouncement() {
		System.out.println("------addAnnouncement------");
		AnnouncementVO announcementVO = new AnnouncementVO();
		announcementVO.setContent("公告内容");
		announcementVO.setTitle("公告标题");
		System.out.println(announcementService.addAnnouncement(announcementVO));
	}
	

	@Test
	public void updateAnnouncement() {
		System.out.println("------updateAnnouncement------");
		AnnouncementVO announcementVO = new AnnouncementVO();
		announcementVO.setContent("公告内容");
		announcementVO.setTitle("公告标题");
		announcementVO.setId(1L);
		System.out.println(announcementService.updateAnnouncement(announcementVO));
	}

	
	@Test
	public void deleteAnnouncement() {
		System.out.println("------deleteAnnouncement------");
		System.out.println(announcementService.deleteAnnouncement(1L));
	}
	
//	getAnnouncementByBoLongId
	
	@Test
	public void getAnnouncementByBoLongId() {
		System.out.println("------getAnnouncementByBoLongId------");
		List<AnnouncementVO> announcementVOs = announcementService.getAnnouncementByBoLongId(ToolUtil.intToByte(1), "1", 1);
		System.out.println(announcementVOs);
	}

	@Test
	public void getAnnouncementById() {
		System.out.println("------getAnnouncementById------");
		System.out.println(announcementService.getAnnouncementById(1L));
	}
	
}
