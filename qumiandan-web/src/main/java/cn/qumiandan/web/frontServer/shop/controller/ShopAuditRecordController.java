package cn.qumiandan.web.frontServer.shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shop.api.IShopAuditRecordService;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.shop.entity.request.ShopIdParams;

/**
 * 店铺管审核controller
 * @author lrj
 * @version 创建时间：2018年11月12日 14:00
 */
@RestController
@RequestMapping("/shopAuditRecord/")
public class ShopAuditRecordController {
	@Reference()
	private IShopAuditRecordService auditRecordService;
	
	/**
	 * 查询店铺审核记录
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopAuditRecordByShopId")
	 public Result<List<ShopAuditRecordVO>> getShopAuditRecordByShopId(@Valid @RequestBody(required = false) ShopIdParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		Result<List<ShopAuditRecordVO>> result = ResultUtils.success();
		List<ShopAuditRecordVO> list = auditRecordService.getShopAuditRecordByShopId(params.getId());
		if(list == null) {
			list = new ArrayList<>();
		}
		result.setData(list);
		return result;
	 }
}
