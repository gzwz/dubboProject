package cn.qumiandan.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.user.entity.ShopUser;
import cn.qumiandan.user.vo.ShopUserDetailVO;
import cn.qumiandan.user.vo.ShopUserRoleVO;
import cn.qumiandan.user.vo.ShopUserVO;

@Mapper
public interface ShopUserMapper extends BaseMapper<ShopUser>{

    int insertSelective(ShopUser record);

    ShopUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopUser record);

    /**
     * 根据用户id查询 店铺用户关联信息
     * @param params
     * @return
     */
    List<ShopUserVO> getShopUserPageListByUserId(@Param("params") CommonParams params);

    /**
     * 根据总店管理员查询店铺编号集合
     * @param userId
     * @return
     */
	List<ShopUserVO> getShopUserListByUserId(Long userId);

	/**
	 * 根据id获取 店铺用户信息详细信息
	 * @param id
	 * @return
	 */
	ShopUserDetailVO getShopUserDetailInfoById(@Param("id") Long id);
	
	/**
	 * 根据用户id和角色用户关联id获取 店铺用户关联信息
	 * 此接口实质返回   根据用户id和角色用户关联id 获取 该用户在某个角色下
	 * 所管理的店铺用户信息
	 * @param userId
	 * @param userRoleId
	 * @return
	 */
	List<ShopUserVO> getShopUserInfoByUserIdAndUserRoleId(@Param("userId") Long userId, @Param("userRoleId") Long userRoleId);

	/**
	 * 根据总店管理员查询店铺编号集合
	 * @param userId
	 * @return
	 */
	List<Long> getUserShopIdListByUserId(@Param("userId") Long userId);
	
	/**
	 * 根据店铺用户id删除店铺用户关联信息
	 * @param userId
	 * @return
	 */
	Integer deleteShopUserInfoByUserId(@Param("userId") Long userId);
	
	
	/**
	 * 统计用户管理的店铺数
	 * @param userId
	 * @return
	 */
	Integer countMemberShopNum(@Param("userId") Long userId);
	
	/**
	 * 根据店铺id查询店铺用户信息
	 * @param shopId
	 * @return
	 */
	List<ShopUserVO> getShopUserListByShopId(@Param("shopId")Long shopId);
	
	
	/**
	 * 根据店铺id查询店铺用户分页信息
	 * @param shopId
	 * @return
	 */
	List<ShopUserVO> getShopUserPageListByShopId(@Param("params")CommonParams params);
	
	/**
	 * 根据店铺id查询店铺相关人员信息
	 * @param shopId
	 * @return
	 */
	List<ShopUserRoleVO> getShopUserRoleByShopId(Long shopId);
}