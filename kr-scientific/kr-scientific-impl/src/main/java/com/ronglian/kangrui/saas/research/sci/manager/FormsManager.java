package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.admin.api.IHospCenterService;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.biz.StudyUserGroupCenterBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ButtonConstant;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUserGroupCenter;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyMapper;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * crf form处理
 *
 * @author lanyan
 * @create 2019-03-05 11:33
 **/
@Service
@Slf4j
public class FormsManager {

    @Autowired
    private FormDataManager formDataManager ;

    @Autowired
    private StudyObjectManager studyObjectManager ;

    @Autowired
    private FormConfigManager formConfigManager ;

    @Autowired
    private StudyMapper studyMapper;

    @Autowired
    private CrfManager         crfManager ;

    @Autowired
    private IHospCenterService iHospCenterService;

    @Autowired
    private StudyUserGroupCenterBiz studyUserGroupCenterBiz;

    @Autowired
    private CrfDictFieldManager crfDictFieldManager;


    /**
     * 根据study id 查询项目的crf配置
     * @param studyId
     * @return
     */
    public StudyTreeVo getDisplayFieldList(Long studyId) {
        return formConfigManager.getDisplayFieldList(studyId) ;
    }


    /**
     * 保存设置显示字段
     * @param studyTreeVo
     * @return
     */
    public int saveDisplayFieldInfo(StudyTreeVo studyTreeVo) {
        return formConfigManager.saveDisplayFieldInfo(studyTreeVo) ;
    }



    /**
     * 获取动态form的结构及数据
     * @param studyId
     * @return
     */
    public FormsVo getFormConfigAndDataInfo(Long studyId, Long studyGroupId) {
        FormsVo formsVo = new FormsVo() ;

        //查询form config节点
        List<FormConfigVo> formConfigVoList = formConfigManager.getDisplayFormConfigVoList(studyId) ;
        formsVo.setFormConfigs(formConfigVoList);

        //查询form data
        FormDataVo formData = formDataManager.getFormData(formConfigVoList, null);
        formsVo.setData(formData);

        //查询中心及下拉,默认中心
        Long userId = ShiroService.getCurrentUser().getId();
        List<HospCenterVo> centerList = iHospCenterService.getHospCenterList(null); //全部中心

        //获取勾选的中心权限
        List<StudyUserGroupCenter> studyUserGroupCenterList = studyUserGroupCenterBiz.selectCenterByStudyAndUser(studyId, userId, studyGroupId);
        List<Long> centerIds = studyUserGroupCenterList.stream().map(item->item.getCenterId()).collect(Collectors.toList());
        centerList = centerList.stream().filter(each->centerIds.contains(each.getId())).collect(Collectors.toList());

        formsVo.setCenterList(centerList);
        HospCenterVo center =iHospCenterService.selectDefaultCenter(userId);
        Long centerId = center!=null?iHospCenterService.selectDefaultCenter(userId).getId():-1L;
        if(center!=null && !centerIds.contains(center.getId())){
            centerId=-1L;
        }
        formsVo.setCenterId(centerId);
        return formsVo ;
    }





    /**
     * 保存-设置显示列的那几个字段
     * @param formsVo
     * @return
     */
    public boolean saveDisplayForm(FormsVo formsVo, Long studyId, Long studyGroupId,Long centerId) {
        return formDataManager.saveDisplayFormData(formsVo, studyId, studyGroupId, centerId) ;
    }



    /**
     * 根据study object id和 form id 查询某个单节点的form的配置和数据
     * @param objectId
     * @param crfFormId
     * @return
     */
    public FormsVo getOneFormConfigAndDataInfo(Long objectId, Long crfFormId, Long crfSecondId) {
        FormsVo formsVo = new FormsVo() ;
        formsVo.setObjectId(objectId);

        // 查询CRF以及所有字段都已经创建列
        boolean checkCrfConfigValid = crfDictFieldManager.checkCrfConfigValid(crfFormId) ;
        if (checkCrfConfigValid) {

            //查询form config节点
            List<FormConfigVo> formConfigVoList = formConfigManager.getOneFormConfig(objectId, crfFormId, crfSecondId) ;
            formsVo.setFormConfigs(formConfigVoList);

            List<FormConfigVo> tempList = formConfigManager.getOneFormConfig(objectId, crfFormId, null) ;

            //查询form data
            FormDataVo formData = formDataManager.getFormData(tempList, objectId);
            formsVo.setData(formData);

            // 设置某一研究对象针对某一个表单，查询状态
            Crf crf =  crfManager.selectById(crfFormId) ;
            StatusVo statusVo = formDataManager.selectStatus(crf.getTableName(),objectId);
            Integer status = (statusVo!=null) ? statusVo.getStatus() : ResearchConsts.COLLECT_FLAG_WRITING ;
            formsVo.setStatus(status);

        } else {
            formsVo.setStatus(ResearchConsts.COLLECT_FLAG_WRITING) ;
        }

        // 设置某一研究对象针对某一个表单，点击进去5个按钮的状态
        this.setButtonFlag(formsVo, objectId);


        return formsVo ;

    }




