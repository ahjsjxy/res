package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChildEntity {
    private Long id;
    private String name;
    private Boolean checked;
    private Long parentId ;//父级ID
    private Integer level ;// 层级: 2
}
