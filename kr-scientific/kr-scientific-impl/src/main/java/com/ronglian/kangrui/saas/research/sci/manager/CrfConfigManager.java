package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.vo.CrfFormVo;
import com.ronglian.kangrui.saas.research.sci.vo.CrfParentVo;
import com.ronglian.kangrui.saas.research.sci.vo.CrfVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-19 15:06
 **/
@Service
public class CrfConfigManager {
    @Autowired
    private FormConfigManager formConfigManager ;

    @Autowired
    private CrfManager crfManager ;


    /**
     *  初始化 CrfParentVo
     * @param crfFormVo
     * @return
     */
    public CrfParentVo initCrfParentVo(CrfFormVo crfFormVo) {
        CrfParentVo crfParentVo = new CrfParentVo() ;

        // 新建/编辑 CRF form 保存
        Msg msg = crfManager.saveCrfForm(crfFormVo) ;
        if (msg.getSucFlag()) {
            // 初始化 CrfParentVo
            Crf crf = (Crf)msg.getData() ;
            BeanUtils.copyProperties(crf, crfParentVo);
            crfParentVo.setLevel(ResearchConsts.STUDY_TREE_LEVEL_CRF_FORM);//树形结构-CRF表单级别
            crfParentVo.setParentId(crfFormVo.getStudyId().intValue());
            crfParentVo.setNodeId("crf_form_".concat(String.valueOf(crf.getId())));

            // 查询子级节点
            crfParentVo = formConfigManager.getChildListAndCrfParentFieldNum(crfParentVo) ;
        }

        return crfParentVo ;
    }


    /**
     * 初始化 CrfVo
     * @param crfFormVo
     * @return
     */
    public CrfVo initCrfVo(CrfFormVo crfFormVo) {
        CrfVo crfVo = new CrfVo() ;

        // 新建/编辑 题组保存
        Msg msg = crfManager.saveCrfTestlets(crfFormVo) ;
        if (msg.getSucFlag()) {
            // 初始化 CrfVo
            Crf crf = (Crf)msg.getData() ;
            BeanUtils.copyProperties(crf, crfVo);
            crfVo.setLevel(ResearchConsts.STUDY_TREE_LEVELE_CRF_TESTLETS);//树形结构- 题组级别
            crfVo.setParentId(crfFormVo.getParentId().intValue());
            crfVo.setNodeId("crf_".concat(String.valueOf(crf.getId())));
        }

        return crfVo ;
    }

}
