package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class TreeEntity {
    private Long id;
    private String name;
    private Integer parentId ;//父级ID
    private Integer level ;// 层级: 1
    List<ChildEntity> list = new ArrayList<ChildEntity>();
}
