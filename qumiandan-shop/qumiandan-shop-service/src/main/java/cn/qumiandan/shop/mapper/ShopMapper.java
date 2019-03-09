package cn.qumiandan.shop.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.shop.entity.Shop;
import cn.qumiandan.shop.vo.ShopBasicInfoVO;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.shop.vo.ShopDynamicVO;
import cn.qumiandan.shop.vo.ShopInfoVO;
import cn.qumiandan.shop.vo.ShopQueryParamVO;
import cn.qumiandan.shop.vo.ShopUserInfoVO;

@Mapper
public interface ShopMapper extends BaseMapper<Shop> {

    /**
     * 根据类型查询店铺信息
     * @return List（店铺信息列表）
     */
//    List<ShopVO> getIndexShopListByType(Long type);
     
    /**
     * 不带条件查询店铺信息
     * @return List（店铺信息列表）
     */
//    List<ShopVO> getIndexShopList();
    
    /**
     * 模糊查询店铺信息
     * @param inputInfo
     * @return List<ShopVO> （店铺信息列表）
     */
//    List<ShopVO> getShopListLike(String inputInfo);
    
    /**
     * 动态查询店铺
     * @param shopDynamicVO
     * @return List<ShopVO> （店铺信息列表）
     */
    List<ShopBasicVO> getShopDynamic(ShopDynamicVO shopDynamicVO);

    /**
     * 保存店铺-行业关联信息
     * @param id 店铺id
     * @param industryId
     * @return
     */
    int addShopIndustry(Long id,Long industryId);

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
    List<ShopBasicVO> getUserShopByManager(@Param("shopUserInfoVOList") List<ShopUserInfoVO> shopUserInfoVOList);

    /**
     * 根据店铺id查询店铺信息
     * @param shopId
     * @return
     */
    ShopBasicVO getShopBasicById(Long shopId);

    /**
     * 根据店铺 id 获取店铺简要对象
     * @param shopId
     * @return
     */
    ShopBasicInfoVO getShopBasicInfoByShopId(Long shopId);
    
    /**
     * 根据地区编号查询店铺
     * @param countyCode
     * @return
     */
    List<ShopBasicVO> getShopByCodeAndLevel(@Param("code")Integer code,@Param("level")Integer level);
    
    /**
     * 查询店铺状态
     * @param id
     * @return
     */
    Byte getShopStatus(Long id);
    
    /**
     * 更新店铺状态
     * @param shopId
     * @param status
     * @return
     */
    Integer updateShopStatus(@Param("shopId") Long shopId, @Param("status") Byte status);
    
    /**
     * 根据商户号查询店铺信息
     * @param merchantNo
     * @return
     */
    ShopBasicVO getShopByMerchantNo(String merchantNo);
    
    /**
     * 根据店铺状态、店铺名、业务员手机号查询店铺
     * @param paramVO
     * @return
     */
    List<ShopBasicVO> getShopByShopQuery(@Param("shopQueryParamVO") ShopQueryParamVO shopQueryParamVO);
    
    /**
     * 根据店铺id获取店铺展示信息
     * @param shopId
     * @return
     */
    ShopInfoVO getShopInfoById(@Param("shopId")Long shopId);
    
	/**
	 * 根据业务员id和店铺状态查询店铺数量
	 * @param salemanId
	 * @param status
	 * @param startCreateDate
	 * @param endCreateDate
	 * @return
	 */
	Integer getShopNumBySalemanId(@Param("salemanId")Long salemanId, @Param("status")Byte status,@Param("startCreateDate") Date startCreateDate ,@Param("endCreateDate") Date endCreateDate);
}