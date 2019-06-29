package com.ronglian.kangrui.saas.research.sci.mapper;


import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUserGroupCenter;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserGroupCenterVo;

import java.util.List;

public interface StudyUserGroupCenterMapper extends MyBaseMapper<StudyUserGroupCenter> {


//    @Param("studyId") Long studyId,
//    @Param("userId") Long userId,


    /**
     * 根据登陆用户ID和项目ID，获取中心权限列表（若有队列ID，则继续过滤队列下的中心列表）
     *
     * @param studyUserGroupCenterVo
     * @return
     */
    public List<StudyUserGroupCenter> selectCenterByStudyAndUser(StudyUserGroupCenterVo studyUserGroupCenterVo);
}