/*package cn.qumiandan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.xxl.job.core.executor.XxlJobExecutor;

import lombok.Data;

*//**
 * xxl-job 任务配置
 * @author yuleidian
 * @version 创建时间：2019年1月7日 下午2:10:43
 *//*
@Data
@Configuration
@ConfigurationProperties(prefix = "xxl")
@PropertySource("classpath:xxlJbo.properties")
public class XxlJobConfig {

	private String adminAddresses;

	private String appName;

	private String ip;

	private int port;

	private String accessToken;

	private String logPath;

	private int logRetentionDays;

	@Bean(initMethod = "start", destroyMethod = "destroy")
	public XxlJobExecutor xxlJobExecutor() {
		XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
		xxlJobExecutor.setIp(ip);
		xxlJobExecutor.setPort(port);
		xxlJobExecutor.setLogPath(logPath);
		xxlJobExecutor.setAppName(appName);
		xxlJobExecutor.setAccessToken(accessToken);
		xxlJobExecutor.setAdminAddresses(adminAddresses);
		xxlJobExecutor.setLogRetentionDays(logRetentionDays);
		return xxlJobExecutor;
	}
}
*/