package com.ronglian.kangrui.saas.research.rbac.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ronglian.kangrui.saas.research.commonrbac.entity.LoginLog;
import com.ronglian.kangrui.saas.research.commonrbac.entity.Role;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.data.dao.LoginLogRepository;
import com.ronglian.kangrui.saas.research.rbac.data.dao.RoleRepository;
import com.ronglian.kangrui.saas.research.rbac.data.dao.UserRepository;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogBiz {
    
    @Autowired
    private LoginLogRepository logRepo;

    
    private LoginLog filter(LoginLog lg) {

        lg.setCreateTime(null);
        lg.setCreateUser(null);
        lg.setCreateUserName(null);
        lg.setUpdateTime(null);
        lg.setUpdateUser(null);
        lg.setUpdateUserName(null);
        lg.setParams(null);
        
        return lg;
    }
    
    
    public LoginLog appendUserLoginLog(String remoteIp) {
        User u = ShiroService.getCurrentUser();
        
        LoginLog lg = new LoginLog();
        ShiroService.setRecordCreationInfo(lg);
        
        lg.setLoginIp(remoteIp);
        lg.setLoginTime(new Date());
        lg.setUser(u);

        LoginLog saved = logRepo.save(lg);
        return filter(saved);
    }

}
