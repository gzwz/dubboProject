package cn.qumiandan.web.frontServer.order.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.annotation.ValidateShopsManager;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.vo.CreateOrderResponseParamsVO;
import cn.qumiandan.order.vo.OrderAddVO;
import cn.qumiandan.order.vo.OrderQueryParamsVO;
import cn.qumiandan.order.vo.OrderQueryVO;
import cn.qumiandan.order.vo.OrderStatusListVO;
import cn.qumiandan.order.vo.OrderUpdateStatusVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.frontServer.order.entity.request.BackstageQueryOrderParams;
import cn.qumiandan.web.frontServer.order.entity.request.GetOrderByShopIdParams;
import cn.qumiandan.web.frontServer.order.entity.request.GetOrderByShopIds;
import cn.qumiandan.web.frontServer.order.entity.request.GetOrderByUserIdParams;
import cn.qumiandan.web.frontServer.order.entity.request.GetOrderByValTicketIdParams;
import cn.qumiandan.web.frontServer.order.entity.request.OrderAddParams;
import cn.qumiandan.web.frontServer.order.entity.request.OrderDetailParams;
import cn.qumiandan.web.frontServer.order.entity.request.OrderUpdateParams;
import cn.qumiandan.web.frontServer.order.entity.response.OrderDetailResponseParams;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 订单控制器
 * @author: zhuayngyong
 * @date: 2018/12/5 9:26
 */
@Slf4j
@RestController
@RequestMapping("/order/")
public class OrderController {

	@Reference()
	private IOrderService orderService;

	@Reference()
	private IShopService shopService;

	@Reference()
	private IUserService userService;

