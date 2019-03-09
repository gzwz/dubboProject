package cn.qumiandan.web.commonServer.address.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.vo.AddressCodeAndNameVO;
import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.commonServer.address.entity.request.AddressOpenOrCloseServiceParams;
import cn.qumiandan.web.commonServer.address.entity.request.GetAddressByCodeParams;
import cn.qumiandan.web.commonServer.address.entity.request.GetCodeParams;

/**
 * 行业管理
 * @author lrj
 * @version 创建时间：2018年11月20日 17:30
 */
@RestController
@RequestMapping("/address/")
public class AddressController {
	
	@Reference()
	private IAddressService addressService;
//	getProvince
	
	/**
	 * 查询所有省份
	 * @return
	 */
	@RequestMapping("getProvince")
	public Result<List<AddressCodeAndNameVO>> getProvince() {
		List<AddressCodeAndNameVO> addressVOs = addressService.getProvince();
		Result<List<AddressCodeAndNameVO>> result = ResultUtils.success();
		result.setData(addressVOs);
		return result;
	}
	
	
	/**
	 * 根据省份code获取城市信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getCityByProvinceCode")
	public Result<List<AddressCodeAndNameVO>> getCityByProvinceCode(@Valid @RequestBody(required = false)GetCodeParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<AddressCodeAndNameVO> addressVOs = addressService.getCityByProvinceCode(params.getCode());
		Result<List<AddressCodeAndNameVO>> result = ResultUtils.success();
		result.setData(addressVOs);
		return result;
	}
	
	/**
	 * 根据城市code获取区域信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getDistrictByCityCode")
	public Result<List<AddressCodeAndNameVO>> getDistrictByCityCode(@Valid @RequestBody(required = false)GetCodeParams params,BindingResult bindingResult) {
		AssertUtil.isNull(params, "json格式参数不能为空");
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<AddressCodeAndNameVO> addressVOs = addressService.getDistrictByCityCode(params.getCode());
		Result<List<AddressCodeAndNameVO>> result = ResultUtils.success();
		result.setData(addressVOs);
		return result;
	}
	
	/**
	 * 根据城区code获取城镇、街道信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getTownByDistrictCode")
	public Result<List<AddressCodeAndNameVO>> getTownByDistrictCode(@Valid @RequestBody(required = false)GetCodeParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<AddressCodeAndNameVO> addressVOs = addressService.getTownByDistrictCode(params.getCode());
		Result<List<AddressCodeAndNameVO>> result = ResultUtils.success();
		result.setData(addressVOs);
		return result;
	}
	
	/**
	 * 根据城市code查询该城市是否开通
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getCurrentCityIsOpen")
	public Result<Boolean> getCurrentCityIsOpen(@Valid @RequestBody(required = false)GetCodeParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		boolean flag= addressService.getCurrentCityIsOpen(params.getCode());
		Result<Boolean> result = null;
		if(flag) {
			result = ResultUtils.success("当前城市已开通服务");
		}else {
			result = ResultUtils.success("当前城市未开通服务");
		}
		result.setData(flag);
		return result;
	}
	
	/**
	 * 查询开通城市
	 * @return
	 */
	@RequestMapping("getOpenCity")
	public Result<List<AddressCodeAndNameVO>> getOpenCity() { 
		List<AddressCodeAndNameVO> addressVOs = addressService.getOpenCity();
		Result<List<AddressCodeAndNameVO>> result = ResultUtils.success();
		result.setData(addressVOs);
		return result;
	}
	
	/**
	 * 根据省、市、区/县、乡镇code和对应级别查询地址详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getAddressByCode")
	public Result<AddressVO> getAddressByCode(@Valid @RequestBody(required = false)GetAddressByCodeParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AddressVO addressVO = addressService.getAddressByCode(Integer.parseInt(params.getCode()), params.getLevel());
		Result<AddressVO> result = ResultUtils.success();
		result.setData(addressVO);
		return result;
	}
	/**
	 * 设置地址开启、关闭服务
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addressOpenOrCloseService")
	public Result<Boolean> addressOpenOrCloseService(@Valid @RequestBody(required = false)AddressOpenOrCloseServiceParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		Boolean flag= addressService.addressOpenOrCloseService(Integer.parseInt(params.getCode()), params.getLevel(),params.getIsOpen());
		Result<Boolean> result = ResultUtils.success("设置成功");
		result.setData(flag);
		return result;
	}
}
