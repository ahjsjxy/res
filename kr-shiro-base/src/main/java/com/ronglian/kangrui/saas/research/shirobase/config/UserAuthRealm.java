package com.ronglian.kangrui.saas.research.shirobase.config;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.ronglian.kangrui.saas.research.commonrbac.entity.Permission;
import com.ronglian.kangrui.saas.research.commonrbac.entity.Role;
import com.ronglian.kangrui.saas.research.commonrbac.entity.SecurityGroup;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.shirobase.service.RbacServiceBase;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class UserAuthRealm extends AuthorizingRealm {

    @Autowired
    private RbacServiceBase rbacService;
    
    /**
     * 权限核心配置 根据数据库中的该用户 角色 和 权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        for (Role role : user.getRole()) {                                 //获取 角色
            log.info(role.getName());
            authorizationInfo.addRole(role.getName());                      //添加 角色
            for (Permission permission : role.getPermission()) {           //获取 权限
                log.info(permission.getName());
                authorizationInfo.addStringPermission(permission.getName());//添加 权限
            }
        }

        for (SecurityGroup sg : user.getSecurityGroup()) {
            log.info(sg.getName());
        }

        return authorizationInfo;
    }

    /**
     * 用户登陆 凭证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = rbacService.getUserByName(username);
        log.info(user.toString());
        if (user == null) return null;
        String credentials = user.getPasswordSalt() + user.getUsername() + user.getPasswordSalt();//自定义加盐 salt + username + salt
        

        // 后继Shiro会比对Info和Token中的密码
        return new SimpleAuthenticationInfo(
                user, //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(credentials), //加密
                getName()  //realm name
        );
    }
    
    /**
     * 设置 realm的 HashedCredentialsMatcher
     */
    @PostConstruct
    public void setHashedCredentialsMatcher() {
        this.setCredentialsMatcher(hashedCredentialsMatcher());
    }

    /**
     * 凭证匹配 : 指定 加密算法,散列次数
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }
}
