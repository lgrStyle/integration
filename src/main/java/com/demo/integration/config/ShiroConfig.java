package com.demo.integration.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.integration.login.realm.LoginRealm;

@Configuration
public class ShiroConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager sm) {
        logger.info("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugin/**", "anon");
        filterChainDefinitionMap.put("/image/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/userAdd", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/login", "authc");
        filterChainDefinitionMap.put("/**", "user");
        
        shiroFilterFactoryBean.setSecurityManager(sm);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(loginRealm());
        sm.setRememberMeManager(rememberMeManager());
        return sm;
    }
    
    @Bean
    public LoginRealm loginRealm() {
        LoginRealm realm = new LoginRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }
    
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    } 
    
    @Bean
    public SecureRandomNumberGenerator secureRandomNumberGenerator() {
        return new SecureRandomNumberGenerator(); 
    }
    
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
    
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(2592000);
        cookie.setHttpOnly(true);
        return cookie;
    }
    
    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }
    
}
