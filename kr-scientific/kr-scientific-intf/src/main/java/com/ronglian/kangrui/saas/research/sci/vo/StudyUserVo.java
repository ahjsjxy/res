package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 15:40
 **/
@Data
public class StudyUserVo implements Serializable {
    private Long id;
    private String username;//用户名
    private String name;//用户姓名
    private String mobilePhone;//手机号
    private Long studyId ;//项目ID
    private Long roleId ;//角色ID
    private List<Long> userIdList ;
}
