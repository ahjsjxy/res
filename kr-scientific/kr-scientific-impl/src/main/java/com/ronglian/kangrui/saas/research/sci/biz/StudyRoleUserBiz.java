package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleUser;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleUserMapper;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 14:21
 **/
@Service
public class StudyRoleUserBiz extends BaseBiz<StudyRoleUserMapper, StudyRoleUser> {

    @Autowired
    private StudyRoleUserMapper studyRoleUserMapper ;



    /**
     * 项目角色下-用户 保存
     * @param roleId
     * @param selectUserList
     * @return
     */
    public boolean saveUserInfoByRoleId(Long roleId, List<StudyUserVo> selectUserList) {
        boolean flag = Boolean.FALSE ;

        // 1 : 根据 role id 删除 study_role_user
        StudyRoleUser studyRoleUser = new StudyRoleUser() ;
        studyRoleUser.setRoleId(roleId);
        this.delete(studyRoleUser);

        // 2 ：根据 role id 插入 study_role_user
        if (selectUserList!=null && selectUserList.size()>0) {
            for (StudyUserVo studyUserVo: selectUserList) {
                StudyRoleUser temp = new StudyRoleUser() ;
                temp.setRoleId(roleId);
                temp.setUserId(studyUserVo.getId());
                this.insertSelective(temp) ;
            }
        }

        flag = Boolean.TRUE ;
        return flag ;
    }





}
