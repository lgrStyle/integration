package com.demo.integration.login.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.integration.login.entity.Permission;
import com.demo.integration.login.entity.Role;
import com.demo.integration.login.entity.User;
import com.demo.integration.login.service.ILoginService;


public class LoginRealm extends AuthorizingRealm{

    private final static Logger logger = LoggerFactory.getLogger(LoginRealm.class);
    
    @Autowired
    private ILoginService loginService;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("-----授权验证-----");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        for(Role role : user.getRoles()) {
            authorizationInfo.addRole(role.getRole());
            for(Permission permission : role.getPermissions()) {
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("-----身份验证："+token.getPrincipal()+"-----");
        String username = (String) token.getPrincipal();
        User user = loginService.getUserInfo(username);
        if(user == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }

}
