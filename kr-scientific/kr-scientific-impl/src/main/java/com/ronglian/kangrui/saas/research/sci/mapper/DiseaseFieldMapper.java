package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.DiseaseField;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface DiseaseFieldMapper extends Mapper<DiseaseField> {

    /**
     * 先把 disease_field 更新，历史的关联字段置为无效，根据diseaseId和dictId
     * @param diseaseField
     */
    public void updateDiseaseFieldByDiseaseId(DiseaseField diseaseField) ;

}