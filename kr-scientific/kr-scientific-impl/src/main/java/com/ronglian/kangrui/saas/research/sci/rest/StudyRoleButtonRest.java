package com.ronglian.kangrui.saas.research.sci.rest;

import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.sci.manager.StudyRoleButtonManager;
import com.ronglian.kangrui.saas.research.sci.vo.PortalButtonConvertVo;
import com.ronglian.kangrui.saas.research.sci.vo.PortalButtonVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 11:54
 **/
@RestController
@RequestMapping("roleButton")
@Slf4j
public class StudyRoleButtonRest {

    @Autowired
    private StudyRoleButtonManager studyRoleButtonManager ;


    @ApiOperation("角色-按钮列表")
    @RequestMapping(value = "/getButtonByRoleId",method = RequestMethod.GET)
    public List<PortalButtonVo> getButtonByRoleId(@RequestParam Long studyId, @RequestParam Long roleId) {
        return studyRoleButtonManager.getButtonByRoleId(studyId, roleId) ;
    }


    @ApiOperation("角色-按钮保存")
    @RequestMapping(value = "saveStudyRoleButtonInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyRoleButtonInfo(@RequestBody PortalButtonConvertVo portalButtonConvertVo) {
        boolean flag = studyRoleButtonManager.saveStudyRoleButtonInfo(portalButtonConvertVo.getRoleId(),
                        portalButtonConvertVo.getButtonId(), portalButtonConvertVo.isChecked()) ;
        return new ObjectRestResponse().rel(flag) ;
    }


}
