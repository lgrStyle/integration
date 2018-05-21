package com.demo.integration.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");
        
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
}
