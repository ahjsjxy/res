package com.ronglian.kangrui.saas.research.rbac.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.commonrbac.entity.Role;
import com.ronglian.kangrui.saas.research.rbac.biz.RoleBiz;
import com.ronglian.kangrui.saas.research.rbac.biz.UserBiz;
import com.ronglian.kangrui.saas.research.rbac.manager.UserRoleManager;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class RoleRestController {

    @Autowired
    RoleBiz roleBiz;
    
    @Autowired
    UserBiz userBiz;
    
    @Autowired
    UserRoleManager userRoleMgr;

    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getRole(@PathVariable("id") Long id) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(roleBiz.getRoleById(id, true));
    }

    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    public ObjectRestResponse listRole() {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(roleBiz.listRole());
    }

    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    public ObjectRestResponse addRole(@RequestBody Role role) {
        Role saved = roleBiz.addRole(role);
        return new ObjectRestResponse().rel(Boolean.TRUE).data(saved);
    }
    
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    public ObjectRestResponse updateRole(@RequestBody Role role) {
        Role updated = userRoleMgr.updateRole(role);
        return new ObjectRestResponse().rel(Boolean.TRUE).data(updated);
    }

    
    @RequestMapping(value = "/role/delete", method = RequestMethod.GET)
    public ObjectRestResponse deleteRole(@RequestParam Long roleId) {
        roleBiz.deleteRoleById(roleId);
        return new ObjectRestResponse().rel(Boolean.TRUE);
    }
    
    @RequestMapping(value = "/role/{id}/user/list", method = RequestMethod.GET)
    public ObjectRestResponse listUserByRole(@PathVariable("id") Long id) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(userBiz.listUserByRole(id));
    }

}
