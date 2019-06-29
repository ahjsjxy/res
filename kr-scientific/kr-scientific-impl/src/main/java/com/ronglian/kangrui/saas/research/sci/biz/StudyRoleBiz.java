package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.consts.RoleTypeConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRole;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleButton;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleUser;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUser;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleButtonMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyRoleUserMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyUserMapper;

import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleVo;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-28 14:23
 **/
@Service
public class StudyRoleBiz extends BaseBiz<StudyRoleMapper, StudyRole> {

    @Autowired
    private StudyRoleMapper studyRoleMapper ;

    @Autowired
    private StudyRoleButtonMapper studyRoleButtonMapper ;

    @Autowired
    private StudyRoleUserMapper studyRoleUserMapper ;

    @Autowired
    private StudyUserMapper studyUserMapper ;

    @Autowired
    private StudyRoleUserBiz studyRoleUserBiz ;

    @Autowired
    private StudyRoleButtonBiz studyRoleButtonBiz ;





    /**
     *   给项目创建角色(每个项目默认创建4个角色)
     *   每个角色赋值按钮权限
     *   项目创建者为默认为项目管理员
     *
     * @param studyId
     */
    public void addStudyRoleInfo(Long studyId){
        // 新建项目-角色
        for (RoleTypeConsts.RoleTypeEnum roleTypeEnum : RoleTypeConsts.RoleTypeEnum.values()) {
            StudyRole studyRole = new StudyRole() ;
            studyRole.setName(roleTypeEnum.getName());
            studyRole.setAllowOperate(ResearchConsts.ALLOW_OPERATE_NO);//1：禁止修改删除
            studyRole.setDeleted(ResearchConsts.DELETED_NO);//0：未删除
            studyRole.setCreateUser(ShiroService.getCurrentUser().getId());
            studyRole.setUpdateTime(new Date());
            studyRole.setStudyId(studyId);
            studyRole = this.insertSelective(studyRole) ;


            // 角色赋值按钮权限
            List<String> buttonCodeList = Arrays.asList(roleTypeEnum.getButtonArray());
            for (String buttonCode: buttonCodeList) {
                StudyRoleButton studyRoleButton = new StudyRoleButton() ;
                studyRoleButton.setButtonCode(buttonCode);
                studyRoleButton.setRoleId(studyRole.getId());
                studyRoleButtonMapper.insertSelective(studyRoleButton) ;
            }

            // 项目创建者为 【项目管理员, 数据管理员, 数据录入员, 数据分析员】4个角色都有
            //if (roleTypeEnum.getName().equals(RoleTypeConsts.STUDY_ADMIN)) {
                StudyRoleUser studyRoleUser = new StudyRoleUser() ;
                studyRoleUser.setRoleId(studyRole.getId());
                studyRoleUser.setUserId(ShiroService.getCurrentUser().getId());
                studyRoleUserMapper.insertSelective(studyRoleUser) ;
            //}

        }

        // 项目创建用户
        StudyUser studyUser = new StudyUser() ;
        studyUser.setStudyId(studyId);
        studyUser.setUserId(ShiroService.getCurrentUser().getId());
        studyUserMapper.insertSelective(studyUser) ;

    }


    /**
     * 获取某项目下的角色列表
     * @param studyId
     * @param roleName
     * @return
     */
    public List<StudyRoleVo> getStudyRoleList(Long studyId, String roleName) {
        StudyRoleVo studyRoleVo = new StudyRoleVo() ;
        studyRoleVo.setStudyId(studyId);
        studyRoleVo.setName(roleName);
        return studyRoleMapper.getStudyRoleList(studyRoleVo) ;
    }


    /**
     * 校验 项目下的角色名称是否重复
     * @param studyRoleVo
     * @return
     */
    public List<StudyRoleVo> checkNameExists(StudyRoleVo studyRoleVo) {
        return studyRoleMapper.checkNameExists(studyRoleVo) ;
    }


    /**
     * 根据角色ID删除  1, 把角色置为无效  2, 删除角色下关联的用户  3, 删除角色下关联的按钮
     *
     * @return
     */
    public Msg deleteRoleInfo(Long roleId) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            // 1, 把角色置为无效
            StudyRole studyRole = this.selectById(roleId) ;
            studyRole.setDeleted(ResearchConsts.DELETED_YES);
            studyRole.setUpdateTime(new Date());
            studyRole.setUpdateUser(ShiroService.getCurrentUser().getId());
            this.updateSelectiveById(studyRole);

            // 2, 删除角色下关联的用户
            StudyRoleUser studyRoleUser = new StudyRoleUser() ;
            studyRoleUser.setRoleId(roleId);
            studyRoleUserBiz.delete(studyRoleUser);


            // 3, 删除角色下关联的按钮
            StudyRoleButton studyRoleButton = new StudyRoleButton() ;
            studyRoleButton.setRoleId(roleId);
            studyRoleButtonBiz.delete(studyRoleButton);

            msg.setSucFlag(Boolean.TRUE);
            msg.setDesc(ResearchConsts.STUDY_ROLE_DEL_SUC_MSG);

        } catch (Exception e) {
            msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_EXCEPTION);
            e.printStackTrace();
        }

        return msg ;
    }


}
