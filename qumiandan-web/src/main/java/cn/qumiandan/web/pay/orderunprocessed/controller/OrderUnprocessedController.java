package cn.qumiandan.web.pay.orderunprocessed.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.orderunprocessed.api.IOrderUnprocessedService;
import cn.qumiandan.orderunprocessed.vo.OrderUnprocessedVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.pay.orderunprocessed.entity.QueryOrderUnprocessedParams;
/**
 * 未处理成功订单控制器
 * @author lrj
 *
 */
@RestController
@RequestMapping("/orderUnprocessed/")
public class OrderUnprocessedController {

	@Reference
	private IOrderUnprocessedService orderUnprocessedService ;
	
	/**
	 * 分页查询未处理成功的订单
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/queryOrderUnprocessed")
    public Result<PageInfo<OrderUnprocessedVO>> queryOrderUnprocessed(@Valid @RequestBody(required = false) QueryOrderUnprocessedParams params, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        PageInfo<OrderUnprocessedVO> pageInfo = orderUnprocessedService.queryOrderUnprocessed(params);
        return ResultUtils.success(pageInfo);
    }
}
