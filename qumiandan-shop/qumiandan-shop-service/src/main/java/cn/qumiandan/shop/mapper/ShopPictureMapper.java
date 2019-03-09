package cn.qumiandan.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.shop.entity.ShopPicture;
import cn.qumiandan.shop.vo.ShopPictureVO;

@Mapper
public interface ShopPictureMapper extends BaseMapper<ShopPicture> {

    /**
     * 根据店铺id和类型删除店铺i照片
     * @param shopId
     * @param imageType
     * @return
     */
    void deleteAlbumsByShopIdAndType(@Param("shopId") Long shopId, @Param("imageType") Byte imageType);

    /**
     * 根据店铺id获取店铺图片列表
     * @param shopId
     * @return
     */
    List<ShopPicture> selectByShopId(Long shopId);
    
    /**
	 * 根据店铺id和类型查询 店铺相关图片信息
	 * @param shopId
	 * @param types
	 * @return
	 */
    List<ShopPictureVO> getShopPictureByShopIdAndTypes(@Param("shopId") Long shopId, @Param("types") List<Byte> types);
    
    
    
    
    
    
}