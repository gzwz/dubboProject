package cn.qumiandan.web.commonServer.verifycode.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.verifycode.vo.IdentifyingCodeVO;
import cn.qumiandan.verifycode.vo.VerifyCodeAndImgVO;
import cn.qumiandan.web.commonServer.sms.entity.TestSmsVO;
import cn.qumiandan.web.commonServer.sms.service.IAliSmsService;
import cn.qumiandan.web.commonServer.sms.template.SmsTemplate;
import cn.qumiandan.web.commonServer.verifycode.ISendVerifyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SendSmsServiceImpl implements ISendVerifyService {

	@Reference()
	private IVerifyCodeService verifyCodeService;
	
	@Autowired
	private IAliSmsService smsService;
	
	@Override
	public Result<Void> getSmsVerifyCode(String mobile, String ip) {
		AssertUtil.isNull(mobile, "手机号不能为空");
		AssertUtil.isNull(ip, "ip不能为空");
		// 从后台获取需要发送的验证码code;
		String code = verifyCodeService.getVerifyCodeByMobile(mobile,ip);
		TestSmsVO smsVO = new TestSmsVO();
		smsVO.setPhoneNumbers(mobile);
		smsVO.setTemplateCode(SmsTemplate.SMS_141220041.getCode());
		smsVO.setCode(code);
		// 调用发送短信验证码的service
		smsService.SendSmsResponse(smsVO);
		//  插入数据库
		IdentifyingCodeVO identifyingCodeVO = new IdentifyingCodeVO();
		identifyingCodeVO.setMobile(mobile);
		identifyingCodeVO.setCode(code);
		verifyCodeService.addIdentifyingCode(identifyingCodeVO);
		log.info("发送成功！");
		return ResultUtils.success("发送成功！");
	}
	
	@Override
	public Result<Void> validateSmsVerifyCode(String code, String mobile) {
		AssertUtil.isNull(mobile, "手机号不能为空");
		AssertUtil.isNull(code, "验证码不能为空");
		verifyCodeService.validationVerifyCodeAndMobile(mobile, code);
		log.info("验证成功");
		return ResultUtils.success("短信验证码验证通过");
	}
	
 
	@Override
	public Result<Void> validateImgVerifyCode(String sign, String code) {
		AssertUtil.isNull(sign, "唯一标志不能为空");
		AssertUtil.isNull(code, "验证码不能为空");
		verifyCodeService.validationImgVerifyCode(sign, code);
		log.info("短信验证码验证通过");
		return ResultUtils.success("短信验证码验证通过");
	}

	@Override
	public Result<VerifyCodeAndImgVO> getImgVerifyCode(String sign) {
		AssertUtil.isNull(sign, "唯一标志不能为空");
		VerifyCodeAndImgVO img = verifyCodeService.getVerifyCodeAndImg(sign);
		Result<VerifyCodeAndImgVO> result = new Result<>();
		result.setData(img);
		result.setResult(GwErrorCode.GW0000);
		return result;
	}

}