    /**
     * 设置某一研究对象针对某一个表单，点击进去5个按钮的状态
     * @param formsVo
     * @param objectId
     * @return
     */
    public FormsVo setButtonFlag(FormsVo formsVo, Long objectId){
        //根据studyId和用户id获得该用户在该项目下的按钮权限
        StudyObject studyObject = studyObjectManager.selectStudyObject(objectId);
        StudyVo studyVo = new StudyVo();
        studyVo.setUserId(ShiroService.getCurrentUser().getId());
        studyVo.setId(studyObject.getStudyId());
        List<ButtonCodeVo> list = studyMapper.getButtonListByStudyAndUserId(studyVo);
        List<String> buttons = list.stream().map(buttonCodeVo -> buttonCodeVo.getCode()).collect(Collectors.toList());

        //根据不同的状态未不同的按钮操作权限赋值
        formsVo.setSaveEnableFlag((buttons.contains(ButtonConstant.STUDY_OBJECT_SAVE)) ? ButtonConstant.BUTTON_ENABLE : ButtonConstant.BUTTON_DISABLE);
        formsVo.setCommitEnableFlag((buttons.contains(ButtonConstant.STUDY_OBJECT_SUBMIT)) ? ButtonConstant.BUTTON_ENABLE : ButtonConstant.BUTTON_DISABLE);
        formsVo.setPassEnableFlag((buttons.contains(ButtonConstant.STUDY_OBJECT_PASS)) ? ButtonConstant.BUTTON_ENABLE : ButtonConstant.BUTTON_DISABLE);
        formsVo.setUnpassEnableFlag((buttons.contains(ButtonConstant.STUDY_OBJECT_NOPASS)) ? ButtonConstant.BUTTON_ENABLE : ButtonConstant.BUTTON_DISABLE );

        return formsVo;
    }


    /**
     * 根据object id 保存单个form信息
     * @param formsVo
     * @param objectId
     * @return
     */
    public Msg saveOneFormInfo(FormsVo formsVo, Long objectId, Integer saveFlag) {
        return formDataManager.saveOneFormInfo(formsVo, objectId, saveFlag) ;
    }


    /**
     * 根据object id 保存单个form信息 修改状态
     * @param formsVo
     * @param objectId
     * @param saveFlag
     * @return
     */
    public boolean saveFormAuditState(FormsVo formsVo, Long objectId, Integer saveFlag) {
        return formDataManager.saveFormAuditState(formsVo,objectId,saveFlag);
    }



    /**
     * 删除动态form
     * @param objectId
     * @return
     */
    @Transactional
    public  boolean delFormData(Long objectId) {
        // 更新 研究对象主表的状态为已删除
        studyObjectManager.delStudyObject(objectId);

        // 更新 相关的动态表单数据为已删除
        return formDataManager.delFormData(objectId);
    }



    /**
     * 研究对象-点击单个Crf对应的题组树形
     * @param crfFormId
     * @return
     */
    public CrfParentVo getOneFormInfoTree(Long crfFormId) {
        return formConfigManager.getOneFormInfoTree(crfFormId) ;
    }


    /**
     * 根据study id获取动态列头
     * @param studyId
     * @return
     */
    public DynamicFieldsVo getDynamicFieldsInfo(Long studyId) {
        DynamicFieldsVo dynamicFieldsVo = new DynamicFieldsVo() ;

        List<FormConfigVo> formConfigVoList = formConfigManager.getDisplayFormConfigVoList(studyId) ;
        dynamicFieldsVo.setFormConfigVoList(formConfigVoList);

        List<CrfParentVo> crfParentVoList = formConfigManager.getCrfFormListByStudyId(studyId) ;
        dynamicFieldsVo.setCrfList(crfParentVoList);

        return dynamicFieldsVo ;
    }

}
