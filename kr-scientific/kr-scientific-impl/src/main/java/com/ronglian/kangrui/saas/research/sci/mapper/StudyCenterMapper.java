package com.ronglian.kangrui.saas.research.sci.mapper;


import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudyCenterMapper extends MyBaseMapper<StudyCenter> {

    public List<HospCenterVo> getSelectedCenterList(@Param(value = "studyId") Long studyId) ;


    public List<HospCenterVo> getValidCenterList(@Param(value = "studyId") Long studyId) ;


    public void deleteByStudyId(@Param(value = "studyId") Long studyId) ;

}