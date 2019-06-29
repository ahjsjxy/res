package com.ronglian.kangrui.saas.research.admin.rest;


import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronglian.kangrui.saas.research.admin.biz.HospCenterBiz;
import com.ronglian.kangrui.saas.research.admin.biz.HospCenterDeptBiz;
import com.ronglian.kangrui.saas.research.admin.entity.HospCenterDept;
import com.ronglian.kangrui.saas.research.admin.manager.HospCenterManager;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-24 16:25
 **/
@RestController
@RequestMapping("hosp/center")
//@RequiresRoles("admin")
@Slf4j
public class HospCenterRest {
    @Autowired
    private HospCenterManager centerManager;
    
    @Autowired
    private HospCenterBiz centerBiz;
    
    @Autowired
    private HospCenterDeptBiz centerDeptBiz;

    @ApiOperation("医疗机构列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ObjectRestResponse list(String name) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(centerBiz.selectHospCenter(name));
    }
    
    @ApiOperation("添加医疗机构")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestBody HospCenterVo hospCenter) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(centerBiz.addHospCenter(hospCenter));
    }
    
    @ApiOperation("删除医疗机构")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ObjectRestResponse delete(@RequestParam Long id) {
        centerManager.deleteHospCenter(id);
        return new ObjectRestResponse().rel(Boolean.TRUE);
    }
    
    @ApiOperation("更新医疗机构")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ObjectRestResponse update(@RequestBody HospCenterVo hospCenter) {
        return new ObjectRestResponse().rel(Boolean.TRUE).data(centerBiz.updateHospCenter(hospCenter));
    }
    
//    @ApiOperation("医疗机构新增科室")
//    @RequestMapping(value = "/addDept",method = RequestMethod.POST)
//    public ObjectRestResponse addDept(@RequestBody HospCenterDept centerDept) {
//        hospCenterDeptBiz.addDeptToCenter(centerDept);
//        return new ObjectRestResponse().rel(Boolean.TRUE);
//    }
    
    @ApiOperation("医疗机构批量新增科室")
    @RequestMapping(value = "/addDeptList",method = RequestMethod.POST)
    public ObjectRestResponse addDept(@RequestBody List<HospCenterDept> centerDepts) {
        centerDeptBiz.addDeptListToCenter(centerDepts);
        return new ObjectRestResponse().rel(Boolean.TRUE);
    }
    
    @ApiOperation("医疗机构删除科室")
    @RequestMapping(value = "/removeDept",method = RequestMethod.POST)
    public ObjectRestResponse removeDept(@RequestBody HospCenterDept centerDept) {
        centerManager.deleteDeptFromCenter(centerDept);
        return new ObjectRestResponse().rel(Boolean.TRUE);
    }
}
