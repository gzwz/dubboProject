package cn.qumiandan.permission.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.permission.entity.Permission;
@Mapper
public interface PermissionMapper extends BaseMapper<Permission>{
 
    Integer getMaxSort();
    
}