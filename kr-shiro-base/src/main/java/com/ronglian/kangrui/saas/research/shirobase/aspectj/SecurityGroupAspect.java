package com.ronglian.kangrui.saas.research.shirobase.aspectj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

/**
 * 数据过滤处理
 */
@Aspect
@Component
public class SecurityGroupAspect {
    private static Logger logger = LoggerFactory.getLogger(SecurityGroupAspect.class);
    
    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.ronglian.kangrui.saas.research.shirobase.annotation.SecurityGroupAnnotation)")
    public void securityGroupPointCut() {
        // 配置织入点
    }

    @Around("securityGroupPointCut()")
    public Object doAround(ProceedingJoinPoint jp) {
        return handleSecurityGroup(jp);
    }

    private Object handleSecurityGroup(ProceedingJoinPoint jp) {
        // 获取当前的用户
        User currentUser = ShiroService.getCurrentUser();
        if(ObjectUtils.isEmpty(currentUser)) {
            // TODO: 抛出异常
            logger.info("当前用户空！");
            return null;
        }
        else {
            // NOTE: 约定首参数为Map，在首参数中加入securityGroupIds
            // 在mybatis需要数据权限控制的相应mapper中，加入类似如下
            // Where ...
            // <if test="securityGroupIds != null and securityGroupIds != ''">
            //    AND TABLE_ALIAS.sg_id IN ( #{securityGroupIds} )
            // </if>

            Set<Long> securityGroupIds = new HashSet<>();
            currentUser.getSecurityGroup().forEach(sg->{
                    securityGroupIds.add(sg.getId());
                });

            logger.info("SecurityGroupIds: {}", securityGroupIds);
            String strSecurityGroupIds = securityGroupIds.toString();
            strSecurityGroupIds = strSecurityGroupIds.substring(1, strSecurityGroupIds.length()-1); //去除[]
            
            Object[] args = jp.getArgs();
            if(args[0] != null) {
                if(args[0] instanceof Map<?, ?>) {
                    ((Map<String, Object>) args[0]).put("securityGroupIds", strSecurityGroupIds);
                }
                else {
                    logger.warn("首参数不符合约定！请检查需要数据权限的方法！");
                }
            }
            else {
                Map<String, Object> params = new HashMap<>();
                params.put("securityGroupIds", strSecurityGroupIds);
                args[0] = params;
            }
            logger.info("替换参数,加入 SecurityGroupIds: {}", args[0].toString());
            
            try {
                return jp.proceed(args);
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
    }

}
