package cn.qumiandan.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.system.api.ISysPropertiesService;
import cn.qumiandan.system.entity.SysProperties;
import cn.qumiandan.system.mapper.SysPropertiesMapper;
import cn.qumiandan.system.vo.SysPropertiesVO;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = ISysPropertiesService.class)
public class SysPropertiesServiceImpl implements ISysPropertiesService {

	@Autowired
	private SysPropertiesMapper sysPropertiesMapper;
	
	@Override
	public SysPropertiesVO getSysPropertiesInfoById(Long id) {
		SysProperties result = sysPropertiesMapper.selectById(id);
		SysPropertiesVO vo = null;
		if (result != null) {
			vo = CopyBeanUtil.copyBean(result, SysPropertiesVO.class);
		}
		return vo;
	}
	
}
