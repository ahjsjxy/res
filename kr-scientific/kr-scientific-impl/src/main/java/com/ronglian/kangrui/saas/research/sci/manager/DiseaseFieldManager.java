package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.DiseaseFieldBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-19 18:17
 **/
@Service
public class DiseaseFieldManager {

    @Autowired
    private DiseaseFieldBiz diseaseFieldBiz ;


    /**
     * 把选中的字段新增插入 disease_field，根据diseaseId和dictId
     * @param diseaseId
     * @param dictId
     * @param fieldId
     */
    public void addByDiseaseId(Long diseaseId, Long dictId, Long fieldId) {
        diseaseFieldBiz.addByDiseaseId(diseaseId, dictId, fieldId);
    }



    /**
     * 先把 disease_field 更新deleted=1，根据diseaseId和dictId
     * @param diseaseId
     * @param dictId
     */
    public void updateByDiseaseId(Long diseaseId, Long dictId){
        diseaseFieldBiz.updateByDiseaseId(diseaseId, dictId);
    }


    /***
     *  删除disease_field，根据diseaseId和dictId
     * @param diseaseId
     * @param dictId
     */
    public void deleteByDiseaseId(Long diseaseId, Long dictId) {
        diseaseFieldBiz.deleteByDiseaseId(diseaseId, dictId);
    }

}
