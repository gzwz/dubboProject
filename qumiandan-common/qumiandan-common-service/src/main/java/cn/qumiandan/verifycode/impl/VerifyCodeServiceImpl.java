package cn.qumiandan.verifycode.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.CsErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.utils.VerifyCodeUtils;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.verifycode.entity.IdentifyingCode;
import cn.qumiandan.verifycode.mapper.IdentifyingCodeMapper;
import cn.qumiandan.verifycode.vo.IdentifyingCodeVO;
import cn.qumiandan.verifycode.vo.VerifyCodeAndImgVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IVerifyCodeService.class)
public class VerifyCodeServiceImpl implements IVerifyCodeService {

	@Autowired
    StringRedisTemplate stringRedisTemplate;
	@Autowired
	IdentifyingCodeMapper identifyingCodeMapper;
	
	// 短信验证码有效期
    @Value("${ali.sms.smsTimeout}")
    private int smsTimeout;
    // 规定时间类被限制的发送次数
    @Value("${ali.sms.smsLimitCountNum}")
    private int smsLimitCountNum;
    // 限制发送频率时间
    @Value("${ali.sms.smsLimitCountTime}")
    private int smsLimitCountTime;
    
    @Value("${verifyCode.periodOfValidity}")
    private int periodOfValidity;
    
    // 阿里云短信验证码前缀
    private final static String AliSmsVcPrefix = "AliSmsVerifyCode";
    // 图片验证码前缀
    private final static String ImgVcPrefix = "ImgVcPrefixCode";
    
	@Override
	public String getVerifyCodeByMobile(String mobile,String ip) throws QmdException {
		boolean flag = validationByMobileAndIp(mobile, ip);
		if (flag) {
			throw new QmdException(CsErrorCode.CS1002);
		}
		// 默认6位字符串
		log.error("mobile = "+mobile);
		String code = VerifyCodeUtils.generateVerifyCode(6,VerifyCodeUtils.VERIFY_NUM_CODES);
		//将手机号和验证码绑定关系存到redis;
		BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(AliSmsVcPrefix+":"+mobile);
		log.info("发送验证码成功：code="+code+"  存入Redis key = "+AliSmsVcPrefix+":"+mobile);
		ops.set(code,smsTimeout,TimeUnit.SECONDS);
		return code;
	}
	
	public boolean validationVerifyCodeAndMobile(String mobile,String code) {
		//此处key 必须和或请求验证码放入key 保持一致
		BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(AliSmsVcPrefix+":"+mobile);
		String rcode = ops.get();
		if (rcode == null) {
			throw new QmdException(CsErrorCode.CS1006);
		}
		if (StringUtils.equals(code, rcode)) {
			log.info("验证手机号+验证码成功：mobile = "+mobile+" code = "+code+"  存入Redis key = "+AliSmsVcPrefix+":"+mobile);
			return true;
		}else {
			log.info("验证手机号+验证码失败：mobile = "+mobile+" code = "+code+"  存入Redis key = "+AliSmsVcPrefix+":"+mobile);
			
			throw new QmdException(CsErrorCode.CS1006);
		}
	}
	
	@Override
	public boolean validationByMobileAndIp(String mobile, String ip) {
		// 第一次查询 没有数据将数据set进redis , 并设置短信发送频率
		BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(mobile+":"+ip);
		Object obj = ops.get();
		// 为空继续发送
		if (obj == null) {
			ops.set("1", smsLimitCountTime, TimeUnit.SECONDS);
		}else {
			// 不为空，检测发送次数 发送次数 >= smsLimitCountNum 限制发送
			int num = Integer.valueOf(String.valueOf(obj));
			if (num >= smsLimitCountNum) {
				return true;
			}else {
				ops.set(String.valueOf(Integer.valueOf(ops.get()) + 1),smsLimitCountTime,TimeUnit.SECONDS);
			}
		}
		return false;
	}

	
	@Override
	public VerifyCodeAndImgVO getVerifyCodeAndImg(String sign) throws QmdException {
		VerifyCodeAndImgVO vo = new VerifyCodeAndImgVO();
		ByteArrayOutputStream outputFile = new ByteArrayOutputStream();
		// 图片长200，宽50，字符串长度6
		String code = null;
		try {
			code = VerifyCodeUtils.outputVerifyImage(200, 50, outputFile, 4);
			vo.setImgBytes(outputFile.toByteArray());
			outputFile.close();
			log.info("获取图片验证码成功 code = "+code);
		} catch (IOException e) {
			log.error(CsErrorCode.CS1003.getCode(),e.toString());
			throw new QmdException(CsErrorCode.CS1003);
		}
		BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(ImgVcPrefix+":"+sign);
		log.info("生成图片验证码成功：code="+code+"  存入Redis key = "+ImgVcPrefix+":"+sign);
		ops.set(code,periodOfValidity,TimeUnit.SECONDS);
		return vo;
		 
	}
	
	@Override
	public boolean validationImgVerifyCode(String sign, String code) throws QmdException {
		//此处key 必须和或请求验证码放入key 保持一致
		BoundValueOperations<String, String> ops = stringRedisTemplate.boundValueOps(ImgVcPrefix+":"+sign);
		String rcode = ops.get();
		if (rcode == null) {
			log.info("获取验证码图片内容为空");
			throw new QmdException(CsErrorCode.CS1005);
		}
		if (StringUtils.equals(code.toLowerCase(), rcode.toLowerCase())) {
			log.info("图片验证码验证成功");
			return true;
		}else {
			throw new QmdException(CsErrorCode.CS1005);
		}
	}

	
	/**
	 * 在sys_identifying_code表中添加短信验证码记录
	 * @param identifyingCodeVO
	 * @return
	 */
	@Override
	public int addIdentifyingCode(IdentifyingCodeVO identifyingCodeVO) {
		IdentifyingCode identifyingCode = CopyBeanUtil.copyBean(identifyingCodeVO, IdentifyingCode.class);
		identifyingCode.setCreateDate(new Date());
		return identifyingCodeMapper.insert(identifyingCode);
	}
}
