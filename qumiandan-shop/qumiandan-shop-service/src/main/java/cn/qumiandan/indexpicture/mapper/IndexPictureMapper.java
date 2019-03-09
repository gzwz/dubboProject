package cn.qumiandan.indexpicture.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.indexpicture.entity.IndexPicture;
import cn.qumiandan.indexpicture.vo.IndexPictureVO;

@Mapper
public interface IndexPictureMapper extends BaseMapper<IndexPicture> {

    /**
     * 获取首页轮播图集合（三张）
     * @return
     */
    List<IndexPictureVO> getCenterIndexPictureListByCode(@Param("areaCode") String areaCode);

    /**
     * 获取最大排序
     * @return
     */
    Integer getMaxSort();

    int deleteByFileName(String fileName);
    
    /**
     * 总后台查询首页轮播图
     * @param areaCode
     * @param currentDate
     * @param status
     * @return
     */
    List<IndexPictureVO> queryIndexPicture(@Param("areaCode")String areaCode , @Param("isValid")Byte isValid, @Param("status")Byte status);
}
