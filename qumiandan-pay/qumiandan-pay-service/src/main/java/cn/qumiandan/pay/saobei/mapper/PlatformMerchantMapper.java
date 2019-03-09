package cn.qumiandan.pay.saobei.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.pay.saobei.entity.PlatformMerchant;
import cn.qumiandan.pay.saobei.vo.PlatformMerchantVO;

/**
 * 平台商户mapper
 * @author yuleidian
 * @version 创建时间：2018年12月18日 下午2:49:10
 */
@Mapper
public interface PlatformMerchantMapper extends BaseMapper<PlatformMerchant>{

	/**
	 * 随机获取一条商户信息
	 * @return
	 */
	PlatformMerchantVO randomGetMerchant();
}
