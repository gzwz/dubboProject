package cn.qumiandan.shop.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.shop.entity.ShopAuditRecord;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 平台店铺审核记录
 * @author yuleidian
 * @version 创建时间：2018年12月10日 下午2:27:21
 */
@Mapper
public interface ShopAuditRecordMapper extends BaseMapper<ShopAuditRecord> {

	
	/**
	 * 根据shopId获取最新的审核记录
	 * @param shopId
	 * @return
	 */
	ShopAuditRecordVO getNewestAuditRecord(@Param("shopId") Long shopId);
}
