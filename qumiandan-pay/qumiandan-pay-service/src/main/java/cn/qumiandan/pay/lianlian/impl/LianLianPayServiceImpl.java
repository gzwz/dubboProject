package cn.qumiandan.pay.lianlian.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lianlianpay.security.utils.LianLianPaySecurity;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.httprequest.api.IHttpRequestService;
import cn.qumiandan.pay.lianlian.api.ILianLianPayService;
import cn.qumiandan.pay.lianlian.confg.LianLianConfig;
import cn.qumiandan.pay.lianlian.enums.PaymentStatusEnum;
import cn.qumiandan.pay.lianlian.enums.RetCodeEnum;
import cn.qumiandan.pay.lianlian.model.PaymentConstant;
import cn.qumiandan.pay.lianlian.model.PaymentRequestBean;
import cn.qumiandan.pay.lianlian.model.QueryPaymentRequestBean;
import cn.qumiandan.pay.lianlian.model.QueryPaymentResponseBean;
import cn.qumiandan.pay.lianlian.util.SignUtil;
import cn.qumiandan.pay.lianlian.util.TraderRSAUtil;
import cn.qumiandan.pay.lianlian.vo.PaymentResponseVO;
import cn.qumiandan.pay.withdraw.api.IWithdrawCashService;
import cn.qumiandan.pay.withdraw.enums.WithdrawStatusEnum;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashResultVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashVo;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = ILianLianPayService.class)
public class LianLianPayServiceImpl implements ILianLianPayService {
	
	@Reference
	private IHttpRequestService httpRequestService;
	
	@Autowired
	private IWithdrawCashService withdrawCashService;
	
	/*@Autowired
	private IBankCardService bankCardService;*/
	
	@Autowired
	private IPayAccountService payAccountService;
	
	@Autowired
	private LianLianConfig config;
	
