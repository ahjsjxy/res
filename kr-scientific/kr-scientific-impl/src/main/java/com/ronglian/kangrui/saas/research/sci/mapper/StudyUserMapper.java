package com.ronglian.kangrui.saas.research.sci.mapper;


import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUser;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudyUserMapper extends MyBaseMapper<StudyUser> {

    /**
     * 删除项目下的角色关联用户表
     * @param studyUserVo
     */
    public void deleteStudyRoleUserInfo(StudyUserVo studyUserVo) ;

}