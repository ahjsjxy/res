package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:18
 **/
@Data
public class StudyRoleTreeVo implements Serializable {
    private Long id;
    private String name;//项目名称
    private Integer level ;//层级(1：项目)
    private List<StudyRoleVo> studyRoleVoList ;//角色列表
}
