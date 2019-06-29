package com.ronglian.kangrui.saas.research.sci.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.sci.manager.StudyObjectManager;
import com.ronglian.kangrui.saas.research.sci.vo.HeaderVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyObjectButtonVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyObjectVo;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-08 17:31
 **/
@RestController
@RequestMapping("studyObject")
@Slf4j
public class StudyObjectRest {

    @Autowired
    private StudyObjectManager studyObjectManager ;


    @ApiOperation("项目的队列列表")
    @RequestMapping(value = "/getStudyGroupList",method = RequestMethod.GET)
    public List<StudyGroup> getStudyGroupList(@RequestParam Long studyId,
                                              @RequestParam Long centerId) {
        List<Long> centerIds = studyObjectManager.selectCenterByStudyAndUser(studyId);
        //centerId 为0表示中心为所有，选出所有的队列
        if(centerId.equals(0L)&& centerIds.size()==0){
            return new ArrayList<>();
        }
        return studyObjectManager.getStudyGroupList(studyId, centerId, centerIds) ;
    }



    @ApiOperation("研究对象列头")
    @RequestMapping(value = "getHeaderList",method = RequestMethod.GET)
    public List<HeaderVo> getHeaderList(@RequestParam Long studyId) {
        return studyObjectManager.getHeaderList(studyId) ;
    }



    @ApiOperation("研究对象列表")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<StudyObjectVo> pageList(@RequestParam(defaultValue = "10") int limit,
                                                       @RequestParam(defaultValue = "1") int page,
                                                       @RequestParam Long studyId,
                                                       @RequestParam Long centerId,
                                                       @RequestParam Long studyGroupId){
        List<Long> centerIds = studyObjectManager.selectCenterByStudyAndUser(studyId);
        if(centerIds.size()==0){
            return new TableResultResponse<StudyObjectVo>(0L,new ArrayList<>());
        }
        Page<StudyObjectVo> result = PageHelper.startPage(page, limit,true);
        List<StudyObjectVo> studyObjectVoList = studyObjectManager.queryStudyObjectList(studyId, centerId, studyGroupId, centerIds) ;
        return new TableResultResponse<StudyObjectVo>(result.getTotal(), studyObjectVoList);
    }



    @ApiOperation("研究对象删除")
    @RequestMapping(value = "delStudyObjectForm",method = RequestMethod.POST)
    public ObjectRestResponse delStudyObjectForm(@RequestParam String objectIdStr){
        if(StringUtils.isBlank(objectIdStr)){
            log.debug("objectIdStr为null ===================== ");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        Msg msg = studyObjectManager.delStudyObjectForm(objectIdStr) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()).code(msg.getCode()) ;
    }



    @ApiOperation("研究对象-(添加删除设置)按钮权限")
    @RequestMapping(value = "/studyObjectButtonAuthority",method = RequestMethod.GET)
    public StudyObjectButtonVo studyObjectButtonAuthority(@RequestParam Long studyId){
        return studyObjectManager.studyObjectButtonAuthority(studyId);
    }



    @ApiOperation("获取中心列表及默认值")
    @RequestMapping(value = "/centerList",method = RequestMethod.GET)
    public ObjectRestResponse centerList(@RequestParam Long studyId){
        if(studyId==null){
            log.info("studyId 为null=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        return studyObjectManager.getCenterList(studyId);
    }


}
