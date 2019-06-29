package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.sci.vo.StudyObjectVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudyObjectMapper extends Mapper<StudyObject> {

    /**
     * 根据项目id 获取研究对象列表
     * @param studyObjectVo
     * @return
     */
    public List<StudyObjectVo> queryStudyObjectList(@Param("studyObjectVo") StudyObjectVo studyObjectVo,@Param("list") List<Long> list) ;



}