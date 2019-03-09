package cn.qumiandan.saobeishopinfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.qumiandan.saobeishopinfo.entity.SaoBeiShopInfo;

@Mapper
public interface SaoBeiShopInfoMapper {
    int deleteByPrimaryKey(Long shopId);

    int insertSelective(SaoBeiShopInfo record);

    SaoBeiShopInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SaoBeiShopInfo record);
    
    SaoBeiShopInfo selectByShopId(Long shopId);
}