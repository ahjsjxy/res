package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-13 16:14
 **/
@Data
public class FieldConvertVo implements Serializable {
    private Long dictionaryId ;//二级字典ID
    private FieldVo fieldVo ;
}
