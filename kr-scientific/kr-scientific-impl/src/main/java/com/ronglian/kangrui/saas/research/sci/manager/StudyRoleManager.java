package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.biz.StudyBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyRoleBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRole;
import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleTreeVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyRoleVo;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-28 16:51
 **/
@Service
@Slf4j
public class StudyRoleManager {

    @Autowired
    private StudyBiz studyBiz ;

    @Autowired
    private StudyRoleBiz studyRoleBiz ;




    /**
     * 获取某项目下的角色列表
     * @param studyId
     * @param roleName
     * @return
     */
    public StudyRoleTreeVo getStudyRoleList(Long studyId, String roleName) {
        StudyRoleTreeVo studyRoleTreeVo = new StudyRoleTreeVo() ;

        Study study = studyBiz.selectById(studyId) ;
        BeanUtils.copyProperties(study, studyRoleTreeVo);
        studyRoleTreeVo.setLevel(ResearchConsts.LEVEL_STUDY);//层级 1：项目

        List<StudyRoleVo> studyRoleVoList = studyRoleBiz.getStudyRoleList(studyId, roleName) ;
        studyRoleTreeVo.setStudyRoleVoList(studyRoleVoList);

        return studyRoleTreeVo ;
    }



    /**
     * 保存 项目-角色信息
     * @param studyRoleVo
     * @return
     */
    public StudyRoleVo saveStudyRoleInfo(StudyRoleVo studyRoleVo) {
        StudyRole studyRole = new StudyRole() ;
        BeanUtils.copyProperties(studyRoleVo, studyRole);

        if (studyRoleVo.getId()!=null) {
            //编辑
            studyRole.setUpdateTime(new Date());
            studyRole.setUpdateUser(ShiroService.getCurrentUser().getId());
            studyRoleBiz.updateSelectiveById(studyRole);
        } else {
            //新增
            studyRole.setAllowOperate(ResearchConsts.ALLOW_OPERATE_YES);//0：允许修改删除
            studyRole.setDeleted(ResearchConsts.DELETED_NO);
            studyRole.setCreateUser(ShiroService.getCurrentUser().getId());
            studyRole.setUpdateTime(new Date());
            studyRoleBiz.insertSelective(studyRole) ;
        }

        return studyRoleVo ;
    }


    /**
     * 根据角色ID 获取 项目-角色
     * @param roleId
     * @return
     */
    public StudyRoleVo getStudyRoleById(Long roleId) {
        StudyRole studyRole = studyRoleBiz.selectById(roleId) ;

        StudyRoleVo studyRoleVo = new StudyRoleVo() ;
        BeanUtils.copyProperties(studyRole, studyRoleVo);

        return studyRoleVo ;
    }



    /**
     *  校验角色名称，角色下不重复（未删除）
     * @param studyId
     * @param name
     * @param roleId
     * @return
     */
    public boolean checkNameExists(Long studyId, String name, Long roleId) {
        boolean isRepeat = Boolean.FALSE ;

        // 查询系统所有有效的角色名称
        List<StudyRoleVo> studyRoleVoList = studyRoleBiz.getStudyRoleList(studyId, null) ;
        List<StudyRoleVo> repeatList = null ;
        if (studyRoleVoList!=null && studyRoleVoList.size()>0) {
            if (roleId!=null) {
                StudyRoleVo temp = new StudyRoleVo() ;
                temp.setId(roleId);
                temp.setName(name);
                // 编辑的时候判断name是否重复
                repeatList = studyRoleBiz.checkNameExists(temp) ;
            } else {
                repeatList = studyRoleVoList.stream().filter(studyRoleVo -> name.equals(studyRoleVo.getName())) .collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }




    /**
     *  删除角色
     *  1, 把角色置为无效
     *  2, 删除角色下关联的用户
     *  3, 删除角色下关联的按钮
     *
     * @param roleId
     * @return
     */
    @Transactional
    public Msg deleteRoleInfo(Long roleId) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {

            StudyRole studyRole = studyRoleBiz.selectById(roleId) ;
            if (studyRole!=null && studyRole.getDeleted()==ResearchConsts.DELETED_NO) {
                if (checkRoleAllowOperate(roleId)) {
                    msg = studyRoleBiz.deleteRoleInfo(roleId) ;

                }else {
                    //不允许删除
                    msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_NOT_ALLOWED);
                    msg.setDesc(ResearchConsts.STUDY_ROLE_NOT_DELETED_MSG);
                }
            } else {
                // 主键ID无效
                msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_ID_INVALID);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            //删除异常
            msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_EXCEPTION);
        }

        return msg ;
    }



    /**
     *  判断当前角色是否允许删除，True表示允许
     * @param roleId
     * @return
     */
    private boolean checkRoleAllowOperate(Long roleId) {
        StudyRole temp = studyRoleBiz.selectById(roleId) ;
        // 判断该角色ID是否允许删除
        Integer allowOperate = (temp!=null && temp.getAllowOperate()!=null) ? temp.getAllowOperate() : null ;
        boolean flag =  (allowOperate==ResearchConsts.ALLOW_OPERATE_YES) ;
        return flag ;

}


}
