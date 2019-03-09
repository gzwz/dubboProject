package cn.qumiandan.announcement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.announcement.entity.Announcement;
import cn.qumiandan.announcement.vo.AnnouncementVO;
@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement>{
    
	/**
	 * 根据类型、归属id查询公告
	 * @param type
	 * @param belongId
	 * @param count
	 * @return
	 */
	List<AnnouncementVO> getAnnouncementByBoLongId(@Param("type")Byte type,@Param("belongId")String belongId,@Param("count")Integer count  );

	/**
	 * 获取最大的sort值
	 * @return
	 */
	Integer getMaxSort();
}