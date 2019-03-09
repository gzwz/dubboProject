package cn.qumiandan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * 启动类
 * @author yld
 */
@EnableDubboConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Slf4j
public class MutualWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MutualWebApplication.class, args); 
		log.info("启动成功。。。。。。");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MutualWebApplication.class);
	}
}
