package cn.qumiandan.pay.lianlian.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;

import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.lianlian.api.ILianLianPayCallbackService;
import cn.qumiandan.pay.lianlian.confg.LianLianConfig;
import cn.qumiandan.pay.lianlian.enums.PaymentStatusEnum;
import cn.qumiandan.pay.lianlian.util.SignUtil;
import cn.qumiandan.pay.lianlian.util.TraderRSAUtil;
import cn.qumiandan.pay.lianlian.vo.LianLianPaymentNoticeVO;
import cn.qumiandan.pay.withdraw.api.IWithdrawCashService;
import cn.qumiandan.pay.withdraw.enums.WithdrawStatusEnum;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = ILianLianPayCallbackService.class)
public class LianLianPayCallbackServiceImpl implements ILianLianPayCallbackService {

	@Autowired
	private IWithdrawCashService withdrawCashService;
	
	@Autowired
	private LianLianConfig config;
	
	@Override
	public void paymentCallback(LianLianPaymentNoticeVO vo) {
		if (Objects.isNull(vo)) {
			log.error("连连支付回调,回调参数vo为空");
			throw new QmdException("连连支付回调我们 回调参数为空");
		}
		String params = JSONObject.toJSONString(vo);
		// 验签
		boolean signCheck = TraderRSAUtil.checksign(config.getBusinessPrivateKey(), SignUtil.genSignData(JSONObject.parseObject(params)), vo.getSign());
		if (!signCheck) {
			// 传送数据被篡改，可抛出异常，再人为介入检查原因
			// 回调内容先验签，再处理相应逻辑
			log.info("连连支付回调数据被串改|noOrder:" + vo.getNoOrder());
			throw new QmdException("连连支付回调数据被串改");
		}
		
		if (StringUtils.isBlank(vo.getNoOrder())) {
			log.error("连连支付回调|noOrder为空, 无法找到提现申请");
			throw new QmdException("连连支付回调|noOrder为空");
		}
		
		WithdrawCashVo withdrawCash = withdrawCashService.getWithdrawCashInfoById(Long.parseLong(vo.getNoOrder()));
		if (Objects.isNull(withdrawCash)) {
			log.error("连连支付回调, 无法找到提现申请|id:" + vo.getNoOrder());
			throw new QmdException("连连支付回调, 无法找到提现申请");
		}
		
		// 防止重复处理
		if (!WithdrawStatusEnum.ApplyAuditing.getCode().equals(withdrawCash.getStatus())) {
			log.error("连连支付回调, 申请已做回调处理|id:" + vo.getNoOrder());
			throw new QmdException(PayErrorCode.PAY8000);
		}
		
		// 打款成功
		if (PaymentStatusEnum.PAYMENT_SUCCESS.getValue().equals(vo.getResultPay())) {
			BigDecimal callbackMoney = new BigDecimal(vo.getMoneyOrder());
			BigDecimal actualMoney = callbackMoney.multiply(new BigDecimal(100));
			Date paymentDate;
			try {
				paymentDate = DateUtils.parseDate(vo.getSettleDate(), "yyyyMMddHHmmss");
			} catch (ParseException e) {
				e.printStackTrace();
				log.error("回调时间转换异常", e);
				throw new QmdException("回调时间转换异常");
			}
			// 验证打款金额和申请金额一致性
			if (actualMoney.compareTo(withdrawCash.getWithdrawalAmount()) != 0) {
				log.error("连连支付回调的金额和申请提现金额不一致|申请id: " + withdrawCash.getId() + "|申请金额:" + withdrawCash.getWithdrawalAmount() + "|回调金额:" + actualMoney);
				withdrawCash.setErrorMessage("连连支付回调的金额和申请提现金额不一致 回调金额" + callbackMoney);
				withdrawCash.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
				withdrawCash.setRequestParam(params);
				withdrawCash.setPaymentDate(paymentDate);
				withdrawCashService.updateWithdrawCashById(withdrawCash);
				return;
			}
			withdrawCash.setPaymentDate(paymentDate);
			withdrawCash.setStatus(WithdrawStatusEnum.FinishedWithdraw.getCode());
			withdrawCash.setOutTradeNo(vo.getOidPaybill());
			withdrawCash.setRequestParam(params);
			withdrawCash.setRemarkAudit("打款完成");
			withdrawCashService.updateWithdrawCashById(withdrawCash);
		} else if (PaymentStatusEnum.PAYMENT_FAILURE.getValue().equals(vo.getResultPay())) {
			// TODO 商户更新订单为失败，处理自己的业务逻辑
			withdrawCash.setErrorMessage("连连支付回调打款失败, 请手动处理");
			withdrawCash.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
			withdrawCash.setRequestParam(params);
			withdrawCashService.updateWithdrawCashById(withdrawCash);
		} else if (PaymentStatusEnum.PAYMENT_RETURN.getValue().equals(vo.getResultPay())) {
			withdrawCash.setErrorMessage("连连支付回调, 退款这种情况是极小概率情况下才会发生的，个别银行处理机制是先扣款后再打款给用户时, 可直接联系连连客服");
			withdrawCash.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
			withdrawCash.setRequestParam(params);
			withdrawCashService.updateWithdrawCashById(withdrawCash);
		} else {
			// TODO 返回订单为退款状态 ，商户可以更新订单为失败或者退款
			// 退款这种情况是极小概率情况下才会发生的，个别银行处理机制是先扣款后再打款给用户时，
			// 才检验卡号姓名信息的有效性，当卡号姓名信息有误发生退款，实际上钱没打款到商户。
			// 这种情况商户代码上也可不做考虑，如发生用户投诉未收到钱，可直接联系连连客服，连连会跟银行核对
			// 退款情况，异步通知会通知两次，先通知成功，后通知退款（极小概率情况下才会发生的）
			withdrawCash.setErrorMessage("连连支付回调, 打款失败");
			withdrawCash.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
			withdrawCash.setRequestParam(params);
			withdrawCashService.updateWithdrawCashById(withdrawCash);
		}
	}

}
