package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-05 17:34
 **/
@Data
public class CrfParentVo implements Serializable {
    private Long id;
    private String nodeId ;
    private String name;// crf名称
    private Integer level;//层级(1: CRF，2：题组, 3:字典)
    private Integer parentId ;//上一层
    private Integer fieldNum ;//字段个数
    private String description;//描述
    private Integer property;//  crf/题组属性（1：对象、2：随访、3：访视、4：其它）
    private List<CrfVo> childList ;//关联的题组集合
}