	/**
	 * 创建订单
	 * 
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addOrder")
	public Result<CreateOrderResponseParamsVO> addOrder(@Valid @RequestBody(required = false) OrderAddParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		/*
		 * CopyBeanUtil.copyBean(params, orderAddVO); List<OrderProductVO>
		 * orderProductList = new ArrayList<OrderProductVO>(); if (null !=
		 * orderAddVO.getOrderProductList()) {
		 * CopyBeanUtil.copyList(orderAddVO.getOrderProductList(), orderProductList,
		 * OrderProductVO.class); }
		 */
		params.setUserId(ShiroUtils.getUserId());
		OrderAddVO copyBean = CopyBeanUtil.copyBean(params, OrderAddVO.class);
		System.out.println(copyBean);
		CreateOrderResponseParamsVO createOrderResponseParamsVO = orderService.createOrder(copyBean);
		log.info("orderAddVO:" + copyBean);
		Result<CreateOrderResponseParamsVO> result = ResultUtils.success("创建订单成功");
		result.setData(createOrderResponseParamsVO);;
		return result;
	}

	/**
	 * 根据订单编号修改订单状态
	 * 
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateOrderStatusById")
	public Result<OrderVO> updateOrderStatusById(@Valid @RequestBody(required = false) OrderUpdateParams params,
			BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderUpdateStatusVO orderUpdateStatusVO = CopyBeanUtil.copyBean(params, OrderUpdateStatusVO.class);
		OrderVO orderVO = orderService.updateOrderStatusById(orderUpdateStatusVO);
		Result<OrderVO> result = ResultUtils.success(orderVO);
		return result;
	}
	/**
	 * 根据订单编号查询订单详情
	 * 
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getOrderDetailById")
	public Result<OrderDetailResponseParams> getOrderDetailById(@Valid @RequestBody(required = false) OrderDetailParams params,
			BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderQueryVO orderQueryVO = orderService.getOrderDetailById(params.getOrderId());
		Result<OrderDetailResponseParams> result = ResultUtils.success();
		if(orderQueryVO != null) {
			OrderDetailResponseParams detailParams = CopyBeanUtil.copyBean(orderQueryVO, OrderDetailResponseParams.class);
			ShopBasicVO shopInfo = shopService.getShopBasicById(orderQueryVO.getShopId());
			UserVO userInfo = userService.getUserById(orderQueryVO.getUserId());
			if(shopInfo != null) {
				detailParams.setLogo(shopInfo.getLogo());
				detailParams.setLongitude(shopInfo.getLongitude());
				detailParams.setLatitude(shopInfo.getLatitude());
				detailParams.setPhone(shopInfo.getPhone());
				detailParams.setAddress(shopInfo.getAddress());
				detailParams.setGameSwitch(shopInfo.getGameSwitch());
			}
			if(userInfo != null) {
				detailParams.setUserName(userInfo.getUserName());
			}
			result.setData(detailParams);
		}
		return result;

	}

	/**
	 * 根据用户id和订单状态查询订单信息
	 * 
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getOrderByUserId")
	public Result<PageInfo<OrderQueryVO>> getOrderByUserId(
			@Valid @RequestBody(required = false) GetOrderByUserIdParams params, BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderStatusListVO orderStatusListVO = CopyBeanUtil.copyBean(params, OrderStatusListVO.class);
		orderStatusListVO.setUserId(ShiroUtils.getUserId());
		PageInfo<OrderQueryVO> pageInfo = orderService.getOrderByorderStatusList(orderStatusListVO,
				params.getPageNum(), params.getPageSize());
		Result<PageInfo<OrderQueryVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;

	}

	/**
	 * 根据用户id和订单状态查询订单信息
	 * 
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getOrderByShopId")
	public Result<PageInfo<OrderQueryVO>> getOrderByShopId(
			@Valid @RequestBody(required = false) GetOrderByShopIdParams params, BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderStatusListVO orderStatusListVO = CopyBeanUtil.copyBean(params, OrderStatusListVO.class);
		orderStatusListVO.setUserId(ShiroUtils.getUserId());
		PageInfo<OrderQueryVO> pageInfo = orderService.getOrderByorderStatusList(orderStatusListVO,
				params.getPageNum(), params.getPageSize());
		Result<PageInfo<OrderQueryVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;

	}

	/**
	 * 根据店铺id集合查询店铺订单参数
	 * 
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@ValidateShopsManager
	@RequestMapping("getOrderByShopIdList")
	public Result<PageInfo<OrderQueryVO>> getOrderByShopIdList(
			@Valid @RequestBody(required = false) GetOrderByShopIds params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		//拦截shopIds,参数名不一样，手动set
		OrderQueryParamsVO shopIdListVO = CopyBeanUtil.copyBean(params, OrderQueryParamsVO.class);
		shopIdListVO.setShopIdList(params.getShopIds());
		PageInfo<OrderQueryVO> pageInfo = orderService.getOrderByShopIdList(shopIdListVO, params.getPageNum(),
				params.getPageSize());
		Result<PageInfo<OrderQueryVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;

	}
	
	/**
	 * 根据核销券查询订单
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getOrderByValTicketCode")
	public Result<OrderQueryVO> getOrderByValTicketCode(
			@Valid @RequestBody(required = false) GetOrderByValTicketIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderQueryVO orderVO = orderService.getOrderByValTicketCode(params.getTicketCode());
		Result<OrderQueryVO> result = ResultUtils.success();
		result.setData(orderVO);
		return result;

	}
	
	
	/**
	 * 总后台查询订单
	 * 
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("backstageQueryOrder")
	public Result<PageInfo<OrderQueryVO>> backstageQueryOrder(
			@Valid @RequestBody(required = false) BackstageQueryOrderParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderQueryParamsVO shopIdListVO = CopyBeanUtil.copyBean(params, OrderQueryParamsVO.class);
		PageInfo<OrderQueryVO> pageInfo = orderService.getOrderByShopIdList(shopIdListVO, params.getPageNum(),
				params.getPageSize());
		Result<PageInfo<OrderQueryVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;

	}
	
	/**
	 * 取消支付
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("cancelPay")
	public Result<Void> cancelPay(
			@Valid @RequestBody(required = false) OrderDetailParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		orderService.cancelPay(params.getOrderId(), ShiroUtils.getUserId());
		return ResultUtils.success("取消支付成功");

	}
}
