package cn.qumiandan.web.salemanServer.shop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shop.api.IDyShopService;
import cn.qumiandan.shop.vo.DLQueryShopVO;
import cn.qumiandan.shop.vo.ShopVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.salemanServer.shop.entity.request.DLQueryShopParams;
/**
 * 业务员，代理端查询店铺 
 * @author W
 * 2019年1月22日
 */
@RestController
@RequestMapping("/smShop/")
public class SaleManShopController {

	@Reference()
	private IDyShopService dyShopService;

	/**
	 * 查询当前代理管辖下的所有店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	 @RequestMapping("getShopByDyId")
	public Result<PageInfo<ShopVO>> getShopByDyId(@Valid @RequestBody(required = false) 
	 DLQueryShopParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		Long userId = ShiroUtils.getUserId();
		if (null == params) {
			throw new QmdException("参数不能为空");
		}
		DLQueryShopVO vo = CopyBeanUtil.copyBean(params, DLQueryShopVO.class);
		vo.setDyUserId(userId);
		PageInfo<ShopVO> shopList = dyShopService.getShopPageByDyId(vo);
		Result<PageInfo<ShopVO>> result = ResultUtils.success();
		result.setData(shopList);
		return result;
	 }
	 
		/**
		 * 查询当前代理管辖下的所有店铺
		 * @param params
		 * @param bindingResult
		 * @return
		 */
		 @RequestMapping("getShopListByDlId")
		public Result<List<ShopVO>> getShopListByDyId(@Valid @RequestBody(required = false) 
		 DLQueryShopParams params, BindingResult bindingResult){
			if (bindingResult.hasErrors()) {
				return ResultUtils.paramsError(bindingResult);
			}
			Long userId = ShiroUtils.getUserId();
			if (null == params) {
				throw new QmdException("参数不能为空");
			}
			List<ShopVO> shopList = dyShopService.getShopListByDyId(userId);
			Result<List<ShopVO>> result = ResultUtils.success();
			result.setData(shopList);
			return result;
		 }
}
