package cn.qumiandan.pay.saobei.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.pay.saobei.entity.PlatformMerchant;
import cn.qumiandan.pay.saobei.mapper.PlatformMerchantMapper;
import cn.qumiandan.pay.saobei.service.IPlatformMerchantService;
import cn.qumiandan.pay.saobei.vo.PlatformMerchantVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = IPlatformMerchantService.class)
public class PlatformMerchantServiceImpl implements IPlatformMerchantService {

	@Autowired
	private PlatformMerchantMapper platformMerchantMapper;
	
	@Override
	public PlatformMerchantVO randomGetMerchant() {
		return platformMerchantMapper.randomGetMerchant();
	}

	@Override
	public PlatformMerchantVO getPlatformMerchantById(Long id) {
		AssertUtil.isNull(id, "PlatformMerchantServiceImpl|getPlatformMerchantById|传入参数id为空");
		PlatformMerchant merchant = platformMerchantMapper.selectById(id);
		if (Objects.nonNull(merchant)) {
			return CopyBeanUtil.copyBean(merchant, PlatformMerchantVO.class);
		}
		return null;
	}

	@Override
	public PlatformMerchantVO getPlatformMerchantByMerchantNo(String merchantNo) {
		AssertUtil.isNull(merchantNo, "PlatformMerchantServiceImpl|getPlatformMerchantByMerchantNo|传入参数merchantNo为空");
		PlatformMerchant merchant = platformMerchantMapper.selectOne(new QueryWrapper<PlatformMerchant>().eq("merchant_no", merchantNo));
		if (Objects.nonNull(merchant)) {
			return CopyBeanUtil.copyBean(merchant, PlatformMerchantVO.class);
		}
		return null;
	}
}
