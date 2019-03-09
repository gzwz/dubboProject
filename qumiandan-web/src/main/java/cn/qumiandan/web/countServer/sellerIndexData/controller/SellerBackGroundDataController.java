package cn.qumiandan.web.countServer.sellerIndexData.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.annotation.ValidateShopsManager;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.sellerIndexData.api.ISellerBackGroundDataService;
import cn.qumiandan.sellerIndexData.vo.GetAllDataParamsVO;
import cn.qumiandan.sellerIndexData.vo.GetAllDataVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.countServer.sellerIndexData.entity.request.BackGroundDataParams;
import cn.qumiandan.web.countServer.sellerIndexData.entity.response.BackGroundDataResponseParams;

@RestController
@RequestMapping(value="/backGround")
public class SellerBackGroundDataController {
	@Reference
	private ISellerBackGroundDataService backGroundDataService;
	@ValidateShopsManager
	@RequestMapping(value="/allData")
	public Result<BackGroundDataResponseParams> getData(@Valid @RequestBody(required = false)BackGroundDataParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		//准备查询参数
		GetAllDataParamsVO allDataParamsVO = new GetAllDataParamsVO();
		allDataParamsVO.setShopIds(params.getShopIds());
		allDataParamsVO.setStartDate(params.getStartTime());
		allDataParamsVO.setEndDate(params.getEndTime());
		//查询结果
		GetAllDataVO allData = backGroundDataService.getAllData(allDataParamsVO);
		//组装结果
		BackGroundDataResponseParams res=new BackGroundDataResponseParams();
		res.setActualAmount(allData.getTradeVolume() != null ? allData.getTradeVolume() : new BigDecimal(0));
		res.setConsumeNum(allData.getPersonNum() != null ? allData.getPersonNum() : 0L);
		//保留字段，等前端修改后删除
		res.setDiscountAmount(allData.getWaitingUsing() != null ? allData.getWaitingUsing() : 0);
		res.setWaitingUsing(allData.getWaitingUsing() != null ? allData.getWaitingUsing() : 0);
		res.setPayNum(allData.getOrderNum() != null ? allData.getOrderNum() : 0L);
		res.setProductCost(allData.getAllCost() != null ? allData.getAllCost() : new BigDecimal(0));
		res.setReceivableAmount(allData.getReceivableMoney() != null ? allData.getReceivableMoney() : new BigDecimal(0));
		res.setReturnAmount(allData.getRefundMoney() != null ? allData.getRefundMoney() : new BigDecimal(0));
		res.setServiceFee(allData.getServiceMoney() != null ? allData.getServiceMoney() : new BigDecimal(0));
		res.setTradeAmount(allData.getTradeVolume() != null ? allData.getTradeVolume() : new BigDecimal(0));
		return ResultUtils.success(res);
	}
}
