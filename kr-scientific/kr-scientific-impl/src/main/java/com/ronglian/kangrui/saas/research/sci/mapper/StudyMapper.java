package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.vo.ButtonCodeVo;
import com.ronglian.kangrui.saas.research.sci.vo.StudyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudyMapper extends Mapper<Study> {

    /**
     * 查询项目列表
     * @param studyVo
     * @return
     */
    public List<StudyVo> getStudyList(StudyVo studyVo) ;

    /**
     * 查询用户在特定项目下的权限
     * @param studyVo
     * @return
     */
    public List<ButtonCodeVo> getButtonListByStudyAndUserId(StudyVo studyVo);


    /**
     * 校验项目名称不重复（未删除）
     * @param studyVo
     * @return
     */
    public List<StudyVo> checkStudyNameExists(StudyVo studyVo) ;


    /**
     * 查询疾病关联的-未删除的项目列表
     * @param diseaseIdList
     * @return
     */
    public List<StudyVo> getStudyListByDiseaseIdList(@Param("diseaseIdList") List<Long> diseaseIdList) ;
}