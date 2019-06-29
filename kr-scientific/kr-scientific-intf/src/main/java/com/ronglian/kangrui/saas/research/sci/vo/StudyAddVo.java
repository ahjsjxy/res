package com.ronglian.kangrui.saas.research.sci.vo;

import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-16 11:50
 **/
@Data
public class StudyAddVo implements Serializable {
    private Long id;
    private Integer type ;//项目类型 1:前瞻性临床研究 2：回顾性临床研究 3：数据库及其它
    private String name;//项目名称
    private Integer testStage ;//试验分期(1: I期临床试验 2:Ⅱ期临床试验 3:Ⅲ期临床试验 4: Ⅳ期临床试验 5:生物等效性试验)
    private Integer entryNum ;//入组数量
    private Integer groupType ;//分组设计(1:单臂试验  2:分组试验)
    private Integer blindSetting ; //盲法设置，0-开放，1-单盲，2-双盲
    private Integer randomWay ; //随机方式，0-无，1-简单随机，2-分层随机
    private Integer planEntryNum ; //计划入组数量
    private Integer treatTime ; //治疗时间
    private Integer followTime ; //随访时间
    private Long diseaseId ;//疾病ID
    private Integer deleted;//是否删除(0：未删除  1：删除)
    private Integer status;//项目状态(1：创建中、2：已创建)
    private Integer studyFlag;//项目标志(0: 单中心；1：多中心)，默认单中心
    private List<StudyGroupVo> studyGroupVoList ;//分组集合
    private List<HospCenterVo> centerList ;//医疗机构集合
}
