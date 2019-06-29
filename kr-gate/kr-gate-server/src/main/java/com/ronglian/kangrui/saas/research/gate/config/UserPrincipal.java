package com.ronglian.kangrui.saas.research.gate.config;

import javax.servlet.http.HttpServletRequest;

import com.ronglian.kangrui.saas.research.gate.ratelimit.config.IUserPrincipal;

/**
 * Created by ace on 2017/9/23.
 */


public class UserPrincipal implements IUserPrincipal {
    @Override
    public String getName(HttpServletRequest request) {
        //TODO get username from shiroService
        return null;
    }

}
