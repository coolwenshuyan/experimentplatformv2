package com.coolwen.experimentplatformv2.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.coolwen.experimentplatformv2.filter.ResourceCheckFilter;
import com.coolwen.experimentplatformv2.permission.UrlPermissionResovler;
import com.coolwen.experimentplatformv2.realm.MyShiroCaseUrlRealm;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*Created by
 *
 * 该配置使用单点登陆配置
 * */


@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class ShiroCasConfiguration {
    private static final String casFilterUrlPattern = "/shiro-cas";
    @Value("${shiro.cas}")
    String casServerUrlPrefix;
    //
    @Value("${shiro.server}")
    String shiroServerUrlPrefix;
//    @Autowired
//    private Environment env;

    protected static final Logger logger = LoggerFactory.getLogger(ShiroCasConfiguration.class);

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public ResourceCheckFilter resourceCheckFilter() {
        ResourceCheckFilter resourceCheckFilter = new ResourceCheckFilter();
        resourceCheckFilter.setErrorUrl("405");
        return resourceCheckFilter;
    }

    //两个角色的Realm，student,admin
   /* @Bean
    public StudentCaseRealm studentCaseRealm() {
        logger.debug("StudentCaseRealm配置Bean");
        StudentCaseRealm casRealm = new StudentCaseRealm();
        casRealm.setPermissionResolver(urlPermissionResovler());
        logger.debug("StudentCaseRealm配置casServerUrlPrefix:" + casServerUrlPrefix);
        logger.debug("StudentCaseRealm配置cshiroServerUrlPrefix:" + shiroServerUrlPrefix);
        casRealm.setCasServerUrlPrefix(casServerUrlPrefix);
        casRealm.setCasService(shiroServerUrlPrefix + casFilterUrlPattern);
        casRealm.setDefaultRoles("ROLE_USER");
        return casRealm;
    }
*/
//   使用URL进行realm的

    @Bean
    public MyShiroCaseUrlRealm studentCaseRealm() {
        logger.debug("StudentCaseRealm配置Bean");
        MyShiroCaseUrlRealm casRealm = new MyShiroCaseUrlRealm();
        casRealm.setPermissionResolver(urlPermissionResovler());
        logger.debug("StudentCaseRealm配置casServerUrlPrefix:" + casServerUrlPrefix);
        logger.debug("StudentCaseRealm配置cshiroServerUrlPrefix:" + shiroServerUrlPrefix);
        casRealm.setCasServerUrlPrefix(casServerUrlPrefix);
        casRealm.setCasService(shiroServerUrlPrefix + casFilterUrlPattern);
        casRealm.setDefaultRoles("ROLE_USER");
        return casRealm;
    }

    @Bean
    public UrlPermissionResovler urlPermissionResovler() {
        UrlPermissionResovler urlPermissionResovler = new UrlPermissionResovler();
        return urlPermissionResovler;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        logger.debug("ShiroCasConfiguration环境配置开始");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(studentCaseRealm());
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setSubjectFactory(new CasSubjectFactory());
        return securityManager;
    }

    /**
     * 单独提出一个函数用于对shiro权限过滤的设置
     *
     * @param shiroFilterFactoryBean
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        logger.debug("loadShiroFilterChain拦截器工厂类注入开始");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put(casFilterUrlPattern, "casFilter");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/verifyCode", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/405", "anon");
        filterChainDefinitionMap.put("/logout", "anon");

        filterChainDefinitionMap.put("/newsinfo/newslist", "anon");//首页
        filterChainDefinitionMap.put("/setinfo/situation", "anon");//平台概况
        filterChainDefinitionMap.put("/teachers/frontList", "anon");//师资队伍
        filterChainDefinitionMap.put("/question/list", "anon");//咨询答疑
        filterChainDefinitionMap.put("/learning/learningList", "anon");//学习效果
        filterChainDefinitionMap.put("/learning/effectDetails/**", "anon");
        filterChainDefinitionMap.put("/setinfo/jiesao", "anon");
        filterChainDefinitionMap.put("/setinfo/aboutus", "anon");
        filterChainDefinitionMap.put("/newsinfo/more", "anon");
        filterChainDefinitionMap.put("/newsinfo/noticeDetails/**", "anon");
        filterChainDefinitionMap.put("/teachers/frontList", "anon");
//        filterChainDefinitionMap.put("/teachers/frontList", "authc");

        filterChainDefinitionMap.put("/choose/course/list", "authc");//实验大厅
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/kuangjia/shiyan", "authc");
        //        filterChainDefinitionMap.put("/question/add", "authc,perms[student]");
//        filterChainDefinitionMap.put("/newsinfo/shiyan", "authc");

        //需要登录访问的资源
        filterChainDefinitionMap.put("/newsinfo/shiyan", "authc");

        //需要资源检查才能访问
        filterChainDefinitionMap.put("/learning/kuangjia", "authc,resourceCheckFilter");
        filterChainDefinitionMap.put("/**", "authc");//需要登录访问的资源 , 一般将/**放在最下边
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/405");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }
    /*
     * CAS Filter
     */


    @Bean(name = "casFilter")
    public CasFilter getCasFilter(@Value("${shiro.cas}") String casServerUrlPrefix,
                                  @Value("${shiro.server}") String shiroServerUrlPrefix) {
        logger.debug("casFilter拦截器工厂类注入开始");
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        String loginUrl = casServerUrlPrefix + "/login?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
        casFilter.setFailureUrl(loginUrl);
        casFilter.setSuccessUrl("/index");
        return casFilter;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager,
                                                            CasFilter casFilter,
                                                            @Value("${shiro.cas}") String casServerUrlPrefix,
                                                            @Value("${shiro.server}") String shiroServerUrlPrefix) {
        logger.debug("shiroFilter拦截器工厂类注入开始");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        String loginUrl = casServerUrlPrefix + "/login?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        Map<String, Filter> filters = new HashMap<>();
        filters.put("casFilter", casFilter);
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl(casServerUrlPrefix + "/logout?service=" + shiroServerUrlPrefix);
        filters.put("logout", logoutFilter);
        shiroFilterFactoryBean.setFilters(filters);

        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }
}
