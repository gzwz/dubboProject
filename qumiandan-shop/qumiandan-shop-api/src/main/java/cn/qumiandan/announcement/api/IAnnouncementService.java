package cn.qumiandan.announcement.api;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.announcement.vo.AnnouncementPageInfoVO;
import cn.qumiandan.announcement.vo.AnnouncementVO;

/**
 * 公告管理接口
 * @author lrj
 *
 */
public interface IAnnouncementService {

	/**
	 * 添加公告
	 * @param announcementVO
	 * @return
	 */
	int addAnnouncement(AnnouncementVO announcementVO);
	
	/**
	 * 更新公告
	 * @param announcementVO
	 * @return
	 */
	int updateAnnouncement(AnnouncementVO announcementVO);
	
	
	/**
	 * 删除公告
	 * @param id
	 * @return
	 */
	int deleteAnnouncement(Long id);
	
	/**
	 * 根据类型、归属id查询公告
	 * @param type
	 * @param belongId
	 * @param count
	 * @return
	 */
	List<AnnouncementVO> getAnnouncementByBoLongId(Byte type,String belongId,Integer count  );

	
	/**
	 * 根据id（主键）查询公告
	 * @param id
	 * @return
	 */
	AnnouncementVO getAnnouncementById(Long id);
	
	/**
	 * 总后台查询公告（分页）
	 * @param announcementPageInfoVO
	 * @return
	 */
	PageInfo<AnnouncementVO> getAnnouncementPageInfo(AnnouncementPageInfoVO announcementPageInfoVO);
}
