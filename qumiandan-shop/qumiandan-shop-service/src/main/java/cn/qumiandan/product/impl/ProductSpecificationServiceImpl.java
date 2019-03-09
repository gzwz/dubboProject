package cn.qumiandan.product.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.product.api.IProductSpecificationService;
import cn.qumiandan.product.entity.ProductSpecification;
import cn.qumiandan.product.mapper.ProductSpecificationMapper;
import cn.qumiandan.product.vo.SpecificationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IProductSpecificationService.class)
public class ProductSpecificationServiceImpl implements IProductSpecificationService {

	@Autowired
	private ProductSpecificationMapper mapper;
	
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public void addSpecification(SpecificationVO param) throws QmdException {
		List<SpecificationVO> list = querySpecification(param);
		if (list != null && list.size() > 0) {
			throw new QmdException("该分类分类已经存在，请勿重复添加");
		}
		ProductSpecification entity = new ProductSpecification();
		//BeanUtils.copyProperties(param, entity);
		int i = mapper.insert(entity);
		if (i < 1) {
			log.error("插入失败");
		}
	}

	@Override
	public List<SpecificationVO> querySpecification(SpecificationVO param) {
		Map<String, Object> columnMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(param.getName())) {
			columnMap.put("name", param.getName());
		}
		if (param.getProductId() != null) {
			columnMap.put("product_id", param.getProductId());
		}
		List<ProductSpecification> list = mapper.selectByMap(columnMap );
		List<SpecificationVO> targetList = new ArrayList<>();
		for (ProductSpecification productSpecification : list) {
			SpecificationVO target = new SpecificationVO();
			BeanUtils.copyProperties(productSpecification, target);
			targetList.add(target);
		}
		return targetList;
	}

	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public void deleteSpecification(String id) {
		mapper.deleteById(id);
	}

}
