package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.mapper.DiseaseFieldMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.DiseaseField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DiseaseFieldBiz extends BaseBiz<DiseaseFieldMapper,DiseaseField> {


    @Autowired
    private DiseaseFieldMapper diseaseFieldMapper ;


    /**
     * 把选中的字段新增插入 disease_field，根据diseaseId和dictId
     * @param diseaseId
     * @param dictId
     * @param fieldId
     */
    public void addByDiseaseId(Long diseaseId, Long dictId, Long fieldId) {
        DiseaseField diseaseField = new DiseaseField() ;
        diseaseField.setDiseaseId(diseaseId);
        diseaseField.setDictId(dictId);
        diseaseField.setFieldId(fieldId);
        diseaseField.setDeleted(ResearchConsts.DELETED_NO);
        diseaseField.setUpdateTime(new Date());
        diseaseFieldMapper.insertSelective(diseaseField) ;
    }



    /**
     * 先把 disease_field 更新deleted=1，根据diseaseId和dictId
     * @param diseaseId
     * @param dictId
     */
    public void updateByDiseaseId(Long diseaseId, Long dictId){
        DiseaseField diseaseField = new DiseaseField() ;
        diseaseField.setDiseaseId(diseaseId);
        diseaseField.setDictId(dictId);
        diseaseFieldMapper.updateDiseaseFieldByDiseaseId(diseaseField);
    }


    /***
     *  删除disease_field，根据diseaseId和dictId
     * @param diseaseId
     * @param dictId
     */
    public void deleteByDiseaseId(Long diseaseId, Long dictId) {
        DiseaseField diseaseField = new DiseaseField() ;
        diseaseField.setDiseaseId(diseaseId);
        diseaseField.setDictId(dictId);
        diseaseFieldMapper.delete(diseaseField) ;
    }


}
