package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-16 15:20
 **/
@Data
public class StudyGroupVo implements Serializable {
    private Long id;
    private Long studyId;//项目id
    private String name;//队列名称
    private String entryCondition;//入组条件
    private String excludeCondition;//排除条件
}
