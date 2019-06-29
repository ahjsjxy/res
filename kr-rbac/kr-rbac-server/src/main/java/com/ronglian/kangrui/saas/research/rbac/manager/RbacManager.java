package com.ronglian.kangrui.saas.research.rbac.manager;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronglian.kangrui.saas.research.commonrbac.entity.Permission;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.biz.PermissionBiz;
import com.ronglian.kangrui.saas.research.rbac.biz.UserBiz;
import com.ronglian.kangrui.saas.research.shirobase.service.RbacServiceBase;

@Service
public class RbacManager implements RbacServiceBase {
    @Autowired
    private UserBiz userBiz;
    
    @Autowired
    private PermissionBiz permBiz;
    

    public User getUserByName(String name) {
        return userBiz.getUserByUsername(name);
    }


    @Override
    public List<Permission> getAllPermissions() {
        return permBiz.listAllPermissions();
    }
}
