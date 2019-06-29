package com.ronglian.kangrui.saas.research.shirobase.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ronglian.kangrui.saas.research.commonrbac.redis.RedisClient;
import com.ronglian.kangrui.saas.research.shirobase.redis.RedisCacheManager;
import com.ronglian.kangrui.saas.research.shirobase.redis.RedisSessionDao;
import com.ronglian.kangrui.saas.research.shirobase.redis.RedisSessionManager;
import com.ronglian.kangrui.saas.research.shirobase.service.RbacServiceBase;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfig {
    private Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Autowired
    private RbacServiceBase rbacService;
    
    @Bean ShiroExceptionFilter shiroExceptionFilter() {
        return new ShiroExceptionFilter();
    }

    @Bean
    public UserAuthRealm userAuthRealm() {
        return new UserAuthRealm();
    }
    /**
     * 配置 资源访问策略 . web应用程序 shiro核心过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean factoryBean(SessionsSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login");//登录页
        factoryBean.setSuccessUrl("/index");//首页
        factoryBean.setUnauthorizedUrl("/unauthorized");//未授权界面;
        factoryBean.setFilterChainDefinitionMap(setFilterChainDefinitionMap()); //配置 拦截过滤器链
        return factoryBean;
    }

    /**
     * 配置 SecurityManager,可配置一个或多个realm
     */
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        
        RedisCacheManager cacheManager = cacheManager();
        
        RedisSessionDao sessionDao = sessionDao();
        sessionDao.setCacheManager(cacheManager);
        
        RedisSessionManager sessionManager = sessionManager();
        sessionManager.setCacheManager(cacheManager);
        sessionManager.setSessionDAO(sessionDao);
        
        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(userAuthRealm());
//        securityManager.setRealm(xxxxRealm);
        return securityManager;
    }
    
    /**
     * 重要!!!
     * 此注解用以解决shiro annotation影响spring RequestMapping问题
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }


    /**
     * 开启shiro 注解支持. 使以下注解能够生效 :
     * 需要认证 {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}
     * 需要用户 {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}
     * 需要访客 {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}
     * 需要角色 {@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}
     * 需要权限 {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SessionsSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    

    /**
     * 配置 拦截过滤器链.  map的键 : 资源地址 ;  map的值 : 所有默认Shiro过滤器实例名
     * 默认Shiro过滤器实例 参考 : {@link org.apache.shiro.web.filter.mgt.DefaultFilter}
     */
    private Map<String, String> setFilterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<>();
        //注册 数据库中所有的权限 及其对应url
//        List<Permission> allPermission = rbacService.getAllPermissions();//数据库中查询所有权限
//        for (Permission p : allPermission) {
//            filterMap.put(p.getUrl(), "perms[" + p.getName() + "]");    //拦截器中注册所有的权限
//            //filterMap.put(p.getUrl(), "authc");    //拦截器中注册所有的权限
//        }
        filterMap.put("/static/**", "anon");    //公开访问的资源
        filterMap.put("/*/open/api/**", "anon");  //公开接口地址
        filterMap.put("/logout", "logout");     //配置登出页,shiro已经帮我们实现了跳转
        //filterMap.put("/**", "authc");          //所有资源都需要经过验证
        
        //开放swagger和所有url,仅开发
        filterMap.put("/swagger-ui.html#!/**", "anon");
        filterMap.put("/**", "anon");
        
        logger.info(filterMap.toString());
        
        return filterMap;
    }
    
    @Bean
    public RedisSessionManager sessionManager() {
        RedisSessionManager sessionManager = new RedisSessionManager();
        // -----可以添加session 创建、删除的监听器
        return sessionManager;
    }
    
    @Bean
    public RedisSessionDao sessionDao() {
        RedisSessionDao sessionDao = new RedisSessionDao();
        return sessionDao;
    }
    
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager();
        return cacheManager;
    }
    
    @Bean
    public RedisClient redisClient() {
        RedisClient redisClient = new RedisClient();
        return redisClient;
    }
}
