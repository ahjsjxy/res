package com.ronglian.kangrui.saas.research.sci.biz;

import com.google.common.collect.Lists;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.api.IUserService;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUser;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUserGroupCenter;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyCenterMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyGroupMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyUserGroupCenterMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyUserMapper;
import com.ronglian.kangrui.saas.research.sci.util.ListDifferentUtil;
import com.ronglian.kangrui.saas.research.sci.vo.ChildEntity;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserGroupCenterVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserVo;
import com.ronglian.kangrui.saas.research.sci.vo.TreeEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:37
 **/
@Service
public class StudyUserBiz extends BaseBiz<StudyUserMapper,StudyUser> {

    @Autowired
    private StudyUserMapper studyUserMapper ;

    @Autowired
    private StudyCenterMapper studyCenterMapper;

    @Autowired
    private StudyGroupMapper studyGroupMapper;

    @Autowired
    private StudyUserGroupCenterMapper studyUserGroupCenterMapper;


    @Autowired
    private IUserService userService ;



    /**
     * 项目下- 用户 保存
     * @param studyId
     * @param selectUserList
     * @return
     */
    public void addUserInfoByStudyId(Long studyId, List<StudyUserVo> selectUserList) {
        // 1 : 根据study id 删除 study_user
        StudyUser studyUser = new StudyUser() ;
        studyUser.setStudyId(studyId);
        this.delete(studyUser);

        // 2 ：根据study id 插入 study_user
        if (selectUserList!=null && selectUserList.size()>0) {
            for (StudyUserVo studyUserVo: selectUserList) {
                StudyUser temp = new StudyUser() ;
                temp.setStudyId(studyId);
                temp.setUserId(studyUserVo.getId());
                this.insertSelective(temp) ;
            }
        }
    }





    /**
     * 项目下- 用户 保存
     * @param studyId
     * @param selectUserList
     * @return
     */
    public boolean saveUserInfoByStudyId(Long studyId, List<StudyUserVo> selectUserList) {
        boolean flag = Boolean.FALSE ;

        // 查询项目下的历史用户列表；对比最新选中用户列表，找出删除的用户，同时把该用户从项目的所有的角色下删除
        this.deleteStudyRoleInfo(studyId, selectUserList);

        // 项目下用户 保存
        this.addUserInfoByStudyId(studyId, selectUserList) ;

        flag = Boolean.TRUE ;
        return flag ;
    }





    /**
     * 项目下的用户保存时，查询项目下的历史用户列表；对比最新选中用户列表，找出删除的用户，同时把该用户从项目的所有的角色下删除
     *
     * @param studyId
     * @param selectUserList
     */
    public void deleteStudyRoleInfo(Long studyId, List<StudyUserVo> selectUserList) {
        // 查询项目下的历史用户列表
        StudyUser studyUser = new StudyUser() ;
        studyUser.setStudyId(studyId);
        List<StudyUser> oldStudyUserList = this.selectList(studyUser) ;
        List<Long> beforeList = oldStudyUserList.stream().map(StudyUser::getUserId).collect(Collectors.toList());

        // 最新选中用户列表
        List<Long> afterList = selectUserList.stream().map(StudyUserVo::getId).collect(Collectors.toList());

        // 删除的用户列表
        List<Long> deleteUserList = ListDifferentUtil.getDeleteList(beforeList, afterList) ;
        if (deleteUserList!=null && deleteUserList.size()>0) {
            StudyUserVo studyUserVo = new StudyUserVo() ;
            studyUserVo.setStudyId(studyId);
            studyUserVo.setUserIdList(deleteUserList);
            studyUserMapper.deleteStudyRoleUserInfo(studyUserVo);
        }

    }




