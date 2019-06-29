package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.admin.api.IHospCenterService;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.biz.*;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.vo.StudyAddVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyGroupVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyVo;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-11 12:28
 **/
@Service
@Slf4j
public class StudyManager {

    @Autowired
    private StudyBiz studyBiz ;

    @Autowired
    private StudyObjectBiz studyObjectBiz ;

    @Autowired
    private StudyGroupBiz studyGroupBiz ;

    @Autowired
    private StudyCenterBiz studyCenterBiz ;

    @Autowired
    private IHospCenterService iHospCenterService;

    @Autowired
    private IHospCenterService hospCenterService ;



    /**
     * 查询项目列表
     * @return
     */
    public List<StudyVo> getStudyList(StudyVo studyVo) {
        studyVo.setUserId(ShiroService.getCurrentUser().getId());
        studyVo.setStudyFlag((studyVo!=null && studyVo.getStudyFlag()!=null) ? studyVo.getStudyFlag() : ResearchConsts.STUDY_FLAG_SINGLE_CENTER);
        return studyBiz.getStudyList(studyVo) ;
    }



    /**
     * 批量删除study根据study id
     * @param studyIdStr
     * @return
     */
    public Msg deleteStudyById(String studyIdStr){
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            List<Long> studyIdList = Arrays.asList(studyIdStr.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            for (Long studyId : studyIdList) {
                Study study = studyBiz.selectById(studyId) ;
                study.setDeleted(ResearchConsts.DELETED_YES);
                study.setUpdateTime(new Date());
                studyBiz.updateSelectiveById(study);
            }

            msg.setSucFlag(Boolean.TRUE);
            msg.setDesc(ResearchConsts.STUDY_DEL_DEL_SUC_MSG);//删除项目成功

        } catch (Exception e) {
            log.error(e.getMessage());

            //删除异常
            msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_EXCEPTION);
        }

        return msg ;
    }


    /**
     *  项目-第一步保存
     *  编辑-保存
     *  新建项目基本信息-保存(1, 基本信息保存  2，项目状态为创建中)
     *
     * @return
     */
    public StudyAddVo saveStudyStep1(StudyAddVo studyAddVo) {
        try {
            // 校验项目名称重复
            boolean repeatFlag = this.checkStudyNameExists(studyAddVo.getName().trim(), studyAddVo.getStudyFlag(), studyAddVo.getId()) ;
            if (!repeatFlag) {
                // 新增或编辑 保存
                studyAddVo = studyBiz.saveStudyStep1(studyAddVo) ;

            }else {
                // 项目名称存在重复
                log.info(ResearchConsts.STUDY_NAME_EXISTS_REPEAT);
            }
        } catch (BeansException e) {
            log.error(e.getMessage());
        }

        return studyAddVo ;
    }





    /**
     * 项目-第二步保存(编辑和新建队列-保存)
     * @param studyAddVo
     * @return
     */
    public StudyAddVo saveStudyStep2(StudyAddVo studyAddVo) {
        if (studyAddVo == null || studyAddVo.getStudyGroupVoList() == null) {
            return null;
        }

        try {

            List<StudyGroupVo> studyGroupVoList = studyAddVo.getStudyGroupVoList();
            for (StudyGroupVo studyGroupVo : studyGroupVoList) {
                StudyGroup studyGroup = new StudyGroup();
                BeanUtils.copyProperties(studyGroupVo, studyGroup);

                if (studyGroupVo!=null && studyGroupVo.getId()!=null) {
                    // 编辑队列
                    studyGroup.setUpdateTime(new Date());
                    studyGroupBiz.updateSelectiveById(studyGroup); ;
                }else {
                    // 新建队列
                    studyGroup.setStudyId(studyAddVo.getId());
                    studyGroup.setDeleted(ResearchConsts.DELETED_NO);
                    studyGroup.setCreateTime(new Date());
                    studyGroup.setCreateUser(ShiroService.getCurrentUser().getId());
                    studyGroup.setUpdateTime(new Date());
                    studyGroupBiz.insertSelective(studyGroup);
                }
            }

        } catch (BeansException e) {
            e.printStackTrace();
        }

        return studyAddVo ;
    }



    /**
     * 查询项目分组根据项目ID
     * @param studyId
     * @return
     */
    public StudyAddVo getStudyGroupByStudyId(Long studyId) {
        StudyAddVo studyAddVo = new StudyAddVo() ;

        Study study = studyBiz.selectById(studyId) ;
        BeanUtils.copyProperties(study, studyAddVo);

        List<StudyGroupVo> studyGroupVoList = new ArrayList<StudyGroupVo>() ;
        StudyGroup studyGroup = new StudyGroup() ;
        studyGroup.setStudyId(studyId);
        studyGroup.setDeleted(ResearchConsts.DELETED_NO);
        List<StudyGroup> studyGroupList = studyGroupBiz.selectList(studyGroup) ;
        if (studyGroupList!=null && studyGroupList.size()>0) {
            for (StudyGroup temp : studyGroupList){
                StudyGroupVo studyGroupVo = new StudyGroupVo() ;
                BeanUtils.copyProperties(temp, studyGroupVo);
                studyGroupVoList.add(studyGroupVo) ;
            }
            studyAddVo.setStudyGroupVoList(studyGroupVoList);
        }
        return studyAddVo ;
    }



    /**
     * 根据id查询对象
     * @param studyId
     * @return
     */
    public Study selectById(Long studyId) {
        return studyBiz.selectById(studyId) ;
    }



    /**
     * 校验项目名称不重复（未删除）
     * @param name
     * @param studyFlag
     * @param studyId
     * @return
     */
    public boolean checkStudyNameExists(String name, Integer studyFlag, Long studyId) {
        boolean isRepeat = Boolean.FALSE ;

        StudyVo paramVo = new StudyVo() ;
        paramVo.setUserId(ShiroService.getCurrentUser().getId());
        paramVo.setStudyFlag(studyFlag);
        List<StudyVo> studyList = studyBiz.getStudyList(paramVo) ;
        List<StudyVo> repeatList = null ;
        if (studyList!=null && studyList.size()>0) {
            if (studyId!=null) {
                StudyVo temp = new StudyVo() ;
                temp.setId(studyId);
                temp.setName(name);
                temp.setUserId(ShiroService.getCurrentUser().getId());
                temp.setStudyFlag(studyFlag);
                // 编辑的时候判断name是否重复
                repeatList = studyBiz.checkStudyNameExists(temp) ;
            } else {
                repeatList = studyList.stream().filter(studyVo -> name.equals(studyVo.getName())).collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }




    /**
     * 校验项目-是否有研究对象，有研究对象，不允许修改项目的基本信息
     * @param studyId
     * @return
     */
    public boolean checkHasStudyObjectData(Long studyId) {
        return studyObjectBiz.checkHasStudyObjectData(studyId) ;
    }


    /**
     * 项目-第三步保存，修改项目的状态为待发布
     * @param studyId
     * @return
     */
    public StudyVo saveStudyStep3(Long studyId) {
        Study study = studyBiz.selectById(studyId) ;

        StudyVo studyVo = new StudyVo() ;
        if (study!=null && study.getStatus()!=null) {
            // 如果项目的状态为“创建中”，则修改项目的状态为“待发布”
            if (study.getStatus()== ResearchConsts.STUDY_STATUS_CREATING) {
                study.setStatus(ResearchConsts.STUDY_STATUS_NOT_PUBLISHED);//项目状态(2：待发布)
                study.setUpdateTime(new Date());
                study.setUpdateUser(ShiroService.getCurrentUser().getId());
                studyBiz.updateSelectiveById(study);

                BeanUtils.copyProperties(study, studyVo);
            }
        }

        return studyVo ;
    }




    /**
     * 项目-获取已选择的中心
     * @param studyId
     * @return
     */
    public List<HospCenterVo> getSelectedCenterList(Long studyId) {
        Study study = studyBiz.selectById(studyId) ;
        List<HospCenterVo> hospCenterVoList = null ;
        if (study.getStatus()==ResearchConsts.STUDY_STATUS_PUBLISHED) {
            // 已发布
            hospCenterVoList = studyCenterBiz.getSelectedCenterList(studyId) ;
        } else {
            // 待发布
            hospCenterVoList = this.getUserDefCenterList(ShiroService.getCurrentUser().getId()) ;
        }

        return hospCenterVoList ;
    }



    /**
     * 获取当前用户默认选中的中心
     * @param userId
     * @return
     */
    public List<HospCenterVo> getUserDefCenterList(Long userId){
        List<HospCenterVo> hospCenterVoList = new ArrayList<HospCenterVo>() ;
        HospCenterVo hospCenterVo = hospCenterService.selectDefaultCenter(userId) ;
        if (hospCenterVo!=null) {
            hospCenterVoList.add(hospCenterVo) ;
        }

        return hospCenterVoList ;
    }




    /**
     * 项目-获取未选择的中心
     * @param studyId
     * @return
     */
    public List<HospCenterVo> getValidCenterList(Long studyId) {
        Study study = studyBiz.selectById(studyId) ;
        List<HospCenterVo> hospCenterVoList = null ;
        if (study.getStatus()==ResearchConsts.STUDY_STATUS_PUBLISHED) {
            // 已发布
            hospCenterVoList = studyCenterBiz.getValidCenterList(studyId) ;
        } else {
            // 待发布
            hospCenterVoList = iHospCenterService.getHospCenterList(null);
            List<HospCenterVo> hospCenterVo = this.getUserDefCenterList(ShiroService.getCurrentUser().getId()) ;
            hospCenterVoList.removeAll(hospCenterVo);
        }

        return hospCenterVoList ;
    }


    /**
     * 项目-第四步保存
     * 1, 保存项目下的中心信息
     * 2, 设置项目状态为“已发布”
     *
     * @param studyAddVo
     * @return
     */
    public StudyAddVo saveStudyStep4(StudyAddVo studyAddVo) {
        // 保存项目下的中心信息
        if (studyAddVo == null || studyAddVo.getCenterList() == null) {
            return null;
        }

        studyAddVo = studyBiz.saveStudyStep4(studyAddVo) ;
        return studyAddVo ;
    }

}
