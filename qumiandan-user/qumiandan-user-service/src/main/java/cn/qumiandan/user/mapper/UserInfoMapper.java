package cn.qumiandan.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.user.entity.UserInfo;
import cn.qumiandan.user.vo.UserInfoVO;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo>{
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    /**
     * 根据userId更新sys_user_info表内用户信息
     * @param userInfo
     * @return
     */
    int updateUserInfoByUserId(UserInfo userInfo);
    
    /**
     * 根据用户id查询用户扩展信息
     * @param userId
     * @return
     */
    UserInfoVO selectUserInfoByUserId(Long userId);
}