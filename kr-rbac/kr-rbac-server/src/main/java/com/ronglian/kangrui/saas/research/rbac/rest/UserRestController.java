package com.ronglian.kangrui.saas.research.rbac.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.biz.RoleBiz;
import com.ronglian.kangrui.saas.research.rbac.biz.SecurityGroupBiz;
import com.ronglian.kangrui.saas.research.rbac.biz.UserBiz;
import com.ronglian.kangrui.saas.research.rbac.manager.UserRoleManager;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class UserRestController {

    @Autowired
    UserBiz userBiz;
    
    @Autowired
    RoleBiz roleBiz;
    
    @Autowired
    SecurityGroupBiz sgBiz;
    
    @Autowired
    UserRoleManager userRoleMgr;

    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    public ObjectRestResponse getUserInfoByName(@PathVariable("name") String name) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(userBiz.getUserInfoByUsername(name));
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ObjectRestResponse listUserInfo(String keyword) {
        List<User> users = null;
        if(StringUtils.isNotBlank(keyword))
            users = userBiz.searchUserInfo(keyword);
        else
            users = userBiz.listUserInfo();
        
        Map<String,Object> ret = new HashMap<>();
        ret.put("count", users.size());
        ret.put("users", users);
        
        return new ObjectRestResponse().rel(Boolean.TRUE).data(ret);
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ObjectRestResponse addUser(@RequestBody User user) {
        User saved = userRoleMgr.addOrUpdateUser(user);
        return new ObjectRestResponse().rel(Boolean.TRUE).data(saved);
    }
    
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ObjectRestResponse updateUser(@RequestBody User user) {
        User updated = userRoleMgr.addOrUpdateUser(user);
        return new ObjectRestResponse().rel(Boolean.TRUE).data(updated);
    }

    
    @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
    public ObjectRestResponse deleteUser(@RequestParam Long userId) {
        userBiz.deleteUserById(userId);
        return new ObjectRestResponse().rel(Boolean.TRUE);
    }
    
    @RequestMapping(value = "/user/{id}/role/list", method = RequestMethod.GET)
    public ObjectRestResponse listRoleByUser(@PathVariable("id") Long id) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(roleBiz.listRoleByUser(id));
    }

    @RequestMapping(value = "/user/{id}/sg/list", method = RequestMethod.GET)
    public ObjectRestResponse listSecurityGroupByUser(@PathVariable("id") Long id) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(sgBiz.listSecurityGroupByUser(id));
    }
}
