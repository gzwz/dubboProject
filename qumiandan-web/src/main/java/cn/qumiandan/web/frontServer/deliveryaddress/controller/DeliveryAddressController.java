package cn.qumiandan.web.frontServer.deliveryaddress.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.deliveryaddress.api.IDeliveryAddressService;
import cn.qumiandan.deliveryaddress.vo.DeliveryAddressVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.frontServer.deliveryaddress.entity.request.AddDeliveryAddressParams;
import cn.qumiandan.web.frontServer.deliveryaddress.entity.request.GetIdParams;
import cn.qumiandan.web.frontServer.deliveryaddress.entity.request.UpdateDeliveryAddressParams;

/**
 * 收货地址管理
 * @author lrj
 *	创建时间：2018-11-29 14:30
 */

@RestController
@RequestMapping("/deliveryAddress/")
public class DeliveryAddressController {
	@Reference()
	private IDeliveryAddressService deliveryAddressService;
	
	/**
	 * 添加收货地址
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addDeliveryAddress")
	public Result<Integer> addDeliveryAddress(@Valid @RequestBody(required = false) AddDeliveryAddressParams params,  BindingResult bindingResult)   {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		DeliveryAddressVO deliveryAddressVO =
		CopyBeanUtil.copyBean(params, DeliveryAddressVO.class);
		deliveryAddressVO.setUserId(ShiroUtils.getUserId());
		deliveryAddressService.addDeliveryAddress(deliveryAddressVO);
		return ResultUtils.success("添加收货地址成功");
	}
	
	/**
	 * 修改收获地址
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateDeliveryAddress")
	public Result<Integer> updateDeliveryAddress(@Valid @RequestBody(required = false) UpdateDeliveryAddressParams params,  BindingResult bindingResult)   {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		DeliveryAddressVO deliveryAddressVO  =
		CopyBeanUtil.copyBean(params, DeliveryAddressVO.class);
		deliveryAddressVO.setUserId(ShiroUtils.getUserId());
		deliveryAddressService.updateDeliveryAddress(deliveryAddressVO);
		return ResultUtils.success("更新收货地址成功");
	}
	
	/**
	 * 删除收货地址
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("deleteDeliveryAddress")
	public Result<Integer> deleteDeliveryAddress(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult)   {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		deliveryAddressService.deleteDeliveryAddress(params.getId());
		return ResultUtils.success("删除收货地址成功");
	}
	
	/**
	 * 根据id查询收获地址详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getDeliveryAddressById")
	public Result<DeliveryAddressVO> getDeliveryAddressById(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult)   {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		DeliveryAddressVO deliveryAddressVO = deliveryAddressService.getDeliveryAddressById(params.getId());
		Result<DeliveryAddressVO> result = ResultUtils.success();
		result.setData(deliveryAddressVO);
		return result;
	}
	

	/**
	 * 根据用户id查询收货地址
	 * @return
	 */
	@RequestMapping("getDeliveryAddressByUserId")
	public Result<List<DeliveryAddressVO>> getDeliveryAddressByUserId(){
		List<DeliveryAddressVO> list = deliveryAddressService.getDeliveryAddressByUserId(ShiroUtils.getUserId());
		Result<List<DeliveryAddressVO>> result = ResultUtils.success();
		result.setData(list);
		return result;
	} 
}
