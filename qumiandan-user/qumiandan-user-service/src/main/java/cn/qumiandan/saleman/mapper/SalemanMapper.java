package cn.qumiandan.saleman.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.saleman.entity.Saleman;
import cn.qumiandan.saleman.vo.SalemanAndUserParamVO;
import cn.qumiandan.saleman.vo.SalemanAndUserVO;
import cn.qumiandan.saleman.vo.SalemanVO;

@Mapper
public interface SalemanMapper extends BaseMapper<Saleman>{

	/**
	 * 总后台查询业务员
	 * @param params
	 * @return
	 */
	List<SalemanAndUserVO> querySalemanAndUser(@Param("params")SalemanAndUserParamVO params);


	/**
	 * 根据code查询代理信息
	 * @param proCode
	 * @param cityCode
	 * @param countryCode
	 * @param userId
	 * @return
	 */
	List<SalemanVO> getAgentAndSalemanByCode(@Param("proCode")String proCode, @Param("cityCode")String cityCode,@Param("countryCode")String countryCode,@Param("userId")Long userId);
}