package cn.qumiandan.web.countServer.sellerIndexData.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.sellerIndexData.api.ISellerIndexDataService;
import cn.qumiandan.sellerIndexData.vo.SellerIndexDataVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.countServer.sellerIndexData.entity.request.SellerIndexDataParams;

@RestController
@RequestMapping(value="/sellerIndexData")
public class SellerIndexDataController {
	@Reference
	private ISellerIndexDataService sellerIndexDataService;
	/**
	 * 商家端首页数据展示
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/indexData")
	public Result<SellerIndexDataVO> getIndexData(@Valid @RequestBody(required = false)SellerIndexDataParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		SellerIndexDataVO sellerIndexData = sellerIndexDataService.getSellerIndexData(ShiroUtils.getUserId(), params.getTimeStatus());
		return ResultUtils.success(sellerIndexData);
	}
}
