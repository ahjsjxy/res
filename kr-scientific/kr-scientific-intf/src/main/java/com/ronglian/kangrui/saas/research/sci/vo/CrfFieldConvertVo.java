package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-19 10:47
 **/
@Data
public class CrfFieldConvertVo implements Serializable {
    private Long crfId ;
    private Long dictionaryId ;
    private List<DiseaseFieldVo> fieldVoList ;
}
