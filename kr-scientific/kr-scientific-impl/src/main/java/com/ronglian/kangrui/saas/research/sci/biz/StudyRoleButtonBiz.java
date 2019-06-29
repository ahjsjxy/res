package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleButton;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleButtonMapper;
import com.ronglian.kangrui.saas.research.sci.vo.ButtonCodeVo;
import com.ronglian.kangrui.saas.research.sci.vo.PortalButtonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 11:56
 **/
@Service
public class StudyRoleButtonBiz extends BaseBiz<StudyRoleButtonMapper, StudyRoleButton> {

    @Autowired
    private  StudyRoleButtonMapper studyRoleButtonMapper ;


    /**
     * 根据角色 列出所有的菜单，已选中的为checked
     * @param portalButtonVo
     * @return
     */
    public List<PortalButtonVo> getButtonByRoleId(PortalButtonVo portalButtonVo) {
        return studyRoleButtonMapper.getButtonByRoleId(portalButtonVo) ;
    }


    /**
     *  根据角色  查看角色对应的按钮  是否已存在数据
     * @param portalButtonVo
     * @return
     */
    public List<PortalButtonVo> getStudyRoleButtonExists(PortalButtonVo portalButtonVo) {
        return studyRoleButtonMapper.getStudyRoleButtonExists(portalButtonVo) ;
    }


    /**
     * 根据用户，查询用户在某个项目下的权限按钮集合
     * @param studyId
     * @param userId
     * @return
     */
    public List<StudyRoleButton> queryButtonByStudyIdAndUserId(Long studyId,Long userId){
        return studyRoleButtonMapper.queryButtonByStudyIdAndUserId(studyId,userId);
    }
}
