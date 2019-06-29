package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.sci.entity.Dictionary;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.mapper.CrfMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.DictionaryMapper;
import com.ronglian.kangrui.saas.research.sci.vo.DictionaryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryBiz extends BaseBiz<DictionaryMapper,Dictionary> {
    @Autowired
    private DictionaryMapper dictionaryMapper ;

    @Autowired
    private StudyMapper studyMapper ;

    @Autowired
    private CrfMapper crfMapper ;




    /**
     * 查询字典管理列表
     * @param name
     * @return
     */
    public List<DictionaryVo> getDictionaryList(String name) {
        DictionaryVo dictionaryVo = new DictionaryVo() ;
        dictionaryVo.setName(name);
        return dictionaryMapper.getDictionaryList(dictionaryVo) ;
    }


    /**
     * 字典列表下，单个疾病选取的字典字段个数
     * @param diseaseId
     * @param name
     * @return
     */
    public List<DictionaryVo> getDictionaryByDiseaseId(Long diseaseId, String name) {
        DictionaryVo dictionaryVo = new DictionaryVo() ;
        dictionaryVo.setName(name);
        dictionaryVo.setDiseaseId(diseaseId);
        return dictionaryMapper.getDictionaryByDiseaseId(dictionaryVo) ;
    }



    /**
     * 根据字典id 查询字典详情
     * @param dictionaryId
     * @return
     */
    public Dictionary getDictionaryById(Long dictionaryId){
        Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(dictionaryId) ;
        return dictionary ;
    }


    /**
     * 获取项目下的字典列表(项目选择了疾病，按疾病的字典显示)
     * @param diseaseId
     * @param crf
     * @return
     */
    public List<DictionaryVo> getStudyDictionaryList(Long diseaseId, Crf crf){
        DictionaryVo dictionaryVo = new DictionaryVo() ;
        dictionaryVo.setDiseaseId(diseaseId);
        dictionaryVo.setProperty(crf.getProperty());
        dictionaryVo.setCrfId(crf.getId());
        return dictionaryMapper.getStudyDictionaryList(dictionaryVo) ;
    }



    /**
     * 查询字典管理列表(有属性的按照字典类型过滤)
     * @param crf
     * @return
     */
    public List<DictionaryVo> getDictionaryListByCrf(Crf crf) {
        DictionaryVo dictionaryVo = new DictionaryVo() ;
        dictionaryVo.setProperty(crf.getProperty());
        dictionaryVo.setCrfId(crf.getId());
        return dictionaryMapper.getDictionaryListByCrf(dictionaryVo) ;
    }


}
