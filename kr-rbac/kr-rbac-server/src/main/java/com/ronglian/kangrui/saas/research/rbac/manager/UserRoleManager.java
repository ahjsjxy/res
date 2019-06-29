package com.ronglian.kangrui.saas.research.rbac.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronglian.kangrui.saas.research.commonrbac.entity.Role;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.biz.RoleBiz;
import com.ronglian.kangrui.saas.research.rbac.biz.UserBiz;

@Service
public class UserRoleManager {
    @Autowired
    private UserBiz userBiz;
    
    @Autowired
    private RoleBiz roleBiz;
    
    @Autowired
    private HospManager hospMgr;
    
    @Transactional
    public Role updateRole(Role role) {
        
        Role updated = roleBiz.updateRoleInfo(role);
        
        userBiz.clearOneRoleFromUsers(role);
        userBiz.addOneRoleToUsers(role);
        
        return updated;
    }
    
    @Transactional
    public Role addRole(Role role) {
        
        Role added = roleBiz.addRole(role);
        
        userBiz.addOneRoleToUsers(role);
        
        return added;
    }
    
    //@Transactional
    public User addOrUpdateUser(User user) {
        Long centerId = user.getCenterId();
        Long deptId = user.getDeptId();
        User savedOrUpdated = null;
        if(user.getId() == null)
            savedOrUpdated = userBiz.addUser(user);
        else 
            savedOrUpdated = userBiz.updateUser(user);
        
        if(centerId != null && deptId != null)
            hospMgr.addUserToDept(centerId, deptId, savedOrUpdated.getId());
        
        return savedOrUpdated;
    }

}
