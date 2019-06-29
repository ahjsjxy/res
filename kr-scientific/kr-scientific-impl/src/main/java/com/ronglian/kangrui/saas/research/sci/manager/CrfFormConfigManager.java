package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.FormBiz;
import com.ronglian.kangrui.saas.research.sci.vo.FormConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-22 18:17
 **/
@Service
@Slf4j
public class CrfFormConfigManager {

    @Autowired
    private FormBiz formBiz ;


    /**
     * 根据study id 查询 研究对象 列头可展示的form列表
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getNormalFormConfigVoList(Long studyId) {
        return formBiz.getNormalFormConfigVoList(studyId) ;
    }


}
