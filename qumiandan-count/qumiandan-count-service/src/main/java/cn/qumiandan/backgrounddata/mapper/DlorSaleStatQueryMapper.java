package cn.qumiandan.backgrounddata.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.qumiandan.backgrounddata.vo.DlorSaleStatQueryVO2;

@Mapper
public interface DlorSaleStatQueryMapper {

	/**
	 * 查询代理和业务员利润
	 */
	List<DlorSaleStatQueryVO2> queryDlorSaleStat(@Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
