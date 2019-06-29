package com.ronglian.kangrui.saas.research.sci.rest;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.manager.StudyRoleManager;
import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleTreeVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-28 16:48
 **/
@RestController
@RequestMapping("studyRole")
@Slf4j
public class StudyRoleRest {

    @Autowired
    private StudyRoleManager studyRoleManager ;

    @ApiOperation("项目-角色列表")
    @RequestMapping(value = "/getStudyRoleList",method = RequestMethod.GET)
    public StudyRoleTreeVo getStudyRoleList(@RequestParam Long studyId, String roleName) {
        return studyRoleManager.getStudyRoleList(studyId, roleName) ;
    }


    @ApiOperation("项目-角色保存")
    @RequestMapping(value = "saveStudyRoleInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyRoleInfo(@RequestBody StudyRoleVo studyRoleVo) {
        studyRoleVo = studyRoleManager.saveStudyRoleInfo(studyRoleVo) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyRoleVo) ;
    }


    @ApiOperation("项目-角色编辑")
    @RequestMapping(value = "/getStudyRoleById",method = RequestMethod.GET)
    public ObjectRestResponse getStudyRoleById(@RequestParam Long roleId) {
        if(roleId==null){
            log.info("roleId 为null=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        StudyRoleVo studyRoleVo = studyRoleManager.getStudyRoleById(roleId) ;
        return new ObjectRestResponse().data(studyRoleVo) ;
    }


    @ApiOperation("角色-名称重复")
    @RequestMapping(value = "checkNameExists",method = RequestMethod.GET)
    public ObjectRestResponse checkNameExists(@RequestParam Long studyId, @RequestParam String name, Long roleId) {
        if(studyId==null ||  StringUtils.isBlank(name)){
            log.info("roleId 或 studyId 或 name 为null=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        boolean flag = studyRoleManager.checkNameExists(studyId, name, roleId) ;
        return new ObjectRestResponse().rel(flag) ;
    }


    @ApiOperation("角色-删除")
    @RequestMapping(value = "deleteRoleInfo",method = RequestMethod.POST)
    public ObjectRestResponse deleteRoleInfo(@RequestParam Long roleId) {
        if (roleId==null){
            log.info("roleId为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        Msg msg = studyRoleManager.deleteRoleInfo(roleId) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()).code(msg.getCode()) ;
    }


}
