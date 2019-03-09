package cn.qumiandan.complain.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.complain.api.IComplainTypeService;
import cn.qumiandan.complain.entity.ComplainType;
import cn.qumiandan.complain.mapper.ComplainTypeMapper;
import cn.qumiandan.complain.vo.ComplainTypeVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
@Component
@Service(interfaceClass = IComplainTypeService.class)
public class ComplainTypeServiceImpl implements IComplainTypeService{
	@Autowired
	private ComplainTypeMapper complainTypeMapper;
	
	/**
	 * 查询所有的投诉类型
	 */
	@Override
	public List<ComplainTypeVO> getAllComplain() {
		List<ComplainType> complainTypes = complainTypeMapper.selectList(new QueryWrapper<ComplainType>());
		if(ObjectUtils.isEmpty(complainTypes)) {
			return null;
		}
		return CopyBeanUtil.copyList(complainTypes, ComplainTypeVO.class);
	}
	
	
}
