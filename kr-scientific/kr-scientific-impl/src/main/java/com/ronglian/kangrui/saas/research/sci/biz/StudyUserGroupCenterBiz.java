package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCenter;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUserGroupCenter;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyUserGroupCenterMapper;
import com.ronglian.kangrui.saas.research.sci.util.ListDifferentUtil;
import com.ronglian.kangrui.saas.research.sci.vo.StudyAddVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyUserGroupCenterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 11:40
 **/
@Service
public class StudyUserGroupCenterBiz extends BaseBiz<StudyUserGroupCenterMapper, StudyUserGroupCenter> {

    @Autowired
    private StudyUserGroupCenterMapper studyUserGroupCenterMapper;

    @Autowired
    private StudyCenterBiz studyCenterBiz ;



    /**
     * 根据登陆用户ID和项目ID，获取中心权限列表（若有队列ID，则继续过滤队列下的中心列表）
     * @param studyId
     * @param userId
     * @param studyGroupId
     * @return
     */
    public List<StudyUserGroupCenter> selectCenterByStudyAndUser(Long studyId, Long userId, Long studyGroupId) {
        StudyUserGroupCenterVo studyUserGroupCenterVo = new StudyUserGroupCenterVo() ;
        studyUserGroupCenterVo.setStudyId(studyId);
        studyUserGroupCenterVo.setUserId(userId);
        studyUserGroupCenterVo.setGroupId(studyGroupId);
        return studyUserGroupCenterMapper.selectCenterByStudyAndUser(studyUserGroupCenterVo);
    }




    /**
     * 原来项目下的中心和新勾选的中心对比，找出删除的中心列表，删除掉中心center_id 关联的study_user_group_center
     *
     * @param studyAddVo
     */
    public void deleteInfo(StudyAddVo studyAddVo) {

        // 查询项目下老的中心列表
        StudyCenter studyCenter = new StudyCenter() ;
        studyCenter.setStudyId(studyAddVo.getId());
        List<StudyCenter> oldStudyCenterList = studyCenterBiz.selectList(studyCenter) ;

        // 获取新的中心列表
        List<HospCenterVo> newCenterList = studyAddVo.getCenterList() ;

        List<Long> oldStudyIdList = null ;
        if (oldStudyCenterList!=null && oldStudyCenterList.size()>0) {
            oldStudyIdList = oldStudyCenterList.stream().map(StudyCenter::getCenterId).collect(Collectors.toList());
        }

        List<Long> newStudyIdList = null ;
        if (newCenterList!=null && newCenterList.size()>0) {
            newStudyIdList = newCenterList.stream().map(HospCenterVo::getId).collect(Collectors.toList());
        }

        // 找出删除的中心ID集合，同时删除
        if (oldStudyCenterList!=null && oldStudyCenterList.size()>0){
            List<Long> deleteList = ListDifferentUtil.getDeleteList(oldStudyIdList, newStudyIdList) ;
            if (deleteList!=null && deleteList.size()>0) {
                for (Long centerId : deleteList) {
                    StudyUserGroupCenter studyUserGroupCenter = new StudyUserGroupCenter() ;
                    studyUserGroupCenter.setStudyId(studyAddVo.getId());
                    studyUserGroupCenter.setCenterId(centerId);
                    this.delete(studyUserGroupCenter);
                }
            }
        }
    }


}
