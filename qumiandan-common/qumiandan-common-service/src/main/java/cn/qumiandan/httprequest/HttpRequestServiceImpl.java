package cn.qumiandan.httprequest;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.httprequest.api.IHttpRequestService;
import cn.qumiandan.utils.HttpClientUtil;

@Component
@Service(interfaceClass = IHttpRequestService.class)
public class HttpRequestServiceImpl implements IHttpRequestService {

	@Override
	public String postRequest(String url, Map<String, String> param) {
		return HttpClientUtil.postForm(url, param);
	}

	@Override
	public String doPostForJson(String url, Map<String, String> params) {
		return HttpClientUtil.doPostForJson(url, params);
	}

	@Override
	public String doGetForJson(String url, Map<String, String> param) {
		return HttpClientUtil.doGetForJson(url, param);
	}

	@Override
	public String doPostForString(String url, String entity) {
		return HttpClientUtil.doPostForString(url, entity);
	}
}
