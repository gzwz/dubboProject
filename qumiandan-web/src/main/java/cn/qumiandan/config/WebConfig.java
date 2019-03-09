package cn.qumiandan.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import cn.qumiandan.common.converter.ByteToBaseEnumsConverterFactory;
import cn.qumiandan.common.converter.StringToBaseEnumsConverterFactory;

/**
 * webconfig 参数绑定值转化
 * @author yuleidian
 * @version 创建时间：2018年11月5日 下午4:00:16
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport  {
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new StringToBaseEnumsConverterFactory());
		registry.addConverterFactory(new ByteToBaseEnumsConverterFactory());
	}
	

	@Bean("restTemplate")
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
		return restTemplate;
	}
	
	
	
	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(getMappingJackson2HttpMessageConverter());
		super.extendMessageConverters(converters);
	}

	/**
	 * 为了支持扫呗的 text/xml;charset=UTF-8
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<MediaType> list = new ArrayList<>();
		list.add(new MediaType("text", "xml", Charset.forName("UTF-8")));
		converter.setSupportedMediaTypes(list);
		return converter;
	}

}
