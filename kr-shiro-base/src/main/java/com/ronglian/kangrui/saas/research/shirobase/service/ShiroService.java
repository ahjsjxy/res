package com.ronglian.kangrui.saas.research.shirobase.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.stereotype.Component;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.commonrbac.entity.BaseEntity;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.commonrbac.utils.Constants;


@Component
public class ShiroService {
    public static User getCurrentUser() {
        try {
            return (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        }
        catch(Exception e) {
            throw new AuthorizationException("用户认证失败");
        }
    }
    
    public static String getSessionId() {
        return SecurityUtils.getSubject().getSession().getId().toString();
    }

    private static void clearUserSession(String username) {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        Collection<?> caches = sessionManager.getSessionDAO().getActiveSessions();//此处会获取所有Redis Cache
        if (caches == null) {
            return;
        }
        for (Object cached : caches) {
            if(cached instanceof Session) {
                Session session = (Session) cached;
                Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (obj instanceof SimplePrincipalCollection) {
                    User sessionUser = (User) ((SimplePrincipalCollection) obj).getPrimaryPrincipal();
                    if (sessionUser == null)
                        continue;
    
                    // 清除该用户以前登录时保存的session
                    if (username.equals(sessionUser.getUsername())) {
                        sessionManager.getSessionDAO().delete(session);
                    }
                }
            }
        }
    }
    
    public static Map<String, Object> executeLogin(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        clearUserSession(username);

        Map<String,Object> ret = new HashMap<>();
        try {
            subject.login(token);
            subject.getSession().setTimeout(Constants.DEFAULT_EXPIRE*1000);
            User u = getCurrentUser();
            if(u.getDeleted() == 1 || u.getAuditStatus() == 0 || u.getEnableFlag() == 0 ) {
                ret.put("code", RestCodeConstants.STATUS_CODE_FORBIDDEN);
                ret.put("msg", "登录用户未授权或已删除");
                return ret;
            }
            ret.put("token", subject.getSession().getId().toString());
            ret.put("ttl", subject.getSession().getTimeout());
            ret.put("adminFlag", u.getAdminFlag());
        }
        catch (AuthenticationException e){
            ret.put("code", RestCodeConstants.STATUS_CODE_FORBIDDEN);
            ret.put("msg", "用户不存在或者用户密码错误");
        }

        return ret;
    }

    public static void executeLogout(String username) {
        clearUserSession(username);
    }
    
    public static BaseEntity setRecordCreationInfo(BaseEntity e) {
        User u = getCurrentUser();
        
        e.setCreateTime(new Date());
        e.setCreateUser(u.getId());
        e.setCreateUserName(u.getUsername());
        
        return e;
    }
    
    public static BaseEntity setRecordUpdateInfo(BaseEntity e) {
        User u = getCurrentUser();
        
        e.setUpdateTime(new Date());
        e.setUpdateUser(u.getId());
        e.setUpdateUserName(u.getUsername());
        
        return e;
    }
}
