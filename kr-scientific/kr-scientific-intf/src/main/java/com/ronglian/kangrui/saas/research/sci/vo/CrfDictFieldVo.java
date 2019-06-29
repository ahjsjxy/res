package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CrfDictFieldVo implements Serializable {
    private Long id;
    private Long crfId; //crf题组id(crf的二级ID)
    private Long crfFormId; //crf的id(crf的一级ID)
    private Long dictId; //字典id
    private Long fieldId; //字段id
    private String fieldName ; // 数据库字段DB列名
    private Integer generateToDb ; // 动态表单字段是否创建（0：未创建  1：已创建）
    private Integer display; //是否显示(0：显示；1隐藏)
    private Integer deleted; //是否删除(0：未删除  1：删除)

    private Long studyId ;//studyId
    private List<Long> fieldIdList ;

}
