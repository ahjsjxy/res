package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * crf关联的题组id
 *
 * @author lanyan
 * @create 2019-03-05 14:29
 **/
@Data
public class CrfVo implements Serializable {
    private Long id;
    private String nodeId ;
    private String name;//题组名称
    private Integer level;//层级(1: CRF，2：题组, 3:字典)
    private Integer parentId ;//crf form id
    private Integer fieldNum ;//字段个数
    private String description;//描述
    private Integer property;//属性
    private List<CrfDictionaryVo> childList ;//关联的字段集合
}
