package cn.qumiandan.web.pay.saobei.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.pay.saobei.service.ISaoBeiPayService;
import cn.qumiandan.pay.saobei.vo.JSPayVO;
import cn.qumiandan.pay.saobei.vo.WechatAuthopenIdVO;
import cn.qumiandan.pay.saobei.vo.response.pay.JSPayResVO;
import cn.qumiandan.pay.saobei.vo.response.pay.WechatAuthopenIdResVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.vo.TradeResultVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.pay.saobei.entity.reqeust.AuthopenIdParams;
import cn.qumiandan.web.pay.saobei.entity.reqeust.GameJsPayParams;
import cn.qumiandan.web.pay.saobei.entity.reqeust.JsPayParams;
import cn.qumiandan.web.pay.saobei.entity.reqeust.TradeSuccessParams;
import cn.qumiandan.web.pay.saobei.entity.response.JsPayRes;

/**
 * 支付请求controller
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午5:06:36
 */
@RestController
@RequestMapping("/payController/")
public class SaoBeiPayController {

	
	@Reference
	private ISaoBeiPayService saoBeiPayService;
	
	@Reference
	private ITradeDetailService tradeDetailService;
	
	/**
	 * 扫呗支付1.4.1
	 * 微信公众号JSAPI支付授权
	 */
	@RequestMapping("getAuthopenId")
	public Result<WechatAuthopenIdResVO> getAuthopenId(@Valid @RequestBody(required = false)AuthopenIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		return ResultUtils.success(saoBeiPayService.getAuthopenId(CopyBeanUtil.copyBean(params, WechatAuthopenIdVO.class)));
	}
	
	/**
	 * 1.4.0 公众号预支付（统一下单）
	 * 普通订单预支付统一下单
	 */
	@RequestMapping("jsPay")
	public Result<JsPayRes> jsPay(@Valid @RequestBody(required = false)JsPayParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		Result<JsPayRes> result = ResultUtils.success();
		JSPayVO vo = new JSPayVO();
		vo.setAppId(params.getAppId());
		vo.setCreateId(params.getCreateId());
		//vo.setGameOrderId(params.getGameOrderId());
		vo.setMerchantId(params.getMerchantId());
		vo.setOpenId(params.getOpenId());
		vo.setOrderId(params.getOrderId());
		vo.setPayType(params.getPayType());
		
		JSPayResVO jsPay = saoBeiPayService.jsPay(vo);
		if (Objects.nonNull(jsPay)) {
			if (jsPay.isSuccess()) {
				// JsPayRes data = CopyBeanUtil.copyBean(jsPay, JsPayRes.class);
				JsPayRes data = new JsPayRes();
				data.setAppId(jsPay.getAppId());
				data.setAliTradeNo(jsPay.getAliTradeNo());
				data.setNonceStr(jsPay.getNonceStr());
				data.setPackageStr(jsPay.getPackageStr());
				data.setPaySign(jsPay.getPaySign());
				data.setSignType(jsPay.getSignType());
				data.setTimeStamp(jsPay.getTimeStamp());
				data.setTokenId(jsPay.getTokenId());
				data.setTradeId(jsPay.getTradeId().toString());
				result.setData(data);
			}
		} else {
			result.setCode(GwErrorCode.GW9999.getCode());
			result.setMessage("扫呗请求错误");
		}
		return result;
	}
	
	/**
	 * 1.4.0 公众号预支付（统一下单）
	 * 游戏订单预支付统一下单
	 */
	@RequestMapping("gamejsPay")
	public Result<JsPayRes> gamejsPay(@Valid @RequestBody(required = false)GameJsPayParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		Result<JsPayRes> result = ResultUtils.success();
		JSPayVO vo = new JSPayVO();
		vo.setAppId(params.getAppId());
		vo.setCreateId(params.getCreateId());
		vo.setGameOrderId(params.getGameOrderId());
		vo.setMerchantId(params.getMerchantId());
		vo.setOpenId(params.getOpenId());
		vo.setOrderId(params.getOrderId());
		vo.setPayType(params.getPayType());
		
		JSPayResVO jsPay = saoBeiPayService.gameJsPay(vo);
		if (Objects.nonNull(jsPay)) {
			if (jsPay.isSuccess()) {
				//JsPayRes data = CopyBeanUtil.copyBean(jsPay, JsPayRes.class);
				JsPayRes data = new JsPayRes();
				data.setAppId(jsPay.getAppId());
				data.setAliTradeNo(jsPay.getAliTradeNo());
				data.setNonceStr(jsPay.getNonceStr());
				data.setPackageStr(jsPay.getPackageStr());
				data.setPaySign(jsPay.getPaySign());
				data.setSignType(jsPay.getSignType());
				data.setTimeStamp(jsPay.getTimeStamp());
				data.setTokenId(jsPay.getTokenId());
				data.setTradeId(jsPay.getTradeId().toString());
				result.setData(data);
			}
		} else {
			result.setCode(GwErrorCode.GW9999.getCode());
			result.setMessage("扫呗请求错误");
		}
		return result;
	}
	
	/**
	 * 获取支付流水是否获取成功
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getTradeResult")
	public Result<TradeResultVO> getTradeResult(@Valid @RequestBody(required = false)TradeSuccessParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		TradeResultVO tradeResult = tradeDetailService.getTradeResult(params.getTradeId());
		return ResultUtils.success(tradeResult);
	}
}
