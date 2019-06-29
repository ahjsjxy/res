package com.ronglian.kangrui.saas.research.shirobase.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.web.servlet.OncePerRequestFilter;

public class ShiroExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        try{
            super.doFilter(request, response, chain);
        }
        catch(Exception e) {
            // 从Shiroe抛出的异常约定为org.apache.shiro.authz.AuthorizationException
            if(e.getCause() instanceof AuthorizationException) {
                HttpServletResponse rsp = (HttpServletResponse)response;
                rsp.sendError(403,e.getCause().getMessage());
                return;
            }
            else {
                throw e;
            }
        }
    }
}
