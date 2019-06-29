package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.api.IUserService;
import com.ronglian.kangrui.saas.research.sci.biz.StudyRoleBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyRoleUserBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyUserBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRole;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleUser;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUser;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserGroupCenterVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import com.ronglian.kangrui.saas.research.sci.vo.TreeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:05
 **/
@Service
@Slf4j
public class StudyUserManager {

    @Autowired
    private StudyUserBiz studyUserBiz ;

    @Autowired
    private StudyRoleUserBiz studyRoleUserBiz ;

    @Autowired
    private IUserService userService ;

    @Autowired
    private StudyRoleBiz studyRoleBiz ;



    /**
     *  查询项目或角色下-用户列表
     * @param objectId
     * @param level
     * @return
     */
    public List<StudyUserVo> getUserListByObjectId(Long objectId, int level) {
        List<StudyUserVo> studyUserVoList = null ;
        if (level == ResearchConsts.LEVEL_STUDY) {
            // 查询项目下的 用户列表
            StudyUser studyUser = new StudyUser() ;
            studyUser.setStudyId(objectId);
            List<StudyUser> studyUserList = studyUserBiz.selectList(studyUser) ;
            List<Long> selectUserIdList = studyUserList.stream().map(StudyUser::getUserId).collect(toList());
            studyUserVoList = studyUserBiz.getSelectUserList(selectUserIdList) ;

        } else if (level == ResearchConsts.LEVEL_ROLE){
            // 查询项目角色下的 用户列表
            StudyRoleUser studyRoleUser = new StudyRoleUser() ;
            studyRoleUser.setRoleId(objectId);
            List<StudyRoleUser> studyRoleUserList = studyRoleUserBiz.selectList(studyRoleUser) ;
            List<Long> selectUserIdList = studyRoleUserList.stream().map(StudyRoleUser::getUserId).collect(toList());
            studyUserVoList = studyUserBiz.getSelectUserList(selectUserIdList)  ;
        }

        return studyUserVoList ;
    }




    /**
     * 查询项目或角色下 - 未选择用户列表
     * @param objectId
     * @param level
     * @return
     */
    public List<StudyUserVo> getValidUserListByObjectId(Long objectId, int level) {
        List<StudyUserVo> validUserList = null ;

        if (level == ResearchConsts.LEVEL_STUDY) {
            // 获取有效的用户列表
            List<User> allUserList = userService.listUserInfo() ;

            // 查询项目下的 选择用户列表
            StudyUser studyUser = new StudyUser() ;
            studyUser.setStudyId(objectId);
            List<StudyUser> studyUserList = studyUserBiz.selectList(studyUser) ;
            List<Long> selectUserIdList = studyUserList.stream().map(StudyUser::getUserId).collect(toList());
            validUserList = studyUserBiz.getValidUserList(allUserList, selectUserIdList) ;

        } else if (level == ResearchConsts.LEVEL_ROLE){
            // 判断某个课题下是否已选择用户列表，如果有用户列表， 未选择用户=（项目下的已选择用户-角色已选择用户）；否则返回空
            List<StudyUser> studyUserList = this.getUserListByStudyId(objectId) ;
            if (studyUserList!=null && studyUserList.size()>0) {
                // 获取项目下 已选中用户列表
                List<Long> studyUserIdList = studyUserList.stream().map(StudyUser::getUserId).collect(toList());
                List<User> userList = studyUserBiz.getStudyUserList(studyUserIdList) ;

                // 查询角色下的 已选中用户列表
                StudyRoleUser studyRoleUser = new StudyRoleUser() ;
                studyRoleUser.setRoleId(objectId);
                List<StudyRoleUser> studyRoleUserList = studyRoleUserBiz.selectList(studyRoleUser) ;
                List<Long> selectUserIdList = studyRoleUserList.stream().map(StudyRoleUser::getUserId).collect(toList());

                // 查询角色下的 未选择用户列表
                validUserList = studyUserBiz.getValidUserList(userList, selectUserIdList) ;
            }

        }

        return validUserList ;
    }





    /**
     * 获取某个角色关联的课题下已选择用户列表
     *
     * @param roleId
     * @return
     */
    public List<StudyUser> getUserListByStudyId(Long roleId){
        StudyRole studyRole = studyRoleBiz.selectById(roleId) ;
        StudyUser studyUser = new StudyUser() ;
        studyUser.setStudyId(studyRole.getStudyId());
        List<StudyUser> studyUserList = studyUserBiz.selectList(studyUser) ;
        return studyUserList ;
    }







    /**
     * 保存  项目或角色下-用户列表
     * @param objectId
     * @param level
     * @param selectUserList
     * @return
     */
    @Transactional
    public boolean saveUserInfo(Long objectId, int level, List<StudyUserVo> selectUserList) {
        boolean flag = Boolean.FALSE ;

        if (level == ResearchConsts.LEVEL_STUDY) {
            // 项目下- 用户 保存
            flag = studyUserBiz.saveUserInfoByStudyId(objectId, selectUserList) ;

        } else if (level == ResearchConsts.LEVEL_ROLE){
            // 项目角色下-用户 保存
            flag = studyRoleUserBiz.saveUserInfoByRoleId(objectId, selectUserList) ;

        }

        return flag ;
    }


    /**
     * 展示勾选队列
     * @param studyId
     * @param userId
     * @return
     */
    public List<TreeEntity>getSelectedGroup(Long studyId, Long userId){
     return  studyUserBiz.getSelectedGroup(studyId,userId);
    }


    /**
     * 展示勾选中心
     * @param studyId
     * @param userId
     * @return
     */
    public List<TreeEntity>getSelectedCenter(Long studyId, Long userId){
        return  studyUserBiz.getSelectedCenter(studyId,userId);
    }



    /**
     * 保存用户勾选 中心-队列 数据
     *
     * @param studyUserGroupCenterVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSelectedGroup(StudyUserGroupCenterVo studyUserGroupCenterVo){
        return studyUserBiz.saveSelectedGroup(studyUserGroupCenterVo);
    }


}
