package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-06 11:39
 **/
@Data
public class StudyTreeVo implements Serializable {
    private Long id;
    private String nodeId ;//节点唯一ID
    private String name;// crf名称
    private Integer level;//层级(1: CRF，2：题组, 3:字典)
    private Integer parentId ;//上一层
    private Integer fieldNum ;//字段个数
    private List<CrfParentVo> childList ;//关联的crf form list
}
