package cn.qumiandan.pay.lianlian.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.qumiandan.pay.lianlian.model.PaymentRequestBean;
import cn.qumiandan.utils.DateUtil;

public class SignUtil {

	public static void main(String[] args) throws Exception {
		PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
		paymentRequestBean.setNo_order("123456");
		paymentRequestBean.setDt_order(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
		paymentRequestBean.setMoney_order("0.01");
		paymentRequestBean.setCard_no("123456456465");
		paymentRequestBean.setAcct_name("小明");
		paymentRequestBean.setInfo_order("提现");
		paymentRequestBean.setFlag_card("0");
		paymentRequestBean.setMemo("趣免项目提现");
		// 填写商户自己的接收付款结果回调异步通知
		paymentRequestBean.setNotify_url("http://www.baidu.com");
		paymentRequestBean.setOid_partner("123123321");
		paymentRequestBean.setPlatform("12456123");
		paymentRequestBean.setApi_version("0.1");
		paymentRequestBean.setSign_type("RAS");
		// 用商户自己的私钥加签
		paymentRequestBean.setSign(SignUtil.genRSASign(JSON.parseObject(JSON.toJSONString(paymentRequestBean)), "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMcVi6ij2rRyWnct5JbbZOKzTvz6/9LkAWjtPA6BMuvJ5/5Cce+VJ6FLsFPeE9p4RVJbgxtHwoy2iO/b5pu4v+7FO0rcRErADAoXkprc6faQseD52sMixHaeDf5lnIOp/r7sEMXxQvce1PV+r5qMScDE0vwigYM+S18DTzsBIO6hAgMBAAECgYAwvle5bJTpxW3c7EGhpgnnb5uRWSs1yVmHBT7FFmEPVhoVKNOYo15WoZ+EiUU3ImdmqL1K+qDpru+MOr0Vt4BWWQdi2Mi/4AAfmK52WP7tdrB6LrirpmnosWwWiL86lMY9AGJ9eWnTnO6VL6vATYHaQ+dWdmlfFTz7cjKDzVlfmQJBAO1zvMDV6m0KpN5Q5Is1HjlLvISd+aB/OQdAGKa7Qt05QHIRYtfUHSrBIaBa9mYVqDSgsg5Z/4pcb4CPJrH6CwsCQQDWopNONCIXVkcfDx8E0a7NjmWQSDRSCD8JHn+YDSNAtohbsV+noJylQstwznemJzVph6uH0w+047mRJD1IFNiDAkEA1fhlLp+VD413452hVJAZHChTSG3Sc/4wtYfcruIv7omYuTFRkItjfRNr6cQOJQ9+822bBbOzwkppb7+PbvGS4QJBAKUy2IIWqi6gH3mOX+eLpCleclmM2YhSBIJ2UOqtIfcLBwTcDq2sdyTTtlYK9BiUkQ26eJHAJ5Sougk2hVxXfxUCQE5mj4jNZpiwOM4Q2ZS+9H84hEYsuZMVBYh5vBE4kBX9eSx251h6yD1G2wfSr5dRB2RXOyyr/l/82abQoDBBD2k="));
	}
	
	public static String genRSASign(JSONObject reqObj, String privateKey) {
		// 生成待签名串
		String sign_src = genSignData(reqObj);
		String sign = TraderRSAUtil.sign(privateKey, sign_src);
		return sign;
	}

	/**
	 * 生成待签名串
	 * 
	 * @param paramMap
	 * @return
	 */
	public static String genSignData(JSONObject jsonObject) {
		StringBuffer content = new StringBuffer();

		// 按照key做首字母升序排列
		List<String> keys = new ArrayList<String>(jsonObject.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			// sign 和ip_client 不参与签名
			if ("sign".equals(key)) {
				continue;
			}
			String value = (String) jsonObject.getString(key);
			// 空串不参与签名
			if (StringUtils.isEmpty(value)) {
				continue;
			}
			content.append((i == 0 ? "" : "&") + key + "=" + value);

		}
		String signSrc = content.toString();
		if (signSrc.startsWith("&")) {
			signSrc = signSrc.replaceFirst("&", "");
		}
		return signSrc;
	}
}
