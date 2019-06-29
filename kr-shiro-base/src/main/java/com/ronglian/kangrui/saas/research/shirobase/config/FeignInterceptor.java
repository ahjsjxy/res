package com.ronglian.kangrui.saas.research.shirobase.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor{

    public void apply(RequestTemplate requestTemplate){
        Map<String, Collection<String> > oldHeaders = new HashMap<>( requestTemplate.headers() );
        
        ArrayList<String> tokenHeader = new ArrayList<>();
        log.info(ShiroService.getSessionId());
        tokenHeader.add(ShiroService.getSessionId());
        oldHeaders.put("Kr-D-Token", tokenHeader);
        
        requestTemplate.headers(oldHeaders);
    }
}