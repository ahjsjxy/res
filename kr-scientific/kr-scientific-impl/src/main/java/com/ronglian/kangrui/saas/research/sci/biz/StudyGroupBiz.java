package com.ronglian.kangrui.saas.research.sci.biz;


import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUserGroupCenter;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyGroupMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.vo.StudyGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class StudyGroupBiz extends BaseBiz<StudyGroupMapper, StudyGroup> {

    @Autowired
    private StudyGroupMapper studyGroupMapper ;

    @Autowired
    private StudyUserGroupCenterBiz studyUserGroupCenterBiz ;


    /**
     * 校验队列名称不重复（1：项目下的队列不重复  2：未删除的队列）
     * @param name
     * @param studyGroupId
     * @return
     */
    public boolean checkStudyGroupNameExists(String name, Long studyId, Long studyGroupId) {
        boolean isRepeat = Boolean.FALSE ;

        // 准备查询参数
        StudyGroupVo studyGroupVoTemp = new StudyGroupVo() ;
        studyGroupVoTemp.setStudyId(studyId);
        List<StudyGroupVo> studyGroupVoList = studyGroupMapper.getGroupListByStudyId(studyGroupVoTemp) ;
        List<StudyGroupVo> repeatList = null ;
        if (studyGroupVoList!=null && studyGroupVoList.size()>0) {
            if (studyGroupId!=null) {
                StudyGroupVo temp = new StudyGroupVo() ;
                temp.setId(studyGroupId);
                temp.setStudyId(studyId);
                temp.setName(name);
                // 编辑的时候判断name是否重复
                repeatList = studyGroupMapper.checkStudyGroupNameExists(temp) ;
            } else {
                repeatList = studyGroupVoList.stream().filter(studyGroupVo -> name.equals(studyGroupVo.getName())).collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }





    /**
     * 删除队列信息
     * @param studyGroupId
     * @return
     */
    public Msg deleteStudyGroupInfo(Long studyGroupId) {
        Msg msg = new Msg() ;

        StudyGroup entityStudyGroup = this.selectById(studyGroupId) ;

        // 队列下没有研究对象，可以删除队列
        StudyGroup studyGroup = new StudyGroup() ;
        studyGroup.setId(studyGroupId);
        studyGroup.setDeleted(ResearchConsts.DELETED_YES);
        studyGroup.setUpdateTime(new Date());
        this.updateSelectiveById(studyGroup);

        // 删除studyGroupId 关联的 study_user_group_center
        StudyUserGroupCenter studyUserGroupCenter = new StudyUserGroupCenter() ;
        studyUserGroupCenter.setGroupId(studyGroupId);
        studyUserGroupCenter.setStudyId(entityStudyGroup.getStudyId());
        studyUserGroupCenterBiz.delete(studyUserGroupCenter);


        msg.setSucFlag(Boolean.TRUE);
        msg.setDesc(ResearchConsts.STUDY_GROUP_DEL_SUC_MSG);//删除队列成功

        return msg ;
    }

}
