package cn.qumiandan.shirofilter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.shirofilter.entity.ShiroFilter;
import cn.qumiandan.shirofilter.vo.ShiroFilterVO;
@Mapper
public interface ShiroFilterMapper extends BaseMapper<ShiroFilter>{
	
    /**
     * 查询权限列表信息
     * @return
     */
    List<ShiroFilterVO> getShiroFilterVOOrderBySort();
}