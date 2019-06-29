package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.mapper.CrfDictFieldMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.CrfDictField;
import com.ronglian.kangrui.saas.research.sci.mapper.CrfMapper;
import com.ronglian.kangrui.saas.research.sci.util.ListDifferentUtil;
import com.ronglian.kangrui.saas.research.sci.vo.CrfDictFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.CrfFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.DiseaseFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class CrfDictFieldBiz extends BaseBiz<CrfDictFieldMapper, CrfDictField> {


    @Autowired
    private CrfDictFieldMapper crfDictFieldMapper ;

    @Autowired
    private CrfMapper crfMapper ;


    /**
     * 先把 crf_dict_field 更新deleted=1，根据crf id 和dictId 和fieldId 集合
     * @param crfId
     * @param dictionaryId
     */
    public void updateByCrfId(Long crfId, Long dictionaryId, List<Long> fieldIdList){
        CrfDictFieldVo crfDictFieldVo = new CrfDictFieldVo() ;
        crfDictFieldVo.setCrfId(crfId);
        crfDictFieldVo.setDictId(dictionaryId);
        crfDictFieldVo.setFieldIdList(fieldIdList);
        crfDictFieldMapper.updateByCrfId(crfDictFieldVo);
    }


    /**
     * 把选中的字段新增插入 crf_dict_field，根据crfId (题组ID) 和dictId
     * @param crfId
     * @param dictId
     * @param fieldId
     */
    public void addCrfFieldByCrfId(Long crfId, Long dictId, Long fieldId) {
        // 查询题组ID
        Crf crf = crfMapper.selectByPrimaryKey(crfId) ;

        CrfDictField crfDictField = new CrfDictField() ;
        crfDictField.setCrfId(crfId);
        crfDictField.setCrfFormId(crf.getParentId());
        crfDictField.setDictId(dictId);
        crfDictField.setFieldId(fieldId);
        crfDictField.setGenerateToDb(ResearchConsts.GENERATE_TO_DB_NO);//动态表单字段是否创建（0：未创建 ）
        crfDictField.setDisplay(ResearchConsts.DISPLAY_NO);//新添加的字段都是隐藏
        crfDictField.setDeleted(ResearchConsts.DELETED_NO);// 0：未删除
        crfDictField.setCreateTime(new Date());
        crfDictFieldMapper.insertSelective(crfDictField) ;
    }



    /**
     * 查询crf 题组 关联的字段列表
     * @param crfId
     * @param dictionaryId
     * @param fieldId
     * @return
     */
    public List<FieldVo> getSelectFieldByCrfId(Long crfId, Long dictionaryId, Long fieldId) {
        CrfDictField crfDictField = new CrfDictField() ;
        crfDictField.setCrfId(crfId);
        crfDictField.setDictId(dictionaryId);
        crfDictField.setFieldId(fieldId);
        List<FieldVo> selectFieldList = crfDictFieldMapper.getSelectFieldByCrfId(crfDictField) ;
        return selectFieldList ;
    }



    /**
     * 查询题组下的字段列表
     * @param crfSecondId
     * @return
     */
    public List<CrfDictField> getCrfDictFieldInfoByCrfSecondId(Long crfSecondId) {
        CrfDictField crfDictField = new CrfDictField();
        crfDictField.setCrfId(crfSecondId);
        crfDictField.setDeleted(ResearchConsts.DELETED_NO);
        List<CrfDictField> crfDictFieldList = crfDictFieldMapper.select(crfDictField) ;
        return crfDictFieldList ;
    }



    /**
     * 项目-校验  1：存在未删除的CRF且还没创建表 2：CRF存在字段未创建到数据库列的
     * @param studyId
     * @return
     */
    public boolean checkHasCrfFormGenerateToDb(Long studyId) {
        CrfDictFieldVo crfDictFieldVo = new CrfDictFieldVo() ;
        crfDictFieldVo.setStudyId(studyId);
        List<CrfFieldVo> crfDictFieldVoList = crfDictFieldMapper.getCrfAndFieldGenerateToDbNoList(crfDictFieldVo) ;
        boolean flag = (crfDictFieldVoList!=null && crfDictFieldVoList.size()>0) ;
        return flag ;
    }



    /**
     *  查询 CRF表单 字段未创建到数据库列的
     * @param crfDictFieldVo
     * @return
     */
    public List<CrfFieldVo> getCrfAndFieldGenerateToDbNoList(CrfDictFieldVo crfDictFieldVo) {
        return crfDictFieldMapper.getCrfAndFieldGenerateToDbNoList(crfDictFieldVo) ;
    }



    /**
     * 更新 crf_dict_field 的 field_name和generate_to_db
     * @param crfFieldVoList
     */
    public void updateCrfFieldsGenerateToDB(List<CrfFieldVo> crfFieldVoList) {
        if (crfFieldVoList!=null && crfFieldVoList.size()>0) {
            for (CrfFieldVo crfFieldVo : crfFieldVoList) {
                CrfDictField crfDictField = new CrfDictField() ;
                BeanUtils.copyProperties(crfFieldVo, crfDictField);
                crfDictField.setGenerateToDb(ResearchConsts.GENERATE_TO_DB_YES);
                crfDictField.setUpdateTime(new Date());
                crfDictFieldMapper.updateByPrimaryKeySelective(crfDictField) ;
            }
        }
    }



    /**
     * 更新 crf_dict_field 的 field_name和generate_to_db
     * @param crfFieldVo
     */
    public void updateCrfFieldsGenerateToDB(CrfFieldVo crfFieldVo) {
        CrfDictField crfDictField = new CrfDictField() ;
        BeanUtils.copyProperties(crfFieldVo, crfDictField);
        crfDictField.setGenerateToDb(ResearchConsts.GENERATE_TO_DB_YES);
        crfDictField.setUpdateTime(new Date());
        crfDictFieldMapper.updateByPrimaryKeySelective(crfDictField) ;
    }



    /**
     * Crf表单-校验存在未删除的字段  1: CRF 已经创建表到DB  2：CRF存在字段未创建到数据库列的
     * @param crfFormId
     * @return
     */
    public boolean checkHasFieldsGenerateToDb(Long crfFormId) {
        CrfDictFieldVo crfDictFieldVo = new CrfDictFieldVo() ;
        crfDictFieldVo.setCrfFormId(crfFormId);
        List<CrfFieldVo> crfFieldVoList = crfDictFieldMapper.getFieldsGenerateToDbByCrfFormId(crfDictFieldVo) ;
        boolean flag = (crfFieldVoList!=null && crfFieldVoList.size()>0) ;

        return flag ;
    }



    /**
     *  查询 CRF存在字段未创建到数据库列的
     * @param crfFormId
     * @return
     */
    public List<CrfFieldVo> getCrfFieldList(Long crfFormId) {
        CrfDictFieldVo crfDictFieldVo = new CrfDictFieldVo() ;
        crfDictFieldVo.setCrfFormId(crfFormId);
        return crfDictFieldMapper.getFieldsGenerateToDbByCrfFormId(crfDictFieldVo) ;
    }


    /**
     * 查询CRF以及所有字段都已经创建列
     * @param crfDictFieldVo
     * @return
     */
    public List<CrfFieldVo> getCrfAndFieldGenerateToDbList(CrfDictFieldVo crfDictFieldVo){
        return crfDictFieldMapper.getCrfAndFieldGenerateToDbList(crfDictFieldVo) ;
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
    public Map<String, Object> getOldToNewFieldList(Long crfId, Long dictionaryId, List<DiseaseFieldVo> diseaseFieldVos) {
        Map<String, Object> map = new HashMap<String, Object>() ;

        // 先查询这个题组对应的字典下，数据库之前老的已勾选字段
        List<FieldVo> oldSelectFieldList = this.getSelectFieldByCrfId(crfId, dictionaryId,null) ;
        List<Long> oldFieldIdList = null ;
        if (oldSelectFieldList!=null && oldSelectFieldList.size()>0) {
            oldFieldIdList = oldSelectFieldList.stream().map(FieldVo::getId).collect(toList());
        }

        List<Long> newFieldIdList = null ;
        if (diseaseFieldVos!=null && diseaseFieldVos.size()>0) {
            // 页面传参过来判断选中的字段列
            List<DiseaseFieldVo> selectFieldList = diseaseFieldVos.stream().filter(diseaseFieldVo -> (diseaseFieldVo.isChecked()==Boolean.TRUE) ).collect(toList());
            if (selectFieldList!=null && selectFieldList.size()>0) {
                newFieldIdList = selectFieldList.stream().map(DiseaseFieldVo::getId).collect(toList());
            }
        }

        map.put("oldFieldIdList", oldFieldIdList) ;
        map.put("newFieldIdList", newFieldIdList) ;

        return map ;
    }


    /**
     * 部分指标删除；部分指标新增
     *
     * @param crfId
     * @param dictionaryId
     * @param oldFieldIdList
     * @param newFieldIdList
     * @return
     */
    @Transactional
    public Msg handleUpdateCrfFieldsInfo(Long crfId, Long dictionaryId, List<Long> oldFieldIdList, List<Long> newFieldIdList) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            // 获取删除的集合
            List<Long> deleteList = ListDifferentUtil.getDeleteList(oldFieldIdList, newFieldIdList) ;
            if (deleteList!=null && deleteList.size()>0) {
                this.updateByCrfId(crfId, dictionaryId, deleteList);
            }

            // 获取新增的集合
            List<Long> insertList = ListDifferentUtil.getInsertList(oldFieldIdList, newFieldIdList) ;
            if (insertList!=null && insertList.size()>0) {
                for (Long fieldId : insertList) {
                    this.addCrfFieldByCrfId(crfId, dictionaryId, fieldId);
                }
            }

            msg.setSucFlag(Boolean.TRUE);
            msg.setDesc(ResearchConsts.SAVE_SUC_CRF_FIELDS_INFO);//保存指标信息成功

        } catch (Exception e) {
            log.error(e.getMessage());
            //保存异常
            msg.setCode(RestCodeConstants.STATUS_CODE_SAVE_EXCEPTION);
        }

        return msg ;
    }


}
