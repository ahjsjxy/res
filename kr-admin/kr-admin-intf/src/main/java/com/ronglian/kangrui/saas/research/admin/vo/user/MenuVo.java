package com.ronglian.kangrui.saas.research.admin.vo.user;

import lombok.Getter;
import lombok.Setter;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2018-10-26 13:29
 **/
@Getter
@Setter
public class MenuVo {

    private Long id;

    private String code;

    private String title;

    private String description;

    private Integer showType ; //展示类型(1：后端  2：前端)

    private Integer orderNum ;//菜单排序顺序

    private String routerName ;//前端router_name

    private String path;//前端router_path

    public Long groupId ;//角色组ID


    private String routerNameBack ;//后端router_name
    private String pathBack ;//后端router_path


    public Long userId ;//用户ID

}
