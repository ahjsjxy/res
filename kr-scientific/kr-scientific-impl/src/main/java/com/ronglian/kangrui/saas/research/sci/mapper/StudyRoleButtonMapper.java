package com.ronglian.kangrui.saas.research.sci.mapper;


import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleButton;
import com.ronglian.kangrui.saas.research.sci.vo.PortalButtonVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudyRoleButtonMapper extends MyBaseMapper<StudyRoleButton> {

    public List<PortalButtonVo> getButtonByRoleId(PortalButtonVo portalButtonVo) ;


    public List<PortalButtonVo> getStudyRoleButtonExists(PortalButtonVo portalButtonVo) ;

    public List<StudyRoleButton> queryButtonByStudyIdAndUserId(@Param("studyId") Long studyId,
                                                               @Param("userId") Long userId);
}