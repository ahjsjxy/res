package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.Dictionary;
import com.ronglian.kangrui.saas.research.sci.vo.DictionaryVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface DictionaryMapper extends Mapper<Dictionary> {

    /**
     * 查询字典列表
     * @param dictionaryVo
     * @return
     */
    public List<DictionaryVo> getDictionaryList(DictionaryVo dictionaryVo) ;


    /**
     * 字典列表下，单个疾病选取的字典字段个数
     * @param dictionaryVo
     * @return
     */
    public List<DictionaryVo> getDictionaryByDiseaseId(DictionaryVo dictionaryVo) ;


    /**
     * 获取项目下的字典列表(项目选择了疾病，按疾病的字典显示)
     * @param dictionaryVo
     * @return
     */
    public List<DictionaryVo> getStudyDictionaryList(DictionaryVo dictionaryVo) ;


    /**
     * 获取字典列表（所有的字典，如果题组有属性，则显示对应属性的字典，并获取题组在该字典已选中的字段个数）
     * @param dictionaryVo
     * @return
     */
    public List<DictionaryVo> getDictionaryListByCrf(DictionaryVo dictionaryVo) ;

}