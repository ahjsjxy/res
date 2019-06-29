package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.Disease;
import com.ronglian.kangrui.saas.research.sci.entity.DiseaseField;
import com.ronglian.kangrui.saas.research.sci.vo.DiseaseVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface DiseaseMapper extends Mapper<Disease> {

    /**
     * 查询疾病管理列表
     * @param name
     * @return
     */
    public List<DiseaseVo> getDiseaseList(@Param(value = "name") String name) ;


    /**
     * 校验疾病名称是否存在
     * @param disease
     * @return
     */
    public List<DiseaseVo> checkDiseaseNameExists(Disease disease) ;


    /**
     * 查询某疾病关联的字段列表
     * @param diseaseField
     * @return
     */
    public List<FieldVo> getSelectFieldByDiseaseId(DiseaseField diseaseField) ;
}