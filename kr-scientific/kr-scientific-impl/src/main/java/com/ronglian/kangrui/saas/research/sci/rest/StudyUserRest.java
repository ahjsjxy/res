package com.ronglian.kangrui.saas.research.sci.rest;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.sci.manager.StudyUserManager;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserGroupCenterVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import com.ronglian.kangrui.saas.research.sci.vo.TreeEntity;
import com.ronglian.kangrui.saas.research.sci.vo.UserConvertVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:04
 **/
@RestController
@RequestMapping("studyUser")
@Slf4j
public class StudyUserRest {

    @Autowired
    private StudyUserManager studyUserManager ;


    @ApiOperation("项目或角色下-用户列表")
    @RequestMapping(value = "getUserListByObjectId",method = RequestMethod.GET)
    public List<StudyUserVo> getUserListByObjectId(@RequestParam Long objectId,
                                                   @RequestParam int level) {
        return studyUserManager.getUserListByObjectId(objectId, level) ;
    }



    @ApiOperation("项目或角色下-未选择用户")
    @RequestMapping(value = "getValidUserListByObjectId",method = RequestMethod.GET)
    public List<StudyUserVo> getValidUserListByObjectId(@RequestParam Long objectId,
                                                   @RequestParam int level){
        return studyUserManager.getValidUserListByObjectId(objectId, level) ;
    }



    @ApiOperation("项目或角色下-保存用户")
    @RequestMapping(value = "saveUserInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveUserInfo(@RequestBody UserConvertVo userConvertVo) {
        if (userConvertVo==null || userConvertVo.getObjectId()==null) {
            return new ObjectRestResponse().rel(Boolean.FALSE)  ;
        }

        // 保存用户
        boolean flag = studyUserManager.saveUserInfo(userConvertVo.getObjectId(),
                        userConvertVo.getLevel(), userConvertVo.getSelectUserList()) ;
        return new ObjectRestResponse().rel(flag)  ;
    }



    @ApiOperation("项目用户-展示勾选队列")
    @RequestMapping(value = "/getSelectedGroup",method = RequestMethod.GET)
    public ObjectRestResponse getSelectedGroup(@RequestParam Long studyId,
                                               @RequestParam Long userId
                                               ){
        if (studyId==null || userId==null) {
            log.info("studyId 或 userId 为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        List<TreeEntity> groupTree = studyUserManager.getSelectedGroup(studyId,userId);
        return new ObjectRestResponse().data(groupTree);
    }




    @ApiOperation("项目用户-展示勾选中心")
    @RequestMapping(value = "/getSelectedCenter",method = RequestMethod.GET)
    public ObjectRestResponse getSelectedCenter(@RequestParam Long studyId,
                                               @RequestParam Long userId){
        if (studyId==null || userId==null) {
            log.info("studyId 或 userId 为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        List<TreeEntity> groupTree = studyUserManager.getSelectedCenter(studyId,userId);
        return new ObjectRestResponse().data(groupTree);
    }




    @ApiOperation("项目用户-队列权限保存")
    @RequestMapping(value = "/saveSelectedGroup",method = RequestMethod.POST)
    public ObjectRestResponse saveSelectedGroup(@RequestBody StudyUserGroupCenterVo studyUserGroupCenterVo){
        Boolean flag = studyUserManager.saveSelectedGroup(studyUserGroupCenterVo);
        return new ObjectRestResponse().rel(flag);
    }


}
