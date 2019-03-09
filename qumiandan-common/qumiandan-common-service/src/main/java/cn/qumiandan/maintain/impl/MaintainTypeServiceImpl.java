package cn.qumiandan.maintain.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.maintain.api.IMaintainTypeService;
import cn.qumiandan.maintain.entity.MaintainType;
import cn.qumiandan.maintain.mapper.MaintainTypeMapper;
import cn.qumiandan.maintain.vo.MaintainTypeVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
/**
 * 维护类型实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IMaintainTypeService.class)
public class MaintainTypeServiceImpl implements IMaintainTypeService{

	@Autowired
	private MaintainTypeMapper maintainTypeMapper;
	/**
	 * 查询所有维护类型
	 */
	@Override
	public List<MaintainTypeVO> getAllMaintainType(Long maintainType) {
		QueryWrapper<MaintainType> queryWrapper = new QueryWrapper<>();
		if(maintainType != null) {
			queryWrapper.eq("sys_maintain_type", maintainType);
		}
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		List<MaintainType> maintainTypes = maintainTypeMapper.selectList(queryWrapper);
		if(ObjectUtils.isEmpty(maintainTypes)) {
			return null;
		}
		return CopyBeanUtil.copyList(maintainTypes, MaintainTypeVO.class);
	}
	@Override
	public MaintainTypeVO getMaintainTypeByType(String type) {
		MaintainType maintainType = maintainTypeMapper.selectOne(new QueryWrapper<MaintainType>().eq("type", type).eq("status", StatusEnum.normal.getCode()));
		if(maintainType == null ) {
			return null;
		}
		return CopyBeanUtil.copyBean(maintainType, MaintainTypeVO.class);
	}

}
