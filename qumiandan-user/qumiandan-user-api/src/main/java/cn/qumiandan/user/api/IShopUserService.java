package cn.qumiandan.user.api;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.user.vo.AddShopUserVO;
import cn.qumiandan.user.vo.ShopUserDetailVO;
import cn.qumiandan.user.vo.ShopUserRoleVO;
import cn.qumiandan.user.vo.ShopUserVO;
import cn.qumiandan.user.vo.UpdateShopUserRoleVO;
import cn.qumiandan.user.vo.UpdateShopUserVO;

public interface IShopUserService extends IBaseService {

	/**
	 * 添加店铺用户人员
	 * @param record
	 * @return
	 */
	ShopUserVO addShopUser(ShopUserVO record);

	/**
	 * 获取用户店铺分页信息
	 * @param params
	 * @return
	 */
	PageInfo<ShopUserVO> getShopUserPageListByUserId(CommonParams params);

	/**
	 * 通过店铺用户表id查询 
	 * 店铺用户信息并带出所管店铺信息
	 * @param params
	 * @return
	 */
	ShopUserDetailVO getShopUserDetailInfo(CommonParams params);
	
	/**
	 * 根据用户id和角色用户关联id获取 店铺用户关联信息
	 * 此接口实质返回   根据用户id和角色用户关联id 获取 该用户在某个角色下
	 * 所管理的店铺用户信息
	 * @param userId
	 * @param userRoleId
	 * @return
	 */
	List<ShopUserVO> getShopUserInfoByUserIdAndUserRoleId(Long userId, Long userRoleId);

	/**
	 * 根据用户id查询店铺人员列表
	 * @param userId
	 * @return
	 */
	List<ShopUserVO> getShopUserListByUserId(Long userId);


	/**
	 * 根据用户id查询店铺id列表
	 * @param userId
	 * @return
	 */
	List<Long> getShopIdListByUserId(Long userId);
	

	/**
	 * 验证用户是否是某家店的店员
	 * true : 已存在(已和某家店铺建立过关系)
	 * false : 不存在 (没有和任意一家店铺建立过关系)
	 * @param userId
	 * @return
	 */
	boolean memberExist(Long userId);
	
	/**
	 * 添加店铺成员信息
	 * @param addShopUserVO
	 * @return
	 */
	int addMemberShopInfo(AddShopUserVO addShopUserVO);
	
	
	/**
	 * 修改成员角色
	 * @param shopUserId
	 * @param roleId
	 * @return
	 */
	int updateMemberRole(UpdateShopUserRoleVO vo);
	
	/**
	 * 修改成员管理的店铺信息
	 * @param shopUserId
	 * @param shopIds
	 * @return
	 */
	int updateMemberManageShop(UpdateShopUserVO vo);
	
	/**
	 * 删除成员 店铺用户关系
	 * @param shopUserId
	 * @return
	 */
	int unbindMemberShopUser(Long shopUserId, Long updateId);
	
	/**
	 * 根据店铺id查询 店铺用户信息
	 * @param shopId
	 * @return
	 */
	List<ShopUserVO> getShopUserListByShopId(Long shopId);
	
	
	/**
	 * 根据店铺id查询店铺 店铺用户信息
	 * @param shopId
	 * @return
	 */
	PageInfo<ShopUserVO> getShopUserPageListByShopId(CommonParams params);
	
	/**
	 * 根据用户id查询店铺id集合
	 * @param userId
	 * @return
	 */
	Set<Long> getShopIdsByUserId(Long userId);
	
	/**
	 * 根据店铺id查询店铺相关人员信息
	 * @param shopId
	 * @return
	 */
	List<ShopUserRoleVO> getShopUserRoleByShopId(Long shopId);
}