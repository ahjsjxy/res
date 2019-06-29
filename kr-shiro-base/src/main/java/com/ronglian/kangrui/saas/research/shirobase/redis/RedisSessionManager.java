package com.ronglian.kangrui.saas.research.shirobase.redis;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;


public class RedisSessionManager extends DefaultWebSessionManager {


    @Override
    protected Serializable getSessionId(ServletRequest httpRequest, ServletResponse response) {
        HttpServletRequest request = (HttpServletRequest) httpRequest;
        return request.getHeader("Kr-D-Token");
    }
}
