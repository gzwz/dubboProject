package cn.qumiandan.indexmenu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.indexmenu.entity.IndexMenu;

@Mapper
public interface IndexMenuMapper extends BaseMapper<IndexMenu>{
    
    /**
     * 根据名字查询菜单信息
     * @param name
     * @return
     */
    IndexMenu getIndexMenuByName(String name);
    
    /**
     * 查询所有菜单信息
     * @return
     */
    List<IndexMenu> getAllIndexMenu();
    
    /**
     * 获取最大排序数
     * @return
     */
    Integer getMaxSort();

}