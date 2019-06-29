package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-15 20:46
 **/
@Data
public class DiseaseVo implements Serializable {
    private Long id;
    private String code;
    private String name;
    private Integer dictNum ;//字典数
    private Integer fieldNum ;//字段数
    private Integer status;//是否开启(0：开启  1：关闭)


}
