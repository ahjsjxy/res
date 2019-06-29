package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.StudyCrf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudyCrfMapper extends Mapper<StudyCrf> {

    public StudyCrf getStudyIdByCrfFormId(@Param(value = "crfFormId") Long crfFormId) ;


    public List<StudyCrf> getCrfListByStudyId(@Param(value = "studyId") Long studyId) ;
}