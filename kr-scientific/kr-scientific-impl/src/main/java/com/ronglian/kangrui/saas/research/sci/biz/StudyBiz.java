package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.sci.consts.ButtonConstant;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.vo.ButtonCodeVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyAddVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyVo;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class StudyBiz extends BaseBiz<StudyMapper, Study> {

    @Autowired
    private StudyMapper studyMapper ;

    @Autowired
    private StudyCenterBiz studyCenterBiz ;

    @Autowired
    private StudyRoleBiz studyRoleBiz ;

    @Autowired
    private StudyUserGroupCenterBiz studyUserGroupCenterBiz ;



    /**
     * 查询项目列表
     * @return
     */
    public List<StudyVo> getStudyList(StudyVo studyVo) {
        List<StudyVo> studyList = studyMapper.getStudyList(studyVo);

        // 每一个项目，获取当前登录用户 是否有（设置和删除按钮）权限
        studyList = this.getStudyListButtonList(studyList) ;

        return studyList ;
    }



    /**
     * 项目列表的每一个项目，获取当前登录用户 是否有（设置和删除按钮）权限
     * @param studyList
     * @return
     */
    private List<StudyVo> getStudyListButtonList(List<StudyVo> studyList) {
        for (StudyVo each : studyList) {
            each.setUserId(ShiroService.getCurrentUser().getId());
            List<ButtonCodeVo> list = studyMapper.getButtonListByStudyAndUserId(each);
            List<String> codes = list.stream().map(button->button.getCode()).collect(Collectors.toList());
            each.setDeleteEnableFlag(codes.contains(ButtonConstant.STUDY_DELETE) ? ButtonConstant.BUTTON_ENABLE : ButtonConstant.BUTTON_DISABLE);
            each.setSettingEnableFlag(codes.contains(ButtonConstant.STUDY_SETTING)? ButtonConstant.BUTTON_ENABLE : ButtonConstant.BUTTON_DISABLE);
        }

        return studyList ;
    }




    /**
     * 校验项目名称不重复（未删除）
     * @param temp
     * @return
     */
    public List<StudyVo> checkStudyNameExists(StudyVo temp) {
        return studyMapper.checkStudyNameExists(temp) ;
    }



    /**
     * 判断疾病ID是否有 关联的项目，1：存在未删除的项目，不允许删除
     * @param objectIdList
     * @return
     */
    public List<StudyVo> getStudyListByDiseaseIdList(List<Long> objectIdList) {
        return studyMapper.getStudyListByDiseaseIdList(objectIdList) ;
    }




    /**
     * 项目-第四步保存
     * 1, 保存项目下的中心信息
     * 2, 获取项目勾选的中心，更新study_user_group_center
     * 2, 设置项目状态为“已发布”
     *
     * @param studyAddVo
     */
    @Transactional
    public StudyAddVo saveStudyStep4(StudyAddVo studyAddVo) {

        // 原来项目下的中心和新勾选的中心对比，找出删除的中心列表，删除掉中心center_id 关联的study_user_group_center
        studyUserGroupCenterBiz.deleteInfo(studyAddVo) ;


        //先删除项目下的原来中心，新建-项目关联的中心
        studyCenterBiz.saveStudyCenterInfo(studyAddVo) ;


        //修改项目的状态-已发布
        Study study = this.selectById(studyAddVo.getId()) ;
        if (study!=null && study.getStatus()!=null &&
                study.getStatus()== ResearchConsts.STUDY_STATUS_NOT_PUBLISHED){
            study.setStatus(ResearchConsts.STUDY_STATUS_PUBLISHED);//项目状态(3：已发布)
        }
        study.setId(studyAddVo.getId());
        study.setUpdateTime(new Date());
        study.setUpdateUser(ShiroService.getCurrentUser().getId());
        this.updateSelectiveById(study);


        return studyAddVo ;
    }



    /**
     * 新建项目基本信息-保存(1, 基本信息保存  2，项目状态为创建中)
     * @param study
     * @return
     */
    @Transactional
    public Study saveStudyStep1(Study study) {
        // 1, 新建项目-第一步基本信息
        study.setStatus(ResearchConsts.STUDY_STATUS_CREATING);//1：创建中
        study.setDeleted(ResearchConsts.DELETED_NO);
        study.setCreateTime(new Date());
        study.setCreateUser(ShiroService.getCurrentUser().getId());
        study.setCreateUserName(ShiroService.getCurrentUser().getUsername());
        study.setUpdateTime(new Date());
        study = this.insertSelective(study) ;


        // 2, 给项目创建角色(每个项目默认创建4个角色)  3, 每个角色赋值按钮权限  4, 项目创建者为默认为项目管理员
        studyRoleBiz.addStudyRoleInfo(study.getId());

        return study ;
    }


    /**
     * 编辑保存
     *
     * @param study
     * @return
     */
    public Study updateStudyStep1(Study study) {
        Study oldStudy = this.selectById(study.getId()) ;
        study.setStudyFlag(oldStudy.getStudyFlag());
        study.setDeleted(oldStudy.getDeleted());
        study.setStatus(study.getStatus());

        // 编辑项目-第一步基本信息
        study.setUpdateTime(new Date());
        study.setUpdateUser(ShiroService.getCurrentUser().getId());
        this.updateSelectiveById(study);

        return study ;
    }




    /**
     * 新建项目基本信息-保存(1, 基本信息保存  2，项目状态为创建中)
     *
     * @param studyAddVo
     * @return
     */
    public StudyAddVo saveStudyStep1(StudyAddVo studyAddVo) {

        Study study = new Study() ;
        BeanUtils.copyProperties(studyAddVo, study);

        if (studyAddVo!=null && studyAddVo.getId()!=null) {
            // 编辑保存
            study = this.updateStudyStep1(study) ;

        } else {
            // 新增保存
            study = this.saveStudyStep1(study) ;
        }
        BeanUtils.copyProperties(study, studyAddVo);

        return studyAddVo ;
    }


}
