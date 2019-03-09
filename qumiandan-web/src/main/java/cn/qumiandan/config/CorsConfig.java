package cn.qumiandan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 因为后端分离
 * tomcat跨域设置
 * @author yuleidian
 * @version 创建时间：2018年11月5日 下午1:44:42
 */
@Configuration
public class CorsConfig {

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 允许任何域名
		corsConfiguration.addAllowedHeader("*"); // 允许任何头
		// corsConfiguration.addAllowedMethod("*"); // 允许任何方法
		corsConfiguration.addAllowedMethod(HttpMethod.GET);
		corsConfiguration.addAllowedMethod(HttpMethod.POST);
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
		corsConfiguration.addAllowedMethod(HttpMethod.HEAD);
		corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
		corsConfiguration.addAllowedMethod(HttpMethod.PUT);
		corsConfiguration.addAllowedMethod(HttpMethod.TRACE);
		corsConfiguration.setAllowCredentials(true);
		//corsConfiguration.addAllowedMethod("*");
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig()); // 注册
		return new CorsFilter(source);
	}
}
