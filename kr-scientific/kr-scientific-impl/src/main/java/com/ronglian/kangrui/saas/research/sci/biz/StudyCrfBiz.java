package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCrf;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyCrfMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-06-24 17:00
 **/
@Service
public class StudyCrfBiz extends BaseBiz<StudyCrfMapper, StudyCrf> {

    @Autowired
    private StudyCrfMapper studyCrfMapper ;


    /**
     * 获取crf id 所属的项目id
     *
     * @param crfFormId
     * @return
     */
    public StudyCrf getStudyIdByCrfFormId(Long crfFormId) {
        return studyCrfMapper.getStudyIdByCrfFormId(crfFormId) ;
    }



    /**
     *  查询有效的CRF表单
     * @param studyId
     * @return
     */
    public List<StudyCrf> getCrfListByStudyId(Long studyId) {
        return studyCrfMapper.getCrfListByStudyId(studyId) ;
    }
}
