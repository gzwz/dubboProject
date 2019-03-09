package cn.qumiandan.sellerIndexData.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.sellerIndexData.api.ISellerBackGroundDataService;
import cn.qumiandan.sellerIndexData.mapper.SellerBackGroundDataMapper;
import cn.qumiandan.sellerIndexData.vo.GetAllDataParamsVO;
import cn.qumiandan.sellerIndexData.vo.GetAllDataVO;
import cn.qumiandan.utils.ObjectUtils;
@Component
@Service(interfaceClass=ISellerBackGroundDataService.class)
public class SellerBackGroundServiceImpl implements ISellerBackGroundDataService{
	@Autowired
	private SellerBackGroundDataMapper backGroundDataMapper;

	@Override	
	public GetAllDataVO getAllData(GetAllDataParamsVO allDataParamsVO) {
		if(ObjectUtils.isEmpty(allDataParamsVO.getShopIds())) {
			return new GetAllDataVO();
		}
		List<Long> accountIds = backGroundDataMapper.getAccountIdsByShopIds(allDataParamsVO.getShopIds());
		allDataParamsVO.setAccountIds(accountIds);
		GetAllDataVO allData = backGroundDataMapper.getAllData(allDataParamsVO);
		return allData;
	}
	
}
