package com.ronglian.kangrui.saas.research.sci.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ButtonConstant;
import com.ronglian.kangrui.saas.research.sci.manager.StudyGroupManager;
import com.ronglian.kangrui.saas.research.sci.manager.StudyManager;
import com.ronglian.kangrui.saas.research.sci.manager.StudyRoleButtonManager;
import com.ronglian.kangrui.saas.research.sci.vo.StudyAddVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目列表
 *
 * @author lanyan
 * @create 2019-03-05 11:31
 **/
@RestController
@RequestMapping("study")
@Slf4j
public class StudyRest{

    @Autowired
    private StudyManager studyManager ;

    @Autowired
    private StudyGroupManager studyGroupManager ;

    @Autowired
    private StudyRoleButtonManager studyRoleButtonManager;



    @ApiOperation("项目列表")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<StudyVo> pageList(@RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 StudyVo studyVo){
        // 查询
        Page<StudyVo> result = PageHelper.startPage(page, limit,true);
        List<StudyVo> studyVoList = studyManager.getStudyList(studyVo) ;
        return new TableResultResponse<StudyVo>(result.getTotal(), studyVoList);
    }



    @ApiOperation("删除项目")
    @RequestMapping(value = "/deleteStudyById",method = RequestMethod.POST)
    public ObjectRestResponse deleteStudyById(@RequestParam String studyIdStr) {
        if (StringUtils.isBlank(studyIdStr)) {
            log.info("studyIdStr为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        if(!studyRoleButtonManager.checkHasAuthority(ButtonConstant.STUDY_DELETE,Long.parseLong(studyIdStr)))
        {
            log.info("当前用户没有删除该项目的权限==========");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_OPERATOR_NO_AUTH);
        }

        Msg msg = studyManager.deleteStudyById(studyIdStr.trim()) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()).code(msg.getCode()) ;
    }



    @ApiOperation("项目-第一步保存")
    @RequestMapping(value = "/saveStudyStep1",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyStep1(@RequestBody StudyAddVo studyAddVo) {
        studyAddVo = studyManager.saveStudyStep1(studyAddVo) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyAddVo) ;
    }




    @ApiOperation("项目-第二步保存")
    @RequestMapping(value = "/saveStudyStep2",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyStep2(@RequestBody StudyAddVo studyAddVo) {
        studyAddVo = studyManager.saveStudyStep2(studyAddVo) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyAddVo) ;
    }




    @ApiOperation("查询项目详情根据项目ID")
    @RequestMapping(value = "/getStudyInfosByStudyId",method = RequestMethod.GET)
    public ObjectRestResponse getStudyInfosByStudyId(@RequestParam Long studyId) {
        StudyAddVo studyAddVo = studyManager.getStudyGroupByStudyId(studyId) ;
        return new ObjectRestResponse().data(studyAddVo) ;
    }



    @ApiOperation("校验项目名称重复")
    @RequestMapping(value = "checkStudyNameExists",method = RequestMethod.GET)
    public ObjectRestResponse checkStudyNameExists(@RequestParam String name,
                                                   @RequestParam(defaultValue = "0") Integer studyFlag,
                                                   Long studyId) {
        if (StringUtils.isBlank(name) || studyFlag==null) {
            log.info("name为空或studyFlag为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_PARAM_NULL) ;
        }

        boolean flag = studyManager.checkStudyNameExists(name.trim(), studyFlag, studyId) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("校验队列名称重复")
    @RequestMapping(value = "checkStudyGroupNameExists",method = RequestMethod.GET)
    public ObjectRestResponse checkStudyGroupNameExists(
                                                        @RequestParam Long studyId,
                                                        @RequestParam String name,
                                                        Long studyGroupId) {
        if (StringUtils.isBlank(name) || studyId==null) {
            log.info("name为空或studyId为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_PARAM_NULL) ;
        }

        boolean flag = studyGroupManager.checkStudyGroupNameExists(name.trim(), studyId, studyGroupId) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("删除队列")
    @RequestMapping(value = "deleteStudyGroupInfo",method = RequestMethod.POST)
    public ObjectRestResponse deleteStudyGroupInfo(@RequestParam Long studyGroupId) {
        if (studyGroupId == null) {
            log.info("studyGroupId为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }

        Msg msg = studyGroupManager.deleteStudyGroupInfo(studyGroupId) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()).code(msg.getCode()) ;
    }



    @ApiOperation("项目-判断是否有研究对象")
    @RequestMapping(value = "checkHasStudyObjectData",method = RequestMethod.GET)
    public ObjectRestResponse checkHasStudyObjectData(@RequestParam Long studyId) {
        if (studyId==null) {
            log.info("studyId为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }

        boolean flag = studyManager.checkHasStudyObjectData(studyId) ;
        return new ObjectRestResponse().rel(flag)  ;
    }



    @ApiOperation("项目-第三步保存")
    @RequestMapping(value = "/saveStudyStep3",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyStep3(@RequestParam Long studyId) {
        StudyVo studyVo = studyManager.saveStudyStep3(studyId) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyVo) ;
    }


    @ApiOperation("项目-已选择的中心")
    @RequestMapping(value = "getSelectedCenterList",method = RequestMethod.GET)
    public List<HospCenterVo> getSelectedCenterList(@RequestParam Long studyId) {
        return studyManager.getSelectedCenterList(studyId) ;
    }


    @ApiOperation("项目-未选择的中心")
    @RequestMapping(value = "getValidCenterList",method = RequestMethod.GET)
    public List<HospCenterVo> getValidCenterList(@RequestParam Long studyId) {
        return studyManager.getValidCenterList(studyId) ;
    }


    @ApiOperation("项目-第四步保存")
    @RequestMapping(value = "/saveStudyStep4",method = RequestMethod.POST)
    public ObjectRestResponse saveStudyStep4(@RequestBody StudyAddVo studyAddVo) {
        studyAddVo = studyManager.saveStudyStep4(studyAddVo) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(studyAddVo) ;
    }

}
