package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 13:55
 **/
@Data
public class PortalButtonVo implements Serializable {
    private Long id; //按钮id
    private String code;//按钮code
    private String name;//按钮名称
    private boolean checked ;//是否选中 (true：选中，false : 未选中)
    private Long studyId;//项目ID
    private Long roleId ;//角色ID
}
