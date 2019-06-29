package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-28 14:33
 **/
@Data
public class StudyRoleVo implements Serializable {
    private Long id;
    private String name;//角色名称
    private String remark ;//角色备注
    private Integer allowOperate;//是否允许操作(0：允许修改删除  1：禁止修改删除)
    private Integer level ;//层级(2：角色)
    private Long studyId;//项目ID

}
