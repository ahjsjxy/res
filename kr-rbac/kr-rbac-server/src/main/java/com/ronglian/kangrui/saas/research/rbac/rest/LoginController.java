package com.ronglian.kangrui.saas.research.rbac.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.util.ClientUtil;
import com.ronglian.kangrui.saas.research.rbac.biz.LogBiz;
import com.ronglian.kangrui.saas.research.rbac.biz.UserBiz;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;


@RestController
public class LoginController {
    
    @Autowired
    HttpServletRequest request;
    
    @Autowired
    UserBiz userBiz;
    
    @Autowired
    LogBiz logBiz;

    @RequestMapping(value = "/open/api/login",method = RequestMethod.POST)
    public ObjectRestResponse login(@RequestBody Map<String, String> params) {
        if (params.containsKey("username") && params.containsKey("password")) {
            String username = params.get("username");
            String password = params.get("password");
            Integer adminFlag = 0;
            if(params.containsKey("adminFlag"))
                adminFlag = Integer.valueOf(params.get("adminFlag"));
            
            Map<String,Object> map = ShiroService.executeLogin(username, password);
            if(map.containsKey("code")){
                return new ObjectRestResponse().rel(Boolean.FALSE).data(map);
            }

            if(adminFlag == 1 && (Integer)map.get("adminFlag") != 1) {
                map.clear();
                map.put("code", RestCodeConstants.STATUS_CODE_UNAUTHORIZED);
                map.put("msg", "用户未授权");
                return new ObjectRestResponse().rel(Boolean.FALSE).data(map);
            }
            
            logBiz.appendUserLoginLog(ClientUtil.getClientIp(request));
            return new ObjectRestResponse().rel(Boolean.TRUE).data(map);
        } else {
            return new ObjectRestResponse().rel(Boolean.FALSE);
        }
    }
    
    @RequestMapping(value = "/open/api/logout",method = RequestMethod.GET)
    public ObjectRestResponse logout() {
        String currentUser = ShiroService.getCurrentUser().getUsername();
        ShiroService.executeLogout(currentUser);

        return new ObjectRestResponse().rel(Boolean.TRUE);
    }
    
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public ObjectRestResponse getUserInfo() {
        String currentUser = ShiroService.getCurrentUser().getUsername();
        return new ObjectRestResponse().rel(Boolean.TRUE).data(userBiz.getUserInfoByUsername(currentUser));
    }
}
