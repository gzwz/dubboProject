package cn.qumiandan.areacode.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.areacode.entity.AreaCode;
import cn.qumiandan.areacode.vo.AreaCodeVO;
@Mapper
public interface AreaCodeMapper extends BaseMapper<AreaCode>{
    AreaCode selectByPrimaryKey(Long id);
    
    /**
     * 根据地区名获取地区编码
     * @param name
     * @param level
     * @return
     */
    AreaCode getAreaCodeByAddress(@Param("addressVO")AddressVO addressVO, @Param("level")Integer level);
    
    /**
     * 根据地区编码获取地区名
     * @param name
     * @param level
     * @return
     */
    AreaCode getAreaCodeByCode(String code, Integer level);
    
    /**
     * 根据省 和区名 获取扫呗对应信息
     * @param provinceName
     * @param countryName
     * @return
     */
    AreaCodeVO getAreaInfoByProvinceNameAndcountryName(@Param("provinceName") String provinceName, @Param("countryName") String countryName);
}