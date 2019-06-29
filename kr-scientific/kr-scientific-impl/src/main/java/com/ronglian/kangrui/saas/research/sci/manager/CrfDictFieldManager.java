package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.biz.StudyCrfBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCrf;
import com.ronglian.kangrui.saas.research.sci.biz.CrfDictFieldBiz;
import com.ronglian.kangrui.saas.research.sci.vo.CrfDictFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.CrfFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.DiseaseFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-19 14:12
 **/
@Service
@Slf4j
public class CrfDictFieldManager {

    @Autowired
    private CrfDictFieldBiz crfDictFieldBiz ;

    @Autowired
    private StudyCrfBiz studyCrfBiz ;

    @Autowired
    private FormConfigManager formConfigManager ;



    /**
     * 查询crf 题组 关联的字段列表
     * @param crfId
     * @param dictionaryId
     * @param fieldId
     * @return
     */
    public List<FieldVo> getSelectFieldByCrfId(Long crfId, Long dictionaryId, Long fieldId) {
        return crfDictFieldBiz.getSelectFieldByCrfId(crfId, dictionaryId, fieldId) ;
    }



    /**
     * 项目-校验  1：存在未删除的CRF且还没创建表 2：CRF存在字段未创建到数据库列的
     * @param studyId
     * @return
     */
    public boolean checkHasCrfFormGenerateToDb(Long studyId) {
        return crfDictFieldBiz.checkHasCrfFormGenerateToDb(studyId) ;
    }



    /**
     * Crf表单-校验存在未删除的字段  1: CRF 已经创建表到DB  2：CRF存在字段未创建到数据库列的
     * @param crfFormId
     * @return
     */
    public boolean checkHasFieldsGenerateToDb(Long crfFormId) {
        return crfDictFieldBiz.checkHasFieldsGenerateToDb(crfFormId) ;
    }



    /**
     * 1，项目-校验存在未删除的CRF且还没创建表，若存在，则项目-创建Crf表单到DB
     * 2, 不存在：Crf表单-校验存在未删除的字段
     *
     * @param studyId
     * @return
     */
    public boolean  checkFormToFieldValidByStudyId(Long studyId) {
        boolean flag = Boolean.FALSE ;

        // 项目-校验存在未删除的CRF且还没创建表
        flag = this.checkHasCrfFormGenerateToDb(studyId) ;
        if (!flag) {
            // 不存在：Crf表单-校验存在未删除的字段
            formConfigManager.saveFieldsGenerateToDbByStudyId(studyId) ;
        }

        return flag ;
    }



    /**
     * 1，项目-校验存在未删除的CRF且还没创建表，若存在，则项目-创建Crf表单到DB
     * 2, 不存在：Crf表单-校验存在未删除的字段
     *
     * @param crfFormId
     * @return
     */
    public boolean checkFormToFieldValid(Long crfFormId) {
        boolean flag = Boolean.FALSE ;

        StudyCrf studyCrf = studyCrfBiz.getStudyIdByCrfFormId(crfFormId) ;
        // 项目-校验存在未删除的CRF且还没创建表
        boolean checkHasCrfFormGenerateToDb = this.checkHasCrfFormGenerateToDb(studyCrf.getStudyId()) ;
        if (checkHasCrfFormGenerateToDb) {
            // 存在：项目-创建Crf表单到DB
            formConfigManager.saveCrfFormGenerateToDb(studyCrf.getStudyId()) ;
        } else {
            // 不存在：Crf表单-校验存在未删除的字段
            flag = this.checkHasFieldsGenerateToDb(crfFormId) ;
        }

        return flag ;
    }



    /**
     * 1: 先查询这个题组对应的字典下，老的已勾选字段
     * 2: 页面上选中的字段集合
     * 3：找出库中已有字段和页面勾选字段对比：删除的字段和新增的字段。编辑的字段保持不动
     *
     * @param crfId
     * @param dictionaryId
     * @param diseaseFieldVos
     * @return
     */
    public Msg handleUpdateCrfFieldsInfo(Long crfId, Long dictionaryId, List<DiseaseFieldVo> diseaseFieldVos) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {

            // 获取oldFieldIdList，newFieldIdList
            Map<String, Object> map = crfDictFieldBiz.getOldToNewFieldList(crfId, dictionaryId, diseaseFieldVos) ;
            if (map!=null && map.size()>0) {
                List<Long> oldFieldIdList = (List<Long>)map.get("oldFieldIdList") ;
                List<Long> newFieldIdList = (List<Long>)map.get("newFieldIdList") ;

                // 部分指标删除；部分指标新增
                msg = crfDictFieldBiz.handleUpdateCrfFieldsInfo(crfId, dictionaryId, oldFieldIdList, newFieldIdList) ;

            } else {
                log.info("map为空===========");
                msg.setDesc(ResearchConsts.SAVE_ERROR_CRF_FIELDS_INFO);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            //保存异常
            msg.setCode(RestCodeConstants.STATUS_CODE_SAVE_EXCEPTION);
        }

        return msg ;
    }



    /**
     * 查询CRF以及所有字段都已经创建列
     *
     * @param crfFormId
     * @return
     */
    public boolean checkCrfConfigValid(Long crfFormId) {
        StudyCrf studyCrf = studyCrfBiz.getStudyIdByCrfFormId(crfFormId) ;

        CrfDictFieldVo crfDictFieldVo = new CrfDictFieldVo() ;
        crfDictFieldVo.setCrfFormId(crfFormId);
        crfDictFieldVo.setStudyId(studyCrf.getStudyId());
        List<CrfFieldVo> crfFieldVoList = crfDictFieldBiz.getCrfAndFieldGenerateToDbList(crfDictFieldVo) ;
        boolean flag = (crfFieldVoList!=null && crfFieldVoList.size()>0) ;

        return flag ;
    }


}
