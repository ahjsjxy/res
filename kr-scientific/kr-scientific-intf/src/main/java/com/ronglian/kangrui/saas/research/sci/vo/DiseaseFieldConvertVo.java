package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-20 9:56
 **/
@Data
public class DiseaseFieldConvertVo implements Serializable {
    private Long diseaseId ;
    private Long dictionaryId ;
    private List<DiseaseFieldVo> diseaseFieldVoList ;
}