    /**
     * 队列权限
     * @param studyId
     * @param userId
     * @return
     */
    public List<TreeEntity>getSelectedGroup(Long studyId, Long userId){
        List<HospCenterVo> centerList = getCenterByStudyId(studyId);
        List<StudyGroup> groupList = getGroupByStudyId(studyId);
        List<StudyUserGroupCenter> groupCenterList = getStudyUserGroupCenter(studyId,userId);
        List<TreeEntity> result = Lists.newArrayList();
        for(HospCenterVo center : centerList){
            TreeEntity treeEntity = new TreeEntity();
            treeEntity.setId(center.getId());
            treeEntity.setName(center.getName());
            treeEntity.setParentId(ResearchConsts.PARENT_ROOT_ID);
            treeEntity.setLevel(ResearchConsts.LEVEL_FIRST);

            List<Long> groups = groupCenterList.stream().filter(StudyUserGroupCenter->(StudyUserGroupCenter.getCenterId().longValue()==center.getId().longValue())).map(item->item.getGroupId()).collect(Collectors.toList());
            List<ChildEntity> childEntityList = new ArrayList<>();
            for(StudyGroup group : groupList){
                ChildEntity childEntity = new ChildEntity();
                childEntity.setId(group.getId());
                childEntity.setName(group.getName());
                childEntity.setParentId(center.getId());
                childEntity.setLevel(ResearchConsts.LEVEL_TWO);
                if(groups.contains(group.getId())){
                    childEntity.setChecked(Boolean.TRUE);
                } else {
                    childEntity.setChecked(Boolean.FALSE);
                }
                childEntityList.add(childEntity);
            }
            treeEntity.setList(childEntityList);

            result.add(treeEntity);
        }
        return result;
    }



    /**
     * 中心权限列表
     * @param studyId
     * @param userId
     * @return
     */
    public List<TreeEntity>getSelectedCenter(Long studyId, Long userId){
        List<HospCenterVo> centerList = getCenterByStudyId(studyId);
        List<StudyGroup> groupList = getGroupByStudyId(studyId);
        List<StudyUserGroupCenter> groupCenterList = getStudyUserGroupCenter(studyId,userId);
        List<TreeEntity> result = Lists.newArrayList();
        for(StudyGroup group : groupList){
            TreeEntity treeEntity = new TreeEntity();
            treeEntity.setId(group.getId());
            treeEntity.setName(group.getName());
            treeEntity.setParentId(ResearchConsts.PARENT_ROOT_ID);
            treeEntity.setLevel(ResearchConsts.LEVEL_FIRST);

            List<Long> centers = groupCenterList
                    .stream()
                    .filter(StudyUserGroupCenter->(StudyUserGroupCenter.getGroupId().longValue()==group.getId().longValue()))
                    .map(item->item.getCenterId())
                    .collect(Collectors.toList());
            List<ChildEntity> childEntityList = new ArrayList<>();
            for(HospCenterVo center : centerList){
                ChildEntity childEntity = new ChildEntity();
                childEntity.setId(center.getId());
                childEntity.setName(center.getName());
                childEntity.setParentId(group.getId());
                childEntity.setLevel(ResearchConsts.LEVEL_TWO);
                if(centers.contains(center.getId())){
                    childEntity.setChecked(Boolean.TRUE);
                } else {
                    childEntity.setChecked(Boolean.FALSE);
                }
                childEntityList.add(childEntity);
            }
            treeEntity.setList(childEntityList);

            result.add(treeEntity);
        }
        return result;
    }




    /**
     * 获取项目下的队列
     * @param studyId
     * @return
     */
    private List<StudyGroup> getGroupByStudyId(Long studyId){
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setStudyId(studyId);
        studyGroup.setDeleted(0);
        List<StudyGroup> groupList = studyGroupMapper.select(studyGroup);
        return  groupList;
    }

    /**
     * 获取项目下的中心
     * @param studyId
     * @return
     */
    private List<HospCenterVo> getCenterByStudyId(Long studyId){
        List<HospCenterVo> centerList = studyCenterMapper.getSelectedCenterList(studyId);
        return centerList;
    }

    public List<StudyUserGroupCenter> getStudyUserGroupCenter(Long studyId,Long userId){
        StudyUserGroupCenter studyUserGroupCenter = new StudyUserGroupCenter();
        studyUserGroupCenter.setStudyId(studyId);
        studyUserGroupCenter.setUserId(userId);
        List<StudyUserGroupCenter> studyUserGroupCenterList = studyUserGroupCenterMapper.select(studyUserGroupCenter);
        return studyUserGroupCenterList;
    }



    /**
     * 保存用户勾选 中心-队列 数据
     * @param studyUserGroupCenterVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSelectedGroup(StudyUserGroupCenterVo studyUserGroupCenterVo){
        Boolean flag = Boolean.TRUE;
        StudyUserGroupCenter  studyUserGroupCenter = new StudyUserGroupCenter() ;
        BeanUtils.copyProperties(studyUserGroupCenterVo, studyUserGroupCenter);

        if(studyUserGroupCenterVo.getChecked().equals(Boolean.FALSE)){
            studyUserGroupCenterMapper.delete(studyUserGroupCenter);
        } else{
            studyUserGroupCenterMapper.insertSelective(studyUserGroupCenter);
        }
        return flag;
    }



    /**
     * 获取选中的用户列表
     *
     * @param selectUserIdList
     * @return
     */
    public List<StudyUserVo> getSelectUserList(List<Long> selectUserIdList) {
        List<StudyUserVo> selectUserList = new ArrayList<StudyUserVo>() ;

        if (selectUserIdList!=null && selectUserIdList.size()>0){
            // 获取有效的用户列表
            List<User> allUserList = userService.listUserInfo() ;

            if (allUserList!=null && allUserList.size()>0) {
                for (User user : allUserList) {
                    if (selectUserIdList.contains(user.getId())) {
                        StudyUserVo studyUserVo = new StudyUserVo() ;
                        BeanUtils.copyProperties(user,studyUserVo);
                        selectUserList.add(studyUserVo) ;
                    }
                }
            }
        }

        return selectUserList ;
    }




    /**
     * 获取选中的用户列表
     *
     * @param selectUserIdList
     * @return
     */
    public List<User> getStudyUserList(List<Long> selectUserIdList) {
        List<User> selectUserList = new ArrayList<User>() ;

        if (selectUserIdList!=null && selectUserIdList.size()>0){
            // 获取有效的用户列表
            List<User> allUserList = userService.listUserInfo() ;

            if (allUserList!=null && allUserList.size()>0) {
                for (User user : allUserList) {
                    if (selectUserIdList.contains(user.getId())) {
                        selectUserList.add(user) ;
                    }
                }
            }
        }

        return selectUserList ;
    }



    /**
     * 查询项目或角色下 - 未选择用户列表
     *
     * @param allUserList
     * @param selectUserIdList
     * @return
     */
    public List<StudyUserVo> getValidUserList(List<User> allUserList, List<Long> selectUserIdList) {
        List<StudyUserVo> selectUserList = new ArrayList<StudyUserVo>() ;

        if (allUserList!=null && allUserList.size()>0) {
            if (selectUserIdList!=null && selectUserIdList.size()>0){
                for (User user : allUserList) {
                    if (!selectUserIdList.contains(user.getId()) && user.getId()!=1) {//不等于管理员
                        StudyUserVo studyUserVo = new StudyUserVo() ;
                        BeanUtils.copyProperties(user,studyUserVo);
                        selectUserList.add(studyUserVo) ;
                    }
                }
            } else {
                for (User user : allUserList) {
                    if (user.getId()!=1) {//不等于管理员
                        StudyUserVo studyUserVo = new StudyUserVo() ;
                        BeanUtils.copyProperties(user,studyUserVo);
                        selectUserList.add(studyUserVo) ;
                    }
                }
            }
        }

        return selectUserList ;
    }


}
