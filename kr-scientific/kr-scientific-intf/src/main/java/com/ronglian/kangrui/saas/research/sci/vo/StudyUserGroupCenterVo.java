package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-06-19 14:18
 **/
@Data
public class StudyUserGroupCenterVo implements Serializable {
    private Long studyId;//项目ID
    private Long userId;//用户ID
    private Long groupId;//队列ID
    private Long centerId;//中心ID
    private Boolean checked ;//是否选中

}
