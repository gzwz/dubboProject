package cn.qumiandan.system.initializer.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.shirofilter.api.IShiroFilterService;
import cn.qumiandan.shirofilter.vo.ShiroFilterVO;
import cn.qumiandan.system.initializer.service.SystemStartInitializerService;
import cn.qumiandan.system.shiro.ShiroRealm;
import cn.qumiandan.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * CommandLineRunner 系统启动后初始化数据
 * 初始化顺序为 spring加载Component->dubbo加载->CommandLineRunner执行
 * @author yuleidian
 * @version 创建时间：2018年11月19日 上午10:22:12
 */
@Slf4j
@Component
public class SystemStartInitializer implements SystemStartInitializerService, CommandLineRunner {

	@Reference
	private IShiroFilterService shiroFilterService;

	@Reference()
	private IRoleService roleService;
	
	@Override
	public void run(String... args) throws Exception {
		//-------------------------------加载shiro拦截数据------------------------------------
		ShiroRealm shiroRealm = SpringContextUtils.getBean("shiroRealm");
		shiroRealm.setRoleService(roleService);
		
		initShiroFilterChain();
		
		// xxlJob handler注册
       /* Map<String, Object> serviceBeanMap = SpringContextUtils.getApplicationContext().getBeansWithAnnotation(JobHandler.class);
		if (serviceBeanMap != null && serviceBeanMap.size() > 0) {
			serviceBeanMap.values().stream().forEach(serviceBean -> {
				if (serviceBean instanceof IJobHandler) {
					String name = serviceBean.getClass().getAnnotation(JobHandler.class).value();
					IJobHandler handler = (IJobHandler) serviceBean;
					if (XxlJobExecutor.loadJobHandler(name) != null) {
						throw new RuntimeException("xxl-job jobhandler naming conflicts.");
					}
					XxlJobExecutor.registJobHandler(name, handler);
				}
			});
		}
		log.info("xxlJob Handler注册完成");*/
	}

	@Override
	public void initShiroFilterChain() {
		List<ShiroFilterVO> filterList = shiroFilterService.getShiroFilterVOOrderBySort();
		AbstractShiroFilter instan = SpringContextUtils.getBean("shiroFilter");
		PathMatchingFilterChainResolver chainResolver = (PathMatchingFilterChainResolver) instan.getFilterChainResolver();
		FilterChainManager chainManager = chainResolver.getFilterChainManager();
		if (CollectionUtils.isNotEmpty(filterList)) {
			filterList.forEach(filter -> {
				chainManager.createChain(filter.getName(), filter.getPerms());
			});
		}
		// 注入角色service
		log.info("shiro拦截加载成功");
	}

}
