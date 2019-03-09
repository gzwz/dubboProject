package cn.qumiandan.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.qumiandan.constant.RedisKeyPrefix;
import cn.qumiandan.system.shiro.CredentialsMatcher;
import cn.qumiandan.system.shiro.LoginFormAuthenticationFilter;
import cn.qumiandan.system.shiro.Md5SessionIdGenerator;
import cn.qumiandan.system.shiro.SessionControlFilter;
import cn.qumiandan.system.shiro.SessionManager;
import cn.qumiandan.system.shiro.ShiroRealm;
import cn.qumiandan.system.shiro.WebPermissionsAuthorizationFilter;

/**
 * shiro 配置
 * @author
 *
 */
@Configuration
public class ShiroConfig {
	
	@Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;
    
    /** token过期时间 (单位 秒)*/
    @Value("${spring.shiro.tokenTimeout}")
    private Long tokenTimeout;
    
    
    
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 没有登陆的用户只能访问登陆页面，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
       // shiroFilterFactoryBean.setLoginUrl("/common/unauth");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/auth/index");
        // 未授权界面;
        //shiroFilterFactoryBean.setUnauthorizedUrl("common/unauth1");

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        
        //限制同一帐号同时在线的个数。
        WebPermissionsAuthorizationFilter webPermissionsAuthorizationFilter = new WebPermissionsAuthorizationFilter();
        filtersMap.put(DefaultFilter.authc.toString(), new LoginFormAuthenticationFilter());
        filtersMap.put(DefaultFilter.perms.toString(), webPermissionsAuthorizationFilter);
        filtersMap.put(DefaultFilter.roles.toString(), webPermissionsAuthorizationFilter);
        filtersMap.put("kickout", kickoutSessionControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        /*// 权限控制map.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 公共请求
        
        filterChainDefinitionMap.put("/common/**", "anon");
        // 静态资源
        filterChainDefinitionMap.put("/static/**", "anon");
        // 登录方法
        filterChainDefinitionMap.put("/login/**", "anon"); // 表示可以匿名访问
        filterChainDefinitionMap.put("/verifyCode/**", "anon"); // 表示可以匿名访问
        filterChainDefinitionMap.put("/init", "authc,kickout,perms[/content/add]"); // 表示可以匿名访问
        //此处需要添加一个kickout，上面添加的自定义拦截器才能生效
        filterChainDefinitionMap.put("/admin/**", "authc,kickout");// 表示需要认证才可以访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);*/
        
        
        // 在IShiroLoadService接口中有动态读取权限
    	//String equal = " = ";
  		//String newline = "\n";
  		/*StringBuilder definitions = new StringBuilder();
  		if (CollectionUtils.isNotEmpty(filterList)) {
  			filterList.forEach(filter -> {
  				definitions.append(filter.getName()).append(equal).append(filter.getPerms()).append(newline);
  			});
  		}
  		Ini ini = new Ini();
  		ini.load(definitions.toString());
  		Ini.Section section = ini.getSection("urls");
  		if (org.apache.shiro.util.CollectionUtils.isEmpty(section)) {
  			section = ini.getSection("");
  		}*/
  		/*Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
  		if (CollectionUtils.isNotEmpty(filterList)) {
  			filterList.forEach(filter -> {
  				filterChainDefinitionMap.put(filter.getName(), filter.getPerms());
  			});
  		}
  		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
  		log.info("shiro拦截加载成功");*/
        return shiroFilterFactoryBean;
    }

    
    @Bean("securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 设置realm.
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean("shiroRealm")
    public ShiroRealm shiroRealm() {
        ShiroRealm myShiroRealm = new ShiroRealm();
        myShiroRealm.setCredentialsMatcher(credentialsMatcher());
        return myShiroRealm;
    }

    
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(RedisKeyPrefix.MUTUALWEB_SHIRO_CACHE);   //设置前缀
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(new Md5SessionIdGenerator());
        redisSessionDAO.setKeyPrefix(RedisKeyPrefix.MUTUALWEB_SHIRO_SESSION);
        return redisSessionDAO;
    }
    
    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public SessionManager sessionManager() {
        SimpleCookie simpleCookie = new SimpleCookie("token");
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(false);

        SessionManager sessionManager = new SessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdCookieEnabled(false);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookie(simpleCookie);
        sessionManager.setGlobalSessionTimeout(tokenTimeout);
        return sessionManager;
    }


    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost);
        redisManager.setPort(redisPort);
        redisManager.setTimeout(1800); //设置过期时间
        redisManager.setPassword(redisPassword);
        return redisManager;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    @Bean
    public SessionControlFilter kickoutSessionControlFilter() {
        SessionControlFilter kickoutSessionControlFilter = new SessionControlFilter();
        kickoutSessionControlFilter.setCache(cacheManager());
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl("/common/kickout");
        return kickoutSessionControlFilter;
    }


    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /***
     * 使授权注解起作用不如不想配置可以在pom文件中加入
     * <dependency>
     *<groupId>org.springframework.boot</groupId>
     *<artifactId>spring-boot-starter-aop</artifactId>
     *</dependency>
     * @param securityManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro生命周期处理器
     * 此方法需要用static作为修饰词，否则无法通过@Value()注解的方式获取配置文件的值
     *
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
