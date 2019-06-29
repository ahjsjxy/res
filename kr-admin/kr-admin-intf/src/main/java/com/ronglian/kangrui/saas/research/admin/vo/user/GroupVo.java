package com.ronglian.kangrui.saas.research.admin.vo.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2018-10-25 14:16
 **/
@Getter
@Setter
public class GroupVo implements Serializable {
    private Long id;

    private String code;

    private String name;

    private Integer deleted;

    private String description;

    private String repeatedName ;//重复名称

    private String menuIdStr ;//菜单ID以逗号分隔

    private Date crtTime;//创建时间

    private String crtUser;//创建人

    private Integer userCount ;//用户个数
}
