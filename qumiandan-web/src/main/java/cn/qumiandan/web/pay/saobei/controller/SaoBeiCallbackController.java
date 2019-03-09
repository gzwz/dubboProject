package cn.qumiandan.web.pay.saobei.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.ParentDataEnum;
import cn.qumiandan.orderunprocessed.api.IOrderUnprocessedService;
import cn.qumiandan.orderunprocessed.enums.OrderUnprocessedStatusEnum;
import cn.qumiandan.orderunprocessed.enums.OrderUnprocessedTypeEnum;
import cn.qumiandan.orderunprocessed.vo.OrderUnprocessedVO;
import cn.qumiandan.pay.saobei.enums.SaoBeiResult;
import cn.qumiandan.pay.saobei.service.ISaobeiCallbackService;
import cn.qumiandan.pay.saobei.vo.AttachHelperVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.callback.CreateMerchatCallbackVO;
import cn.qumiandan.pay.saobei.vo.response.pay.callback.JSPayCallbackVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.GsonHelper;
import cn.qumiandan.web.pay.saobei.entity.reqeust.CreateMerchatCallbackParams;
import cn.qumiandan.web.pay.saobei.entity.reqeust.JSPayCallbackParams;
import cn.qumiandan.web.pay.saobei.entity.response.CreateMerchatCallbackResult;
import cn.qumiandan.web.pay.saobei.entity.response.JSPayCallbackResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 扫呗回调controller
 * @author yuleidian
 * @version 创建时间：2018年12月10日 上午10:25:50
 */
@Slf4j
@RestController
@RequestMapping("/saoBeiCallbackController/")
public class SaoBeiCallbackController {

	@Reference
	private ISaobeiCallbackService saobeiCallbackService;
	
	@Reference
	private ITradeDetailService tradeDetailService;
	
	@Reference
	private IOrderUnprocessedService orderUnprocessedService;
	
	/**
	 * 扫呗创建商户回调
	 */
	@RequestMapping("createMerchatCallback")
	public CreateMerchatCallbackResult createMerchatCallback(@RequestBody(required = false) CreateMerchatCallbackParams params, BindingResult bindingResult) {
		if (Objects.isNull(params)) {
			CreateMerchatCallbackResult result = new CreateMerchatCallbackResult();
			result.setReturnCode(SaoBeiResult.RESULT_FAIL.getCode());
			result.setReturnMsg("请求参数不能为空");
			return result;
		}
		
		CreateMerchatCallbackVO vo = CopyBeanUtil.copyBean(params, CreateMerchatCallbackVO.class);
		try {
			saobeiCallbackService.createMerchantCallback(vo);
		} catch (Exception e) {
			CreateMerchatCallbackResult result = new CreateMerchatCallbackResult();
			result.setReturnCode(SaoBeiResult.RESULT_FAIL.getCode());
			result.setReturnMsg("系统内部错误");
			return result;
		}
		CreateMerchatCallbackResult result = new CreateMerchatCallbackResult();
		result.setReturnCode(SaoBeiResult.RESULT_SUCCESS.getCode());
		result.setReturnMsg("成功");
		result.setTraceNo(params.getTraceNo());
		return result;
	}
	
