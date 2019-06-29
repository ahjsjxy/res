package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.DictionaryBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.Dictionary;
import com.ronglian.kangrui.saas.research.sci.entity.Study;
import com.ronglian.kangrui.saas.research.sci.vo.DictionaryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-12 18:00
 **/
@Service
public class DictionaryManager {

    @Autowired
    private DictionaryBiz dictionaryBiz ;



    /**
     * 查询字典管理列表
     * @param name
     * @return
     */
    public List<DictionaryVo> getDictionaryList(String name) {
        return dictionaryBiz.getDictionaryList(name) ;
    }


    /**
     * 字典列表下，单个疾病选取的字典字段个数
     * @param diseaseId
     * @param name
     * @return
     */
    public List<DictionaryVo> getDictionaryByDiseaseId(Long diseaseId, String name) {
        return dictionaryBiz.getDictionaryByDiseaseId(diseaseId, name) ;
    }



    /**
     * 根据字典id 查询字典详情
     * @param dictionaryId
     * @return
     */
    public Dictionary getDictionaryById(Long dictionaryId){
        return dictionaryBiz.getDictionaryById(dictionaryId) ;
    }




    /**
     * 根据项目ID 过滤项目下的疾病字典列表
     * @return
     */
    public List<DictionaryVo> getDictListByStudyId(Study study, Crf crf) {
        if (crf!=null && crf.getProperty()!=null) {
            // 题组的属性如果选择“初始”，表示全部
            Integer property = (crf.getProperty()!=ResearchConsts.CRF_PROPERTY_INIT) ? crf.getProperty() : null ;
            crf.setProperty(property);
        }


        // 获取项目下的字典列表(项目选择了疾病，按疾病的字典显示，同时题组属性过滤；项目没选择疾病，就显示所有的字典，同时题组属性过滤)
        List<DictionaryVo> dictionaryVoList = (study!=null && study.getDiseaseId()!=null) ? dictionaryBiz.getStudyDictionaryList(study.getDiseaseId(), crf) :
                dictionaryBiz.getDictionaryListByCrf(crf) ;

        return dictionaryVoList ;
    }

}
