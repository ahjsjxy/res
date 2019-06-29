package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 疾病关联的字段
 *
 * @author lanyan
 * @create 2019-03-19 10:46
 **/
@Data
public class DiseaseFieldVo implements Serializable {

    private Long id;//字段id
    private String label;//字段中文名
    private String name;//字段英文名
    private boolean checked ;//是否选中 (true：选中，false : 未选中)

    private List<Long> parentFieldList ;//所有父级字段id
    private List<Long> childFieldList; //所有子级字段id

}
