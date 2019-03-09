package cn.qumiandan.pay.saobei.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.httprequest.api.IHttpRequestService;
import cn.qumiandan.pay.RequestDefinition;
import cn.qumiandan.pay.ResponseDefinition;
import cn.qumiandan.pay.saobei.config.SaoBeiConfig;
import cn.qumiandan.pay.saobei.model.request.merchant.ChecMerchantNameReq;
import cn.qumiandan.pay.saobei.model.request.merchant.QueryTerminalReq;
import cn.qumiandan.pay.saobei.model.request.merchant.TerminalReq;
import cn.qumiandan.pay.saobei.model.request.merchant.create.CreateBaseMerchantReq;
import cn.qumiandan.pay.saobei.service.ISaoBeiMerchantService;
import cn.qumiandan.pay.saobei.vo.SaoBeiShopAllInfoVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.BaseMerchantResVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.CreateMerchantResVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.CreateTerminalResVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.QueryTerminalResVO;
import cn.qumiandan.shop.api.IShopAuditRecordService;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.GsonHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = ISaoBeiMerchantService.class)
public class SaoBeiMerchantServiceImpl implements ISaoBeiMerchantService {

	@Resource
	private SaoBeiConfig saobeiConfig;
	
	@Reference
	private IHttpRequestService httpRequestService;
	
	@Reference
	private IShopService shopService;
	
	@Reference
	private IShopAuditRecordService shopAuditRecordService;
	
	@Override
	public String decrypt(String cryptograph) {
		return null;
	}
	
	@Override
	public BaseMerchantResVO checkMerchantName(String merchantName) {
		AssertUtil.isNull(merchantName, "SaoBeiMerchantServiceImpl|checMerchantName|传入参数merchantName为空");
		return doProcess(ChecMerchantNameReq.create(merchantName), saobeiConfig.getCheckMerchantNamelUrl(), CreateMerchantResVO.class);
	}
	
	@Override
	public CreateMerchantResVO createMerchant(SaoBeiShopAllInfoVO vo) {
		AssertUtil.isNull(vo, "SaoBeiMerchantServiceImpl|createMerchant|传入参数vo为空");
		return doProcess(CreateBaseMerchantReq.getCreateMerchantRequest(vo), saobeiConfig.getCreateMerchantUrl(), CreateMerchantResVO.class);
	}

	@Override
	public CreateMerchantResVO updateMerchant(SaoBeiShopAllInfoVO vo) {
		AssertUtil.isNull(vo, "SaoBeiMerchantServiceImpl|createMerchant|传入参数vo为空");
		return doProcess(CreateBaseMerchantReq.getCreateMerchantRequest(vo), saobeiConfig.getUpdateMerchantUrl(), CreateMerchantResVO.class);
	}
	
	@Override
	public CreateTerminalResVO createTerminal(String merchantNo) {
		AssertUtil.isNull(merchantNo, "SaoBeiMerchantServiceImpl|createTerminal|传入参数merchantNo为空");
		return doProcess(TerminalReq.create(merchantNo), saobeiConfig.getCreateTerminalUrl(), CreateTerminalResVO.class);
	}

	@Override
	public QueryTerminalResVO queryTerminal(String terminalId) {
		AssertUtil.isNull(terminalId, "SaoBeiMerchantServiceImpl|queryTerminal|传入参数terminalId为空");
		return doProcess(QueryTerminalReq.create(terminalId), saobeiConfig.getQueryTerminalUrl(), QueryTerminalResVO.class);
	}
	
	/**
	 * 执行请求
	 * @param req
	 * @param url
	 * @param typeof
	 * @return
	 */
	private <T extends ResponseDefinition> T doProcess(RequestDefinition req, String url, Class<T> typeof) {
		req.initInherentParameter(saobeiConfig.getInstNo(), saobeiConfig.getKey());
		Map<String, String> condition = req.getRequestParameter();
		// 发送请求
		log.info(new StringBuilder("SaoBeiMerchantServiceImpl|").append("doProcess|").append("request:").append(req.toJson()).toString());
		String resultStr = httpRequestService.doPostForJson(url, condition);
		log.info(new StringBuilder("SaoBeiMerchantServiceImpl|").append("doProcess|").append("response:").append(resultStr).toString());
		T result = null;
		if (StringUtils.isNotBlank(resultStr)) {
			result = GsonHelper.fromJson(resultStr, typeof);
		}
		return result;
	}
}
