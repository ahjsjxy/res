package com.ronglian.kangrui.saas.research.sci.rest;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ButtonConstant;
import com.ronglian.kangrui.saas.research.sci.manager.CrfDictFieldManager;
import com.ronglian.kangrui.saas.research.sci.manager.FormConfigManager;
import com.ronglian.kangrui.saas.research.sci.manager.FormsManager;
import com.ronglian.kangrui.saas.research.sci.manager.StudyRoleButtonManager;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * crf form
 *
 * @author lanyan
 * @create 2019-03-05 11:32
 **/
@RestController
@RequestMapping("form")
@Slf4j
public class FormRest {

    @Autowired
    private FormsManager formsManager ;

    @Autowired
    private CrfDictFieldManager crfDictFieldManager ;

    @Autowired
    private FormConfigManager formConfigManager ;

    @Autowired
    private StudyRoleButtonManager studyRoleButtonManager;




    @ApiOperation("研究对象-设置显示字段")
    @RequestMapping(value = "getDisplayFieldList",method = RequestMethod.GET)
    public StudyTreeVo getDisplayFieldList(@RequestParam Long studyId){
        StudyTreeVo studyTreeVo = formsManager.getDisplayFieldList(studyId) ;
        return studyTreeVo ;
    }


    @ApiOperation("项目-校验存在未删除的CRF且还没创建表")
    @RequestMapping(value = "checkHasCrfFormGenerateToDb",method = RequestMethod.GET)
    public ObjectRestResponse checkHasCrfFormGenerateToDb(@RequestParam Long studyId) {
        if(studyId==null){
            log.info("studyId 为null=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        boolean flag = crfDictFieldManager.checkFormToFieldValidByStudyId(studyId) ;
        return new ObjectRestResponse().rel(flag)  ;
    }



    @ApiOperation("项目-创建Crf表单到DB")
    @RequestMapping(value = "saveCrfFormGenerateToDb",method = RequestMethod.POST)
    public ObjectRestResponse saveCrfFormGenerateToDb(@RequestParam Long studyId) {
        if(studyId==null){
            log.info("studyId 为null=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        Msg msg = formConfigManager.saveCrfFormGenerateToDb(studyId) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()) ;
    }




    @ApiOperation("Crf表单-校验存在未删除的字段")
    @RequestMapping(value = "checkHasFieldsGenerateToDb",method = RequestMethod.GET)
    public ObjectRestResponse checkHasFieldsGenerateToDb(@RequestParam Long crfFormId) {
        if(crfFormId==null){
            log.info("crfFormId 为null=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        boolean flag = crfDictFieldManager.checkFormToFieldValid(crfFormId) ;
        return new ObjectRestResponse().rel(flag)  ;
    }



    @ApiOperation("Crf表单-创建字段到DB")
    @RequestMapping(value = "saveFieldsGenerateToDb",method = RequestMethod.POST)
    public ObjectRestResponse saveFieldsGenerateToDb(@RequestParam Long crfFormId) {
        if(crfFormId==null){
            log.info("crfFormId 为null=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }
        Msg msg = formConfigManager.saveFieldsGenerateToDb(crfFormId) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()) ;
    }


    @ApiOperation("研究对象-保存设置显示字段")
    @RequestMapping(value = "saveDisplayFieldInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveDisplayFieldInfo(@RequestBody StudyTreeVo studyTreeVo) {
        int rowNum = formsManager.saveDisplayFieldInfo(studyTreeVo) ;
        return new ObjectRestResponse().rel(rowNum>0) ;
    }



    @ApiOperation("研究对象-添加展示字段")
    @RequestMapping(value = "getDisplayFormInfo",method = RequestMethod.GET)
    public ObjectRestResponse<FormsVo> getDisplayFormInfo(@RequestParam Long studyId,@RequestParam Long studyGroupId) {
        FormsVo formsVo = formsManager.getFormConfigAndDataInfo(studyId,studyGroupId) ;
        if(!studyRoleButtonManager.checkHasAuthority(ButtonConstant.STUDY_OBJECT_ADD,studyId))
        {
            log.info("当前用户没有添加研究对象的权限==========");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_OPERATOR_NO_AUTH);
        }
        return new ObjectRestResponse<>().data(formsVo) ;
    }



    @ApiOperation("研究对象-展示字段数据保存")
    @RequestMapping(value = "saveDisplayFormInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveDisplayFormInfo(@RequestBody FormConvertVo formConvertVo) {
        if(!studyRoleButtonManager.checkHasAuthority(ButtonConstant.STUDY_OBJECT_ADD,formConvertVo.getStudyId()))
        {
            log.info("当前用户没有添加研究对象的权限==========");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_OPERATOR_NO_AUTH);
        }
        boolean flag = formsManager.saveDisplayForm(formConvertVo.getFormsVo(), formConvertVo.getStudyId(), formConvertVo.getStudyGroupId(),formConvertVo.getFormsVo().getCenterId()) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("研究对象-点击单个Crf对应的题组树形")
    @RequestMapping(value = "getOneFormInfoTree",method = RequestMethod.GET)
    public CrfParentVo getOneFormInfoTree(@RequestParam Long crfFormId) {
        return formsManager.getOneFormInfoTree(crfFormId) ;
    }



    @ApiOperation("研究对象-点击单个Crf展示")
    @RequestMapping(value = "getOneFormInfo",method = RequestMethod.GET)
    public FormsVo getOneFormInfo(@RequestParam Long objectId,
                                  @RequestParam Long crfFormId,
                                  @RequestParam Long crfSecondId) {
        if (objectId==null || crfFormId==null || crfSecondId==null) {
            return new FormsVo() ;
        }

        return formsManager.getOneFormConfigAndDataInfo(objectId, crfFormId, crfSecondId) ;
    }



    @ApiOperation("研究对象-点击单个Crf保存")
    @RequestMapping(value = "saveOneFormInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveOneFormInfo(@RequestBody FormUpdateVo formUpdateVo) {
        Msg msg = formsManager.saveOneFormInfo(formUpdateVo.getFormsVo(), formUpdateVo.getObjectId(), formUpdateVo.getSaveFlag()) ;
        FormModifiyVo formModifiyVo = (msg.getData()!=null) ? (FormModifiyVo)msg.getData() : null ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(formModifiyVo) ;
    }



    @ApiOperation("研究对象-点击单个Crf修改状态")
    @RequestMapping(value = "saveFormAuditState",method = RequestMethod.POST)
    public ObjectRestResponse saveFormAuditState(@RequestBody FormUpdateVo formUpdateVo){
        boolean flag = formsManager.saveFormAuditState(formUpdateVo.getFormsVo(), formUpdateVo.getObjectId(), formUpdateVo.getSaveFlag());
        return new ObjectRestResponse().rel(flag) ;
    }

}
