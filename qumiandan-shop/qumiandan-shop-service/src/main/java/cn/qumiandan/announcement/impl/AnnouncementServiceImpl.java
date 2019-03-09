package cn.qumiandan.announcement.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.announcement.api.IAnnouncementService;
import cn.qumiandan.announcement.entity.Announcement;
import cn.qumiandan.announcement.mapper.AnnouncementMapper;
import cn.qumiandan.announcement.vo.AnnouncementPageInfoVO;
import cn.qumiandan.announcement.vo.AnnouncementVO;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = IAnnouncementService.class)
public class AnnouncementServiceImpl implements IAnnouncementService{

	@Autowired
	private AnnouncementMapper announcementMapper;
	
	/**
	 * 添加公告
	 * @param announcementVO
	 * @return
	 */
	@Override
	public int addAnnouncement(AnnouncementVO announcementVO) {
		Announcement announcement = CopyBeanUtil.copyBean(announcementVO, Announcement.class);
		announcement.setCreateDate(new Date());
		if(announcement.getSort()==null) {
			Integer sort = announcementMapper.getMaxSort();
			announcement.setSort((sort!=null?sort:0)+1);
		}
		int i = announcementMapper.insert(announcement);
		return i;
	}

	/**
	 * 更新公告
	 * @param announcementVO
	 * @return
	 */
	@Override
	public int updateAnnouncement(AnnouncementVO announcementVO) {
		Announcement announcement = CopyBeanUtil.copyBean(announcementVO, Announcement.class);
		announcement.setUpdateDate(new Date());
		int i = announcementMapper.updateById(announcement);
		return i;
	}

	/**
	 * 删除公告
	 * @param id
	 * @return
	 */
	@Override
	public int deleteAnnouncement(Long id) {
		Announcement announcement = new Announcement();
		announcement.setId(id);
		announcement.setStatus(StatusEnum.normal.getCode());
		int i = announcementMapper.updateById(announcement);
		return i;
	}

	/**
	 * 根据类型、归属id查询公告
	 * @param type
	 * @param belongId
	 * @param count
	 * @return
	 */
	@Override
	public List<AnnouncementVO> getAnnouncementByBoLongId(Byte type, String belongId,Integer count) {
		return announcementMapper.getAnnouncementByBoLongId(type, belongId, count);
	}

	/**
	 * 根据id（主键）查询公告
	 */
	@Override
	public AnnouncementVO getAnnouncementById(Long id) {
		Announcement announcement = announcementMapper.selectById(id);
		if(announcement == null) {
			return null;
		}
		AnnouncementVO announcementVO = CopyBeanUtil.copyBean(announcement, AnnouncementVO.class);
		return announcementVO;
	}

	/**
	 * 总后台分页查询公告
	 */
	@Override
	public PageInfo<AnnouncementVO> getAnnouncementPageInfo(AnnouncementPageInfoVO announcementPageInfoVO) {
		PageHelper.startPage(announcementPageInfoVO.getPageNum(), announcementPageInfoVO.getPageSize());
		List<AnnouncementVO> list = announcementMapper.getAnnouncementByBoLongId(announcementPageInfoVO.getType(), announcementPageInfoVO.getBelongId(), null);
		PageInfo<AnnouncementVO> pageInfo = new PageInfo<AnnouncementVO>(list);
		pageInfo.setTotal(pageInfo.getTotal());
		return pageInfo;
	}

}
