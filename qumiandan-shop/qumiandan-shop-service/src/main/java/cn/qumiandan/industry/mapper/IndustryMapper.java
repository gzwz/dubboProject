package cn.qumiandan.industry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.industry.entity.Industry;
import cn.qumiandan.industry.vo.IndustryVO;

@Mapper
public interface IndustryMapper extends BaseMapper<Industry>{
    
    /**
     * 根据name查询行业信息
     * @param name
     * @return
     */
    IndustryVO getIndustryVOByName(@Param("name")String name,@Param("parentId")Long  parentId);
  
    /**
	 * 获取 一级行业信息
	 * @return
	 */
	List<IndustryVO> getIndustryByLevelAndParentId(@Param("level") Integer level , @Param("parentId")Long parentId);
}