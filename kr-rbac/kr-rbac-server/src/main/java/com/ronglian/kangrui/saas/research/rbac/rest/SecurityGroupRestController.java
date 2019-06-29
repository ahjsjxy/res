package com.ronglian.kangrui.saas.research.rbac.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.commonrbac.entity.SecurityGroup;
import com.ronglian.kangrui.saas.research.rbac.biz.SecurityGroupBiz;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class SecurityGroupRestController {

    @Autowired
    SecurityGroupBiz sgBiz;
    
    @RequestMapping(value = "/sg/{id}", method = RequestMethod.GET)
    public ObjectRestResponse getSecurityGroup(@PathVariable("id") Long id) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(sgBiz.getSecurityGroupById(id, true));
    }

    @RequestMapping(value = "/sg/list", method = RequestMethod.GET)
    public ObjectRestResponse listSecurityGroup() {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(sgBiz.listSecurityGroup());
    }

    @RequestMapping(value = "/sg/add", method = RequestMethod.POST)
    public ObjectRestResponse addSecurityGroup(@RequestBody SecurityGroup sg) {
        SecurityGroup saved = sgBiz.addSecurityGroup(sg);
        return new ObjectRestResponse().rel(Boolean.TRUE).data(saved);
    }
    
    @RequestMapping(value = "/sg/update", method = RequestMethod.POST)
    public ObjectRestResponse updateSecurityGroup(@RequestBody SecurityGroup sg) {
        SecurityGroup updated = sgBiz.updateSecurityGroup(sg);
        return new ObjectRestResponse().rel(Boolean.TRUE).data(updated);
    }

    
    @RequestMapping(value = "/sg/delete", method = RequestMethod.GET)
    public ObjectRestResponse deleteSecurityGroup(@RequestParam Long sgId) {
        sgBiz.deleteSecurityGroupById(sgId);
        return new ObjectRestResponse().rel(Boolean.TRUE);
    }

}
