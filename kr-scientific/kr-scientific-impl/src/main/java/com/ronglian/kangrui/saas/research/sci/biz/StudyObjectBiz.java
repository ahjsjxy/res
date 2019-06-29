package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyGroupMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyObjectMapper;
import com.ronglian.kangrui.saas.research.sci.vo.StudyObjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudyObjectBiz extends BaseBiz<StudyObjectMapper, StudyObject> {

    @Autowired
    private StudyGroupMapper studyGroupMapper ;

    @Autowired
    private StudyObjectMapper studyObjectMapper ;

    @Autowired
    private StudyUserGroupCenterBiz studyUserGroupCenterBiz;



    /**
     * 插入一个研究对象
     * @param studyId
     * @param studyGroupId
     * @return
     */
    public StudyObject insertStudyObject(Long studyId, Long studyGroupId, Long centerId) {
        StudyObject studyObject = new StudyObject() ;
        studyObject.setStudyId(studyId);
        studyObject.setStudyGroupId(studyGroupId);
        studyObject.setCenterId(centerId);
        studyObject.setDeleted(ResearchConsts.DELETED_NO);
        studyObject.setCreateTime(new Date());
        studyObject.setUpdateTime(new Date());
        studyObjectMapper.insertSelective(studyObject) ;
        return studyObject ;
    }



    /**
     * 根据 study object id 更新下 更新时间
     * @param objectId
     * @return
     */
    public StudyObject updateStudyObjectById(Long objectId) {
        StudyObject studyObject = studyObjectMapper.selectByPrimaryKey(objectId) ;
        studyObject.setUpdateTime(new Date());
        studyObjectMapper.updateByPrimaryKeySelective(studyObject) ;
        return studyObject ;
    }


    public StudyObject selectStudyObject(Long objectId) {
        StudyObject studyObject = studyObjectMapper.selectByPrimaryKey(objectId) ;
        return studyObject ;
    }


    /**
     *  查询研究对象列表
     * @param studyId
     * @param studyGroupId
     * @return
     */
    public List<StudyObjectVo> queryStudyObjectList(Long studyId, Long centerId,Long studyGroupId,List<Long> centerIds) {
        StudyObjectVo temp = new StudyObjectVo() ;
        temp.setStudyId(studyId);
        temp.setCenterId(centerId);
        temp.setStudyGroupId(studyGroupId);

        List<StudyObjectVo> studyObjectList = studyObjectMapper.queryStudyObjectList(temp,centerIds) ;
        return studyObjectList ;
    }




    /**
     *  查询某课题下的 队列-列表
     * @param studyId
     * @return
     */
    public List<StudyGroup> getStudyGroupList(Long studyId,Long centerId,Long userId,List<Long> centerIds) {
        List<StudyGroup> studyGroupList = studyGroupMapper.selectStudyGroup(studyId,centerId,userId,centerIds) ;
        return  studyGroupList ;
    }




    /**
     * 根据主键ID更新  研究对象删除
     * @param objectId
     */
    public void delStudyObject(Long objectId) {
        StudyObject studyObject = studyObjectMapper.selectByPrimaryKey(objectId) ;
        studyObject.setDeleted(ResearchConsts.DELETED_YES);
        studyObject.setUpdateTime(new Date());
        studyObjectMapper.updateByPrimaryKeySelective(studyObject) ;
    }




    /**
     * 先查看 队列下是否有研究对象
     * @param studyGroupId
     * @return
     */
    public List<StudyObject> getStudyObjectByGroupId(Long studyGroupId) {
        StudyObject studyObject = new StudyObject() ;
        studyObject.setStudyGroupId(studyGroupId);
        studyObject.setDeleted(ResearchConsts.DELETED_NO);
        List<StudyObject> studyObjectList = studyObjectMapper.select(studyObject) ;
        return studyObjectList ;
    }


    /**
     * 先查看 队列下是否有研究对象
     * @param studyId
     * @return
     */
    public List<StudyObject> getStudyObjectByStudyId(Long studyId) {
        StudyObject studyObject = new StudyObject() ;
        studyObject.setStudyId(studyId);
        studyObject.setDeleted(ResearchConsts.DELETED_NO);
        List<StudyObject> studyObjectList = studyObjectMapper.select(studyObject) ;
        return studyObjectList ;
    }



    /**
     * 校验项目-是否有研究对象，有研究对象，不允许修改项目的基本信息
     * @param studyId
     * @return
     */
    public boolean checkHasStudyObjectData(Long studyId) {
        // 判断项目下是否有研究对象
        List<StudyObject> studyObjectList = this.getStudyObjectByStudyId(studyId) ;
        boolean hasStudyObject = (studyObjectList!=null && studyObjectList.size()>0) ;

        return hasStudyObject ;
    }






}
