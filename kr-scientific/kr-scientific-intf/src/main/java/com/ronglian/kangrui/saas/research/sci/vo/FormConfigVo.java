package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  crf form配置节点
 *
 * @author lanyan
 * @create 2019-03-05 11:00
 **/
@Data
public class FormConfigVo implements Serializable {
    private Long id;
    private Long objectId;//研究对象ID
    private String name;// crf名称
    private String tableName;//crf form DB名称
    private Integer level;//层级(1: CRF，2：题组, 3:字典)
    private String description;// crf描述
    private String property;//crf属性
    private List<CrfFieldVo> fieldVoList ;//关联的题组集合
}