	@Override
	public WithdrawCashResultVO withdraw(Long applyId) {
		WithdrawCashResultVO result = new WithdrawCashResultVO();
		AssertUtil.isNull(applyId, "LianLianPayServiceImpl|withdraw|传入参数applyId为空");
		WithdrawCashVo applyInfo = withdrawCashService.getWithdrawCashInfoById(applyId);
		if (Objects.isNull(applyInfo)) {
			log.error("提现失败, 查询不到申请提现记录 applyId:" + applyId);
			applyInfo.setStatus(WithdrawStatusEnum.AuditUnPass.getCode());
			applyInfo.setRemarkAudit("可提现余额不足");
			result.setMessage("提现失败, 查询不到不申请提现记录");
			return result;
		}
		
		PayAccountVO account = payAccountService.getPayAccountById(applyInfo.getAccountId());
		if (Objects.isNull(account)) {
			log.error("提现失败,  提现账户不存在 accountId:" + applyInfo.getAccountId());
			result.setMessage("提现失败, 提现账户不存在");
			return result;
		}
		
		/*BankCardVO bankcard = bankCardService.getBankCardByAccountId(applyInfo.getAccountId());
		if (Objects.isNull(bankcard)) {
			applyInfo.setStatus(WithdrawStatusEnum.AuditUnPass.getCode());
			applyInfo.setRemarkAudit("账户没有绑定银行卡");
			withdrawCashService.updateWithdrawCashById(applyInfo);
			result.setMessage("账户没有绑定银行卡");
			return result;
		}*/
		
		// 固定手续费
		BigDecimal fee = new BigDecimal(200);
		// 实际提现金额
		BigDecimal withDrawMoney = applyInfo.getWithdrawalAmount().subtract(fee).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
		
		/*applyInfo.setBankNo(bankcard.getBankCardNo());
		applyInfo.setCardHolder(bankcard.getBankCardHolder());*/
		
		// 连连内部测试环境数据(商户测试期间需要用正式的数据测试，测试时默认单笔单日单月额度50，等测试OK，申请走上线流程打开额度）
		Date now = new Date();
		PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
		paymentRequestBean.setNo_order(applyInfo.getId().toString());
		paymentRequestBean.setDt_order(DateUtil.formatDate(now, "yyyyMMddHHmmss"));
		paymentRequestBean.setMoney_order(withDrawMoney.toString());
		// paymentRequestBean.setMoney_order("0.01");
		paymentRequestBean.setCard_no(applyInfo.getBankNo());
		paymentRequestBean.setAcct_name(applyInfo.getCardHolder());
		paymentRequestBean.setInfo_order("提现");
		paymentRequestBean.setFlag_card("0");
		paymentRequestBean.setMemo("营业额提现");
		// 填写商户自己的接收付款结果回调异步通知
		paymentRequestBean.setNotify_url(config.getCallbackUrl());
		paymentRequestBean.setOid_partner(config.getOidPartner());
		paymentRequestBean.setPlatform(config.getOurIp());
		paymentRequestBean.setApi_version(config.getApiVersion());
		paymentRequestBean.setSign_type(config.getSignType());
		// 用商户自己的私钥加签
		paymentRequestBean.setSign(SignUtil.genRSASign(JSON.parseObject(JSON.toJSONString(paymentRequestBean)), config.getBusinessPrivateKey()));
	
		String jsonStr = JSON.toJSONString(paymentRequestBean);
		// 用银通公钥对请求参数json字符串加密
		// 报Illegal sign key
		// size异常时，可参考这个网页解决问题http://www.wxdl.cn/java/security-invalidkey-exception.html
		String encryptStr = null;
		try {
			encryptStr = LianLianPaySecurity.encrypt(jsonStr, config.getPublicKeyOnline());
		} catch (Exception e) {
			// 加密异常
			log.error("提现失败, 加密失败encryptStr|加密失败encryptStr|applyId:" + applyId);
		}
		if (StringUtils.isEmpty(encryptStr)) {
			// 加密异常
			log.error("提现失败, 加密失败encryptStr|加密失败encryptStr加密后的为空|applyId:" + applyId);
			throw new QmdException("提现失败, 系统异常");
		}

		Map<String, String> condition = Maps.newHashMap();
 		condition.put("oid_partner", config.getOidPartner());
 		condition.put("pay_load", encryptStr);
		String response = httpRequestService.doPostForJson(config.getPaymentUrl(), condition); //HttpUtil.doPost("https://instantpay.lianlianpay.com/paymentapi/payment.htm", json, "UTF-8");
		PaymentResponseVO paymentResponseBean = null;
		if (StringUtils.isBlank(response)) {
			// 出现异常时调用订单查询，明确订单状态，不能私自设置订单为失败状态，以免造成这笔订单在连连付款成功了，而商户设置为失败
			queryPaymentAndDealBusiness(paymentRequestBean.getNo_order(), applyInfo);
		} else {
			paymentResponseBean = JSONObject.parseObject(response, PaymentResponseVO.class);
			// 对返回0000时验证签名
			if (paymentResponseBean.getRet_code().equals("0000")) {
				// 先对结果验签
				boolean signCheck = TraderRSAUtil.checksign(config.getPublicKeyOnline(), SignUtil.genSignData(JSONObject.parseObject(response)), paymentResponseBean.getSign());
				if (!signCheck) {
					// 传送数据被篡改，可抛出异常，再人为介入检查原因
					log.error("连连返回提现数据被串改|提现applyId:" + applyId);
					result.setMessage("提现失败, 系统错误, 请联系管理员");
					return result;
				}
				// 已生成连连支付单，付款处理中（交易成功，不是指付款成功，是指跟连连流程正常），商户可以在这里处理自已的业务逻辑（或者不处理，在异步回调里处理逻辑）,最终的付款状态由异步通知回调告知
				result.setSuccess(true);
				result.setMessage("打款成功");
				applyInfo.setPaymentDate(now);
				applyInfo.setRequestParam(response);
				applyInfo.setRemarkAudit("打款完成");
				applyInfo.setStatus(WithdrawStatusEnum.FinishedWithdraw.getCode());
				applyInfo.setOutTradeNo(paymentResponseBean.getOid_paybill());
			} else if (paymentResponseBean.getRet_code().equals("4002") || paymentResponseBean.getRet_code().equals("4004")) {
				// 当调用付款申请接口返回4002，4003，4004,会同时返回验证码，用于确认付款接口
				// 对于疑似重复订单，需先人工审核这笔订单是否正常的付款请求，而不是系统产生的重复订单，确认后再调用确认付款接口或者在连连商户站后台操作疑似订单，api不调用确认付款接口
				// 对于疑似重复订单，也可不做处理，
				result.setSuccess(true);
				applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
			} else if (RetCodeEnum.isNeedQuery(paymentResponseBean.getRet_code())) {
				// 出现1002，2005，4006，4007，4009，9999这6个返回码时（或者对除了0000之后的code都查询一遍查询接口）调用付款结果查询接口，明确订单状态，不能私自设置订单为失败状态，以免造成这笔订单在连连付款成功了，而商户设置为失败
				// 第一次测试对接时，返回{"ret_code":"4007","ret_msg":"敏感信息解密异常"},可能原因报文加密用的公钥改动了,demo中的公钥是连连公钥，商户生成的公钥用于上传连连商户站用于连连验签，生成的私钥用于加签
				//queryPaymentAndDealBusiness(paymentRequestBean.getNo_order());
				applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
			} else {
				// 返回其他code时，可将订单置为失败
				applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
			}
			applyInfo.setErrorMessage(RetCodeEnum.getRetCodeEnumByValue(paymentResponseBean.getRet_code()).getMsg());
			applyInfo.setRequestParam(response);
			withdrawCashService.updateWithdrawCashById(applyInfo);
		}
		result.setMessage(RetCodeEnum.getRetCodeEnumByValue(paymentResponseBean.getRet_code()).getMsg());
		return result;
	}

