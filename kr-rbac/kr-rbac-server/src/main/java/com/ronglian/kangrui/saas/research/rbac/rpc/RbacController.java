package com.ronglian.kangrui.saas.research.rbac.rpc;


import com.ronglian.kangrui.saas.research.commonrbac.entity.Permission;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.biz.PermissionBiz;
import com.ronglian.kangrui.saas.research.rbac.biz.UserBiz;
import com.ronglian.kangrui.saas.research.rbac.manager.RbacManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RbacController {
    
    @Autowired
    RbacManager rbacMgr;
    
    @Autowired
    UserBiz userBiz;

    @Autowired
    PermissionBiz permissionBiz;
    
    @RequestMapping(value="/inner/user/{name}", method= RequestMethod.GET)
    public User getUserInfoByName(@PathVariable("name") String name) {
        return userBiz.getUserByUsername(name);
    }
    
    @RequestMapping(value="/inner/user/list", method= RequestMethod.POST)
    public List<Map<String, String> > listUserByIds(@RequestBody List<Long> userIds) {
        return userBiz.listUserByIds(userIds);
    }
    
    @RequestMapping(value="/inner/permissions", method= RequestMethod.GET)
    public List<Permission> getAllPermission() {
        return permissionBiz.listAllPermissions();
    }
    

}
