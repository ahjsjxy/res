package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-11 12:32
 **/
@Data
public class StudyVo implements Serializable {
    private Long id;
    private String name;//项目名称
    private String type ;//0：我发起的, 1：我参与的
    private Integer studyObjectNum ;//研究对象数
    private Date createTime; //创建时间
    private Integer fieldNum ;//字段个数
    private Integer status;//项目状态(1：创建中、2：已创建)
    private Long diseaseId; // 疾病名称

    private Integer deleteEnableFlag; //项目删除按钮权限，0无权，1有权
    private Integer settingEnableFlag; //项目设置按钮权限，0无权，1有权

    private Integer entranceEnableFlag =1; //进入都可以
    private Integer applyEnableFlag=0; //申请都不可以

    private Integer studyFlag;//项目标志(0: 单中心；1：多中心)，默认单中心

    private String createUserName;//创建人姓名
    private Long userId ;//创建人ID

}