	// 异常时，先查询订单状态，再根据订单状态处理业务逻辑
	public void queryPaymentAndDealBusiness(String orderNo, WithdrawCashVo applyInfo) {
		// 连连内部测试环境数据
		QueryPaymentRequestBean queryRequestBean = new QueryPaymentRequestBean();
		queryRequestBean.setNo_order(orderNo);
		queryRequestBean.setOid_partner(config.getOidPartner());
		//queryRequestBean.setPlatform(config.getOurIp());
		queryRequestBean.setApi_version(config.getApiVersion());
		queryRequestBean.setSign_type(config.getSignType());
		queryRequestBean.setSign(SignUtil.genRSASign(JSON.parseObject(JSON.toJSONString(queryRequestBean)), config.getBusinessPrivateKey()));
		String queryResult = httpRequestService.doPostForString(config.getQueryPaymentUrl(), JSON.parseObject(JSON.toJSONString(queryRequestBean)).toString()); //HttpUtil.doPost("https://instantpay.lianlianpay.com/paymentapi/queryPayment.htm", JSON.parseObject(JSON.toJSONString(queryRequestBean)), "UTF-8");
		if (StringUtils.isEmpty(queryResult)) {
			// 可抛异常，查看原因
			return;
		}
		QueryPaymentResponseBean queryPaymentResponseBean = JSONObject.parseObject(queryResult, QueryPaymentResponseBean.class);
		// 先对结果验签
		boolean signCheck = TraderRSAUtil.checksign(PaymentConstant.PUBLIC_KEY_ONLINE, SignUtil.genSignData(JSONObject.parseObject(queryResult)), queryPaymentResponseBean.getSign());
		if (!signCheck) {
			// 传送数据被篡改，可抛出异常，再人为介入检查原因
			return;
		}
		if (queryPaymentResponseBean.getRet_code().equals("0000")) {
			PaymentStatusEnum paymentStatusEnum = PaymentStatusEnum.getPaymentStatusEnumByValue(queryPaymentResponseBean.getResult_pay());
			applyInfo.setRequestParam(queryResult);
			// TODO商户根据订单状态处理自已的业务逻辑
			switch (paymentStatusEnum) {
			case PAYMENT_APPLY:
				// 付款申请，这种情况一般不会发生，如出现，请直接找连连技术处理
				break;
			case PAYMENT_CHECK:
				// 复核状态 TODO
				// 返回4002，4003，4004时，订单会处于复核状态，这时还未创建连连支付单，没提交到银行处理，如需对该订单继续处理，需商户先人工审核这笔订单是否是正常的付款请求，没问题后再调用确认付款接口
				// 如果对于复核状态的订单不做处理，可当做失败订单
				applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
				break;
			case PAYMENT_SUCCESS:
				// 成功 TODO
				applyInfo.setStatus(WithdrawStatusEnum.FinishedWithdraw.getCode());
				break;
			case PAYMENT_FAILURE:
				// 失败 TODO
				applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
				break;
			case PAYMENT_DEALING:
				// 处理中 TODO
				applyInfo.setStatus(WithdrawStatusEnum.WaitingCashWithdraw.getCode());
				break;
			case PAYMENT_RETURN:
				// 退款 TODO
				// 可当做失败（退款情况
				// 极小概率下会发生，个别银行处理机制是先扣款后打款给用户时再检验卡号信息是否正常，异常时会退款到连连商户账上）
				applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
				break;
			case PAYMENT_CLOSED:
				// 关闭 TODO 可当做失败 ，对于复核状态的订单不做处理会将订单关闭
				applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
				break;
			default:
				break;
			}
		} else if (queryPaymentResponseBean.getRet_code().equals("8901")) {
			// 订单不存在，这种情况可以用原单号付款，最好不要换单号，如换单号，在连连商户站确认下改订单是否存在，避免系统并发时返回8901，实际有一笔订单
			applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
		} else {
			// 查询异常（极端情况下才发生,对于这种情况，可人工介入查询，在连连商户站查询或者联系连连客服，查询订单状态）
			applyInfo.setStatus(WithdrawStatusEnum.AbnormalAutomaticWithdraw.getCode());
		}
	}
}
