package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.vo.StudyGroupVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudyGroupMapper extends Mapper<StudyGroup> {

    /**
     * 根据项目id 获取项目的 队列 集合
     * @param studyGroupVo
     * @return
     */
    public List<StudyGroupVo> getGroupListByStudyId(StudyGroupVo studyGroupVo) ;


    /**
     * 校验队列名称不重复（1：项目下的队列不重复  2：未删除的队列）
     * @param studyGroupVo
     * @return
     */
    public List<StudyGroupVo> checkStudyGroupNameExists(StudyGroupVo studyGroupVo) ;

    public List<StudyGroup> selectStudyGroup(@Param("studyId")Long studyId,@Param("centerId")Long centerId,
                                             @Param("userId")Long userId,@Param("list")List<Long> list);

}