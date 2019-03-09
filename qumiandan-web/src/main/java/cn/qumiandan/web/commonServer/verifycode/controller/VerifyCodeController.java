package cn.qumiandan.web.commonServer.verifycode.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.verifycode.vo.IdentifyingCodeVO;
import cn.qumiandan.verifycode.vo.VerifyCodeAndImgVO;
import cn.qumiandan.web.commonServer.sms.entity.TestSmsVO;
import cn.qumiandan.web.commonServer.sms.service.IAliSmsService;
import cn.qumiandan.web.commonServer.sms.template.SmsTemplate;
import cn.qumiandan.web.commonServer.verifycode.entity.request.GetImgVerifyCodeParams;
import cn.qumiandan.web.commonServer.verifycode.entity.request.SmsVerifyCodeParams;
import cn.qumiandan.web.commonServer.verifycode.entity.request.ValmgVerifyCodeParams;
import cn.qumiandan.web.frontServer.user.entity.request.MobileParams;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {
	
	@Reference()
	private IVerifyCodeService verifyCodeService;
	
	@Autowired
	private IAliSmsService smsService;

	/**
	 * 获取手机短信验证码
	 */
	@RequestMapping("getSmsVerifyCode")
	public Result<Void> getSmsVerifyCode(@RequestBody(required = false) @Valid MobileParams params, 
			BindingResult bindingResult,HttpServletRequest request) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		// 从后台获取需要发送的验证码code;
		String code = verifyCodeService.getVerifyCodeByMobile(params.getMobile(),request.getRemoteHost());
		TestSmsVO smsVO = new TestSmsVO();
		smsVO.setPhoneNumbers(params.getMobile());
		smsVO.setTemplateCode(SmsTemplate.SMS_141220041.getCode());
		smsVO.setCode(code);
		// 调用发送短信验证码的service
		smsService.SendSmsResponse(smsVO);
		//  插入数据库
		IdentifyingCodeVO identifyingCodeVO = new IdentifyingCodeVO();
		identifyingCodeVO.setMobile(params.getMobile());
		identifyingCodeVO.setCode(code);
		verifyCodeService.addIdentifyingCode(identifyingCodeVO);
		return ResultUtils.success("发送成功！");
	}
	
	/**
	 * 验证短信证码是否输入正确
	 */
	@RequestMapping("validateSmsVerifyCode")
	public Result<Void> validateSmsVerifyCode(@RequestBody(required = false) @Valid SmsVerifyCodeParams params, 
			BindingResult bindingResult,HttpServletRequest request) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		try {
			verifyCodeService.validationVerifyCodeAndMobile(params.getMobile(), params.getCode());
		} catch (QmdException e) {
			log.error("短信验证码错误",e.toString());
			return ResultUtils.error(e.getMessage());
		}
		return ResultUtils.success("短信验证码验证通过");
	}
	
	/**
	 * 获取图片验证码
	 * @param params
	 */
	@RequestMapping("getImgVerifyCode")
	public Result<VerifyCodeAndImgVO> getImgVerifyCode(@RequestBody(required = false) @Valid GetImgVerifyCodeParams params, 
			BindingResult bindingResult,HttpServletRequest request) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		VerifyCodeAndImgVO img = verifyCodeService.getVerifyCodeAndImg(params.getSign());
		Result<VerifyCodeAndImgVO> result = new Result<>();
		result.setData(img);
		result.setResult(GwErrorCode.GW0000);
		return result;
	}
 
	/**
	 * 验证图片验证码是否输入正确
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("validateImgVerifyCode")
	public Result<Void> validateImgVerifyCode(@RequestBody(required = false) @Valid ValmgVerifyCodeParams params, 
			BindingResult bindingResult,HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		try {
			verifyCodeService.validationImgVerifyCode(params.getSign(), params.getCode());
		} catch (QmdException e) {
			log.error("验证码图片验证码错误",e.toString());
			return ResultUtils.error("验证码图片验证码错误！");
		}
		return ResultUtils.success("短信验证码验证通过");
	}
 
}