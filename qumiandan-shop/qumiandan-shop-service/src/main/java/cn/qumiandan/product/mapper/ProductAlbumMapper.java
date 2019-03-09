package cn.qumiandan.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.product.entity.ProductAlbum;
import cn.qumiandan.product.vo.ProductAlbumVO;

@Mapper
public interface ProductAlbumMapper extends BaseMapper<ProductAlbum>{

    /**
     * 根据商品id和类型删除商品照片
     * @param productId
     * @param imageType
     */
    void deleteAlbumsByProductIdAndType(@Param("productId") Long productId,@Param("imageType") Byte imageType);

    /**
     * 根据商品id获取商品图片列表
     * @param productId
     * @return
     */
    List<ProductAlbumVO> selectByProductId(Long productId);
    
    
    
    
    
    
}