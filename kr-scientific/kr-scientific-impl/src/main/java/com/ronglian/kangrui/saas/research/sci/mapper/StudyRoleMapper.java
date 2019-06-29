package com.ronglian.kangrui.saas.research.sci.mapper;


import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;

import com.ronglian.kangrui.saas.research.sci.entity.StudyRole;
import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleVo;

import java.util.List;

public interface StudyRoleMapper extends MyBaseMapper<StudyRole> {

    public List<StudyRoleVo> getStudyRoleList(StudyRoleVo studyRoleVo) ;


    public List<StudyRoleVo> checkNameExists(StudyRoleVo studyRoleVo) ;


}