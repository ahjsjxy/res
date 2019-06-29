package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 研究对象新增Vo
 *
 * @author lanyan
 * @create 2019-03-11 17:38
 **/
@Data
public class FormConvertVo implements Serializable {

    private FormsVo formsVo ;
    private Long studyId ;
    private Long studyGroupId ;

}