	/**
	 * 扫呗公众号支付回调普通订单处理
	 */
	@RequestMapping("jsPayCallback")
	public JSPayCallbackResult jsPayCallback(@RequestBody(required = false) JSPayCallbackParams params, BindingResult bindingResult) {
		log.info("扫呗回调我们支付接口:" + params);
		if (Objects.isNull(params)) {
			return JSPayCallbackResult.createFail("请求参数为空");
		}
		
		if (bindingResult.hasErrors()) {
			return JSPayCallbackResult.createFail(bindingResult.getFieldError().getDefaultMessage());
		}
		
		JSPayCallbackVO vo = CopyBeanUtil.copyBean(params, JSPayCallbackVO.class);
		try {
			saobeiCallbackService.jsPayCallback(vo);
		} catch (QmdException e) {
			if (PayErrorCode.PAY1003.getCode().equals(e.getCode())) {
				AttachHelperVO atach= GsonHelper.fromJson(params.getAttach(), AttachHelperVO.class);
				TradeDetailVO trade = tradeDetailService.getTradeDetailById(atach.getTradeId());
				if (Objects.nonNull(trade)) {
					OrderUnprocessedVO unprocessed = new OrderUnprocessedVO();
					unprocessed.setOrderId(trade.getSerialNo());
					unprocessed.setGameOrderId(trade.getGameSerialNo());
					unprocessed.setTradeId(trade.getId());
					unprocessed.setType(OrderUnprocessedTypeEnum.CALLBACKMONEY_EXCEPTION.getCode());
					unprocessed.setStatus(OrderUnprocessedStatusEnum.PROCESSING.getCode());
					unprocessed.setCreateDate(new Date());
					unprocessed.setCreateId(ParentDataEnum.RootId.getCode());
					orderUnprocessedService.addOrderUnprocessed(unprocessed);
					
					trade.setCallbackAmount(new BigDecimal(params.getTotalFee()));
					tradeDetailService.updateTradeDetail(trade);
				}
			}
			JSPayCallbackResult result = new JSPayCallbackResult();
			result.setReturnCode(SaoBeiResult.RESULT_FAIL.getCode());
			result.setReturnMsg("系统内部错误");
			return result;
		} catch (Exception e) {
			JSPayCallbackResult result = new JSPayCallbackResult();
			result.setReturnCode(SaoBeiResult.RESULT_FAIL.getCode());
			result.setReturnMsg("系统内部错误");
			return result;
		}
		
		JSPayCallbackResult result = new JSPayCallbackResult();
		result.setReturnCode(SaoBeiResult.RESULT_SUCCESS.getCode());
		result.setReturnMsg("成功");
		return result;
	}
	
	/**
	 * 扫呗公众号支付回调游戏订单处理
	 */
	@RequestMapping("jsPayGameCallback")
	public JSPayCallbackResult jsPayGameCallback(@RequestBody(required = false) JSPayCallbackParams params, BindingResult bindingResult) {
		log.info("扫呗游戏回调我们支付接口:" + params.toString());
		if (Objects.isNull(params)) {
			return JSPayCallbackResult.createFail("请求参数为空");
		}
		
		if (bindingResult.hasErrors()) {
			return JSPayCallbackResult.createFail(bindingResult.getFieldError().getDefaultMessage());
		}
		
		JSPayCallbackVO vo = CopyBeanUtil.copyBean(params, JSPayCallbackVO.class);
		
		try {
			saobeiCallbackService.jsPayCallbackGame(vo);
		} catch (QmdException e) {
			if (PayErrorCode.PAY1003.getCode().equals(e.getCode())) {
				AttachHelperVO atach= GsonHelper.fromJson(params.getAttach(), AttachHelperVO.class);
				TradeDetailVO trade = tradeDetailService.getTradeDetailById(atach.getTradeId());
				if (Objects.nonNull(trade)) {
					OrderUnprocessedVO unprocessed = new OrderUnprocessedVO();
					unprocessed.setOrderId(trade.getSerialNo());
					unprocessed.setGameOrderId(trade.getGameSerialNo());
					unprocessed.setTradeId(trade.getId());
					unprocessed.setType(OrderUnprocessedTypeEnum.CALLBACKMONEY_EXCEPTION.getCode());
					unprocessed.setStatus(OrderUnprocessedStatusEnum.PROCESSING.getCode());
					unprocessed.setCreateDate(new Date());
					unprocessed.setCreateId(ParentDataEnum.RootId.getCode());
					orderUnprocessedService.addOrderUnprocessed(unprocessed);
					
					trade.setCallbackAmount(new BigDecimal(params.getTotalFee()));
					tradeDetailService.updateTradeDetail(trade);
				}
			}
			JSPayCallbackResult result = new JSPayCallbackResult();
			result.setReturnCode(SaoBeiResult.RESULT_FAIL.getCode());
			result.setReturnMsg("系统内部错误");
			return result;
		} catch (Exception e) {
			JSPayCallbackResult result = new JSPayCallbackResult();
			result.setReturnCode(SaoBeiResult.RESULT_FAIL.getCode());
			result.setReturnMsg("系统内部错误");
			return result;
		}
		
		JSPayCallbackResult result = new JSPayCallbackResult();
		result.setReturnCode(SaoBeiResult.RESULT_SUCCESS.getCode());
		result.setReturnMsg("成功");
		return result;
	}
}
