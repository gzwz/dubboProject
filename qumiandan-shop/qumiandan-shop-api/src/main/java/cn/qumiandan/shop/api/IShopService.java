package cn.qumiandan.shop.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shop.vo.ManagerShopUpdateVO;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;
import cn.qumiandan.shop.vo.ShopBasicInfoVO;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.shop.vo.ShopDetailVO;
import cn.qumiandan.shop.vo.ShopDynamicVO;
import cn.qumiandan.shop.vo.ShopInfoVO;
import cn.qumiandan.shop.vo.ShopQueryParamVO;
import cn.qumiandan.shop.vo.ShopSimpleVO;
import cn.qumiandan.shop.vo.ShopUpdateVO;
import cn.qumiandan.shop.vo.ShopUserInfoVO;
import cn.qumiandan.shop.vo.ShopVO;

public interface IShopService extends IBaseService {

	/**
	 * 检测店铺名称是否存在
	 * true： 存在
	 * false: 不存在
	 * @return
	 */
	boolean existShopName(String name);
    /**
     * 获取首页店铺列表（分页）
     * @param pageNum
     * @param pageSize
     * @return
     */
//	PageInfo<ShopVO> getIndexShopList(int pageNum, int pageSize);

    /**
     * 根据分类获取首页店铺列表（分页）
     * @param pageNum
     * @param pageSize
     * @return
     */
//	PageInfo<ShopVO> getIndexShopListByType(Long type, int pageNum, int pageSize);

    /**
     * 根据id获取店铺基础信息
     * @return
     */
    ShopBasicVO getShopBasicById(Long shopId);

    /**
     * 根据id获取店铺详情
     * @return
     */
    ShopDetailVO getShopDetailById(Long shopId);

    /**
     * 添加店铺信息(开店  业务员使用)
     * @param shopVO
     * @return
     */
    int addShop(ShopVO shopVO) throws QmdException ;
    
    /**
     * 更新店铺信息(开店  业务员使用)
     * @param shopUpdateVO
     * @return
     */
    void updateShopById(ShopUpdateVO shopUpdateVO);
    
    /**
     * 商家主动申请关闭店铺
     * @return
     */
    int closeShopById(Long shopId);
   
    /**
     * 模糊查询店铺列表(分页)
     * @param inputInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
//    PageInfo<ShopVO> getShopListLike(String inputInfo,int pageNum, int pageSize);
    
    /**
     * 动态查询店铺
     * @param shopDynamicVO
     * @return
     */
    PageInfo<ShopBasicVO> getShopDynamic(ShopDynamicVO shopDynamicVO,int pageNum, int pageSize) ;

    /**
     * 根据总店管理员查询店铺列表
     * @param shopIds
     * @return
     */
    List<ShopBasicVO> getShopByManager(List<Long> shopIds);

    /**
     * 根据总店管理员查询店铺列表
     * @param shopUserInfoVOList
     * @return
     */
    List<ShopBasicVO> getUserShopByManager(List<ShopUserInfoVO> shopUserInfoVOList);
    
    /**
     * 根据 用户id查询他可管理的店铺简单信息
     * @param userId
     * @return
     */
    List<ShopSimpleVO> getShopSimpleInfoByMangerUserId(Long userId);

    /**
     * 根据 用户id查询他可管理的店铺基础信息
     * @param userId
     * @return
     */
    List<ShopBasicVO> getShopBasicInfoByMangerUserId(Long userId);
    
    /**
     * 根据店铺 id 获取店铺简要对象
     * @param shopId
     * @return
     */
    ShopBasicInfoVO getShopBasicInfoByShopId(Long shopId);
    
    /**
     * 根据省市区编号和级别查询店铺
     * @param countyCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ShopBasicVO> getShopByCode(Integer code,Integer level,int pageNum,int pageSize);

    /**
     * 审核更新店铺信息
     * @param ShopId
     * @param status
     * @return
     */
    int auditUpdateShop(Long ShopId ,Byte status) ;
    
    /**
     * 审核新建店铺
     * @param ShopId
     * @param status
     * @return
     */
    Result<Void> auditAddShop(ShopAuditRecordVO vo) ;

    /**
     * 获取平台开放注册商户结算类型列表
     * @return
     */
    List<Integer> getPlatformOpenMerchatRegist();
    
    /**
     * 更新店铺状态
     * @param shopId
     * @param status
     * @return
     */
    int updateShopStatus(Long shopId, Byte status);
    
    /**
     * 根据商户号查询店铺信息
     * @param merchantNo
     * @return
     */
    ShopBasicVO getShopByMerchantNo(String merchantNo);
    
    /**
     * 根据店铺状态、店铺名、业务员手机号查询店铺
     * @param shopQueryParamVO
     * @return
     */
    PageInfo<ShopBasicVO> getShopByShopQuery(ShopQueryParamVO shopQueryParamVO);
    
    /**
     * 添加店铺接口(后台管理员)
     * @param shopVO
     * @return
     */
    int addShopByAdminManager(ShopVO shopVO);
    
    
    /**
     * 员更新店铺接口(后台管理员)
     * @param shopVO
     * @return
     */
    int updateShopByAdminManager(ShopUpdateVO shopUpdateVO);
    
    /**
     * 修改店铺信息(店铺管理员使用)
     * @param shopVO
     * @return
     */
    int updateShopByShopManager(ManagerShopUpdateVO shopVO);
    
    /**
     * 修改店铺营业额（用数据库内营业额值与传入营业额值累加）
     * @param shopId
     * @param totalIncome
     * @return
     */
    BigDecimal updateTotalIncome(Long shopId,BigDecimal totalIncome);
    
    /**
     * 修改店铺销量（用数据库内销量值与传入营业额值累加）
     * @param shopId
     * @param salesVolume
     * @return
     */
    Long updateSalesVolume(Long shopId, Long salesVolume);
    
    
    /**
     * 店铺信息
     * @param shopId 店铺id
     * @return
     */
    ShopInfoVO getShopInfoById(Long shopId);

    /**
     * 根据业务员id查询店铺列表
     * @param salemanId
     * @param pageNum
     * @param pagSize
     * @return
     */
//    PageInfo<ShopBasicVO> getShopBySalemanId(Long salemanId,int pageNum,int pageSize);
  
    /**
     * 根据业务员查询店铺列表
     * @param salemanId
     * @return
     */
    List<ShopBasicInfoVO> getShopBasicInfoBySalemanId(Long salemanId);
    
	/**
	 * 设置店铺是否打烊，以及打烊时间
	 * @param shopId
	 * @param status
	 * @param openTime
	 * @param restTime
	 */
	void setIsOpenShut(Long shopId, Byte status , Date openTime , Date restTime);

	/**
	 * 业务员查询店铺集合
	 * @param userId
	 * @return
	 */
	List<ShopBasicInfoVO> getShopIdsBySalemanUserId(Long userId);
}
