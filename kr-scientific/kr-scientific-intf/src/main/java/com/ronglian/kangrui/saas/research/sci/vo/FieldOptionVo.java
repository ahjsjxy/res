package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 关联字段的下拉选项
 *
 * @author lanyan
 * @create 2019-03-05 11:18
 **/
@Data
public class FieldOptionVo implements Serializable {
    private Long id;
    private Long fieldId;//关联的字段id
    private String fieldOption;//下拉选项值
    private BigDecimal displayOrder;//下拉选项值的显示顺序
    private Integer status;//是否启用(0：启用  1：未启用)
    private Integer deleted;//是否删除(0：未删除  1：删除)

}
