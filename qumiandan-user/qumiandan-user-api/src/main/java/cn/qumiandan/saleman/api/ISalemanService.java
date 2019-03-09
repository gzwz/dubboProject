package cn.qumiandan.saleman.api;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.saleman.vo.AddSalmanVO;
import cn.qumiandan.saleman.vo.SalemanAndRoleVO;
import cn.qumiandan.saleman.vo.SalemanAndUserParamVO;
import cn.qumiandan.saleman.vo.SalemanAndUserVO;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.saleman.vo.ShopAgentVO;
import cn.qumiandan.saleman.vo.UpdateSalmanVO;

/**
 * 系统管理员接口
 * @author lrj
 *
 */
public interface ISalemanService extends IBaseService {
	
	/**
	 * 添加系统管理员
	 * @param managerVO
	 * @return
	 */
	SalemanVO addSaleman(AddSalmanVO salemanVO) ;
	
	/**
	 * 修改系统管理员
	 * @param managerVO
	 * @return
	 */
	int updateSaleman(SalemanVO salemanVO);
	
	/**
	 * 删除管理员
	 * @param id
	 * @return
	 */
	int deleteSaleman(Long id);
	
	/**
	 * 查询管理员信息
	 * @param id
	 * @return
	 */
	SalemanVO getSalemanById(Long id); 
	
	/**
	 * 根据用户id查询管理员信息
	 * @param userId
	 * @return
	 */
	SalemanVO getSalemanByUserId(Long userId);
	
	/**
	 * 根据业务员手机号查询业务员信息
	 * @param userName
	 * @return
	 */
	SalemanVO getSalemanByUserName(String userName);
	
	/**
	 * 总后台查询业务员列表
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<SalemanAndUserVO>  querySalemanAndUser(SalemanAndUserParamVO params);
	
	
	/**
	 * 根据店铺id查询代理及业务信息
	 * @return
	 */
	ShopAgentVO getAgentAndSalemanByShopId(Long shopId);
	
	/**
	 * 根据code和type查询代理信息
	 * @param code
	 * @param type
	 * @return
	 */
	SalemanVO getAgentByCodeAndType(String code , Byte type);
	
	/**
	 * 根据店铺id查询上级所有的业务级信息
	 * @param shopId
	 * @return
	 */
	List<SalemanVO> getAgentSalemenByShopId(Long shopId);
	
	/**
	 * 查询省、市、区代理、业务员信息及其角色信息
	 * @param userId
	 * @return
	 */
	SalemanAndRoleVO getSalemanAndRoleByUserId(Long userId);
	
	/**
	 * 判断下线关系
	 * 
	 * @param agentUseId
	 *            上线用户id
	 * @param OfflineUserId
	 *            下线用户id
	 * @return 是上下线关系则返回true,否则返回false
	 */
	Boolean isOffline(Long agentUseId, Long OfflineUserId);
	
	/**
	 * 根据用户id查询业务员信息
	 * @param userId
	 * @return
	 */
	SalemanAndUserVO getSalemanAndUserByUserId(Long userId);
	
	/**
	 * 
	 * ***********************************************
	 * 目前只支持更改 
	 * 1.费率 
	 * 2.银行卡信息
	 * ***********************************************
	 * 更新业务员信息
	 * @param vo
	 */
	void updateSalemanInfo(UpdateSalmanVO vo);
	
	/**
	 * 解除业余员 代理关系
	 * @param id
	 */
	void unBindSaleman(Long id, Long operator);
	
	/**
	 * 验证是不是业务员中的某个类型角色
	 * true 是
	 * false 不是
	 * @param userId
	 * @param type
	 * @return
	 */
	Boolean existSaleman(Long userId, Byte...type);
}
