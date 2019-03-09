package cn.qumiandan.saleman.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.saleman.api.ICheckSaleOpenShopService;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.vo.CheckSaleOpenShopVO;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = ICheckSaleOpenShopService.class)
public class CheckSaleOpenShopServiceImpl implements ICheckSaleOpenShopService {

	@Autowired
	private ISalemanService managerService ;
	@Reference
	private IAddressService addressService; 
	
	@Override
	public Boolean checkSaleOpenShop(CheckSaleOpenShopVO vo) throws QmdException {
		AssertUtil.isNull(vo, "参数不能为空");
		AssertUtil.isNull(vo.getShopAreaCode(), "店铺所在区域为空");
		AssertUtil.isNull(vo.getSalemanId(), "业务员id不能为空");
		
		SalemanVO managerVO = managerService.getSalemanByUserId(vo.getSalemanId());
		log.info("检查开店区域是否符合业务员所在区域 managerVO："+managerVO);
		if (managerVO == null) {
			throw new QmdException("该用户不是业务员");
		}
		//查询店铺所在市
		AddressVO addressVO = addressService.getAddressByCode(Integer.parseInt(vo.getShopAreaCode()), 3);
		if (addressVO == null) {
			throw new QmdException("店铺所在城市查询为空");
		}
		return ObjectUtils.equals(managerVO.getCityCode(),addressVO.getCityCode().toString());
	}

}
