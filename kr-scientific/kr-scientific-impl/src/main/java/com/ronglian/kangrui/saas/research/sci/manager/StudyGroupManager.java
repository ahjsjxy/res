package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.sci.biz.StudyGroupBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyObjectBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudyGroupManager {

    @Autowired
    private StudyGroupBiz studyGroupBiz ;

    @Autowired
    private StudyObjectBiz studyObjectBiz ;


    /**
     * 校验队列名称不重复（1：项目下的队列不重复  2：未删除的队列）
     * @param name
     * @param studyId
     * @param studyGroupId
     * @return
     */
    public boolean checkStudyGroupNameExists(String name, Long studyId, Long studyGroupId) {
        return studyGroupBiz.checkStudyGroupNameExists(name, studyId, studyGroupId) ;
    }


    /**
     * 删除项目队列
     * @param studyGroupId
     * @return
     */
    public Msg deleteStudyGroupInfo(Long studyGroupId) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {

            StudyGroup entityStudyGroup = studyGroupBiz.selectById(studyGroupId) ;
            if (entityStudyGroup!=null && entityStudyGroup.getDeleted()==ResearchConsts.DELETED_NO) {

                // 判断当前队列下，是否有研究对象
                List<StudyObject> studyObjectList = studyObjectBiz.getStudyObjectByGroupId(studyGroupId) ;
                if (studyObjectList!=null && studyObjectList.size()>0) {
                    // 该队列存在研究对象，不允许删除
                    msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_NOT_ALLOWED);
                    msg.setDesc(ResearchConsts.STUDY_GROUP_NOT_DELETED_MSG);

                } else {
                    msg = studyGroupBiz.deleteStudyGroupInfo(studyGroupId) ;
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


}
