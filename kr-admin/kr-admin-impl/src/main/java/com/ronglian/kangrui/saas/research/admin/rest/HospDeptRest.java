package com.ronglian.kangrui.saas.research.admin.rest;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronglian.kangrui.saas.research.admin.biz.HospDeptBiz;
import com.ronglian.kangrui.saas.research.admin.manager.HospDeptManager;
import com.ronglian.kangrui.saas.research.admin.vo.user.HospDeptVo;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-24 16:29
 **/
@RestController
@RequestMapping("hosp/dept")
//@RequiresRoles("admin")
@Slf4j
public class HospDeptRest {
    @Autowired
    private HospDeptManager deptManager;
    
    @Autowired
    private HospDeptBiz deptBiz;

    @ApiOperation("机构所属科室列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ObjectRestResponse list(@RequestParam Long centerId,
                                   String name) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(deptManager.selectHospDeptByCenter(centerId,name));
    }
    
    @ApiOperation("全部科室列表")
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    public ObjectRestResponse listAll() {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(deptBiz.listHospDept());
    }
    
    @ApiOperation("机构未包括科室列表")
    @RequestMapping(value = "/listRest",method = RequestMethod.GET)
    public ObjectRestResponse listRest(@RequestParam Long centerId) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(deptManager.selectHospDeptNotInCenter(centerId));
    }
    
    @ApiOperation("添加科室")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestBody HospDeptVo hospCenter) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(deptBiz.addHospDept(hospCenter));
    }
    
    @ApiOperation("删除科室")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ObjectRestResponse delete(@RequestParam Long id) {
        deptManager.deleteHospDept(id);
        return new ObjectRestResponse().rel(Boolean.TRUE);
    }
    
    @ApiOperation("更新科室")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ObjectRestResponse update(@RequestBody HospDeptVo hospCenter) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(deptBiz.updateHospDept(hospCenter));
    }
    
    
    @ApiOperation("获取机构科室下用户")
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public ObjectRestResponse listUser(@RequestParam Long centerId, @RequestParam Long deptId) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(deptManager.listUser(centerId,deptId));
    }
    
    @ApiOperation("获取用户的机构科室")
    @RequestMapping(value = "/userCenterDept",method = RequestMethod.GET)
    public ObjectRestResponse listUser(@RequestParam Long userId) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(deptManager.getUserCenterDept(userId));
    }
    

}
