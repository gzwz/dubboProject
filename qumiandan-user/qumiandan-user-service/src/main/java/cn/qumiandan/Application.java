package cn.qumiandan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication
@EnableDubboConfiguration
public class Application {
	
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(Application.class, args);
    	log.info("启动成功。。。。。。");
    }

}