package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * crf关联的字典
 *
 * @author lanyan
 * @create 2019-03-05 14:25
 **/
@Data
public class CrfDictionaryVo implements Serializable {
    private Long id;
    private String nodeId ;
    private String code;//编码
    private String name;//字典名称(二级)
    private Integer level;//层级(1: CRF，2：题组, 3:字典)
    private Integer parentId ;//crf form id
    private Integer fieldNum ;//字段个数
    private List<CrfFieldVo> fields;//crf关联的表单集合

}
