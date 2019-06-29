package com.ronglian.kangrui.saas.research.sci.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * study关联的研究对象
 */
@Table(name = "study_object")
@Setter
@Getter
public class StudyObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "study_id")
    private Long studyId;

    /**
     * 队列id
     */
    @Column(name = "study_group_id")
    private Long studyGroupId;

    /**
     * 中心id
     */
    @Column(name = "center_id")
    private Long centerId;
    /**
     * 是否删除(0：未删除  1：删除)
     */
    private Integer deleted;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人id
     */
    @Column(name = "create_user")
    private Long createUser;

    /**
     * 创建人姓名
     */
    @Column(name = "create_user_name")
    private String createUserName;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新人id
     */
    @Column(name = "update_user")
    private Long updateUser;

    /**
     * 更新人姓名
     */
    @Column(name = "update_user_name")
    private String updateUserName;

    private static final long serialVersionUID = 1L;


}