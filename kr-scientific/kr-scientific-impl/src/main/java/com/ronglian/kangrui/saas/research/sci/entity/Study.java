package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目列表
 */
@Table(name = "study")
public class Study implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 项目类型 1:前瞻性临床研究 2：回顾性临床研究 3：数据库及其它
     */
    private Integer type ;


    /**
     * 项目名称
     */
    private String name;


    /**
     * 试验分期(1: I期临床试验 2:Ⅱ期临床试验 3:Ⅲ期临床试验 4: Ⅳ期临床试验 5:生物等效性试验)
     */
    @Column(name = "test_stage")
    private Integer testStage ;


    /**
     * 入组数量
     */
    @Column(name = "entry_num")
    private Integer entryNum;

    /**
     * 分组设计（0：单臂试验；1：分组实验）
     */
    @Column(name = "group_type")
    private Integer groupType;




    /**
     * 盲法设置，0-开放，1-单盲，2-双盲
     */
    @Column(name = "blind_setting")
    private Integer blindSetting;

    /**
     * 随机方式，0-无，1-简单随机，2-分层随机
     */
    @Column(name = "random_way")
    private Integer randomWay;

    /**
     * 计划入组数量
     */
    @Column(name = "plan_entry_num")
    private Integer planEntryNum;

    /**
     * 治疗时间
     */
    @Column(name = "treat_time")
    private Integer treatTime;

    /**
     * 随访时间
     */
    @Column(name = "follow_time")
    private Integer followTime;



    /**
     * 疾病名称
     */
    @Column(name = "disease_id")
    private Long diseaseId;


    /**
     * 项目标志(0: 单中心；1：多中心)
     */
    @Column(name = "study_flag")
    private Integer studyFlag;


    /**
     * 是否删除(0：未删除  1：删除)
     */
    private Integer deleted;


    /**
     * 项目状态(1：创建中、2：已创建)
     */
    private Integer status;

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

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取项目名称
     *
     * @return name - 项目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置项目名称
     *
     * @param name 项目名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    /**
     * 试验分期
     * @return
     */
    public Integer getTestStage() {
        return testStage;
    }


    /**
     * 试验分期
     * @param testStage
     */
    public void setTestStage(Integer testStage) {
        this.testStage = testStage;
    }

    /**
     * 获取入组数量
     *
     * @return entryNum - 入组数量
     */
    public Integer getEntryNum() {
        return entryNum;
    }


    /**
     * 设置入组数量
     *
     * @param entryNum 入组数量
     */
    public void setEntryNum(Integer entryNum) {
        this.entryNum = entryNum;
    }

    /**
     * 获取分组设计（0：单臂试验；1：分组实验）
     *
     * @return group_type - 分组设计（0：单臂试验；1：分组实验）
     */
    public Integer getGroupType() {
        return groupType;
    }

    /**
     * 设置分组设计（0：单臂试验；1：分组实验）
     *
     * @param groupType 分组设计（0：单臂试验；1：分组实验）
     */
    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }



    /**
     * 获取是否删除(0：未删除  1：删除)
     *
     * @return deleted - 是否删除(0：未删除  1：删除)
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否删除(0：未删除  1：删除)
     *
     * @param deleted 是否删除(0：未删除  1：删除)
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人id
     *
     * @return create_user - 创建人id
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建人id
     *
     * @param createUser 创建人id
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取创建人姓名
     *
     * @return create_user_name - 创建人姓名
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置创建人姓名
     *
     * @param createUserName 创建人姓名
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取更新人id
     *
     * @return update_user - 更新人id
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新人id
     *
     * @param updateUser 更新人id
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取更新人姓名
     *
     * @return update_user_name - 更新人姓名
     */
    public String getUpdateUserName() {
        return updateUserName;
    }

    /**
     * 设置更新人姓名
     *
     * @param updateUserName 更新人姓名
     */
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }




    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getStudyFlag() {
        return studyFlag;
    }

    public void setStudyFlag(Integer studyFlag) {
        this.studyFlag = studyFlag;
    }

    public Integer getBlindSetting() {
        return blindSetting;
    }

    public void setBlindSetting(Integer blindSetting) {
        this.blindSetting = blindSetting;
    }

    public Integer getRandomWay() {
        return randomWay;
    }

    public void setRandomWay(Integer randomWay) {
        this.randomWay = randomWay;
    }

    public Integer getPlanEntryNum() {
        return planEntryNum;
    }

    public void setPlanEntryNum(Integer planEntryNum) {
        this.planEntryNum = planEntryNum;
    }

    public Integer getTreatTime() {
        return treatTime;
    }

    public void setTreatTime(Integer treatTime) {
        this.treatTime = treatTime;
    }

    public Integer getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Integer followTime) {
        this.followTime = followTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Study study = (Study) o;

        if (!id.equals(study.id))
            return false;
        if (type != null ? !type.equals(study.type) : study.type != null)
            return false;
        if (name != null ? !name.equals(study.name) : study.name != null)
            return false;
        if (testStage != null ? !testStage.equals(study.testStage) : study.testStage != null)
            return false;
        if (entryNum != null ? !entryNum.equals(study.entryNum) : study.entryNum != null)
            return false;
        if (groupType != null ? !groupType.equals(study.groupType) : study.groupType != null)
            return false;
        if (blindSetting != null ? !blindSetting.equals(study.blindSetting) : study.blindSetting != null)
            return false;
        if (randomWay != null ? !randomWay.equals(study.randomWay) : study.randomWay != null)
            return false;
        if (planEntryNum != null ? !planEntryNum.equals(study.planEntryNum) : study.planEntryNum != null)
            return false;
        if (treatTime != null ? !treatTime.equals(study.treatTime) : study.treatTime != null)
            return false;
        if (followTime != null ? !followTime.equals(study.followTime) : study.followTime != null)
            return false;
        if (diseaseId != null ? !diseaseId.equals(study.diseaseId) : study.diseaseId != null)
            return false;
        if (studyFlag != null ? !studyFlag.equals(study.studyFlag) : study.studyFlag != null)
            return false;
        if (deleted != null ? !deleted.equals(study.deleted) : study.deleted != null)
            return false;
        if (status != null ? !status.equals(study.status) : study.status != null)
            return false;
        if (createTime != null ? !createTime.equals(study.createTime) : study.createTime != null)
            return false;
        if (createUser != null ? !createUser.equals(study.createUser) : study.createUser != null)
            return false;
        if (createUserName != null ? !createUserName.equals(study.createUserName) : study.createUserName != null)
            return false;
        if (updateTime != null ? !updateTime.equals(study.updateTime) : study.updateTime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(study.updateUser) : study.updateUser != null)
            return false;
        return updateUserName != null ? updateUserName.equals(study.updateUserName) : study.updateUserName == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (testStage != null ? testStage.hashCode() : 0);
        result = 31 * result + (entryNum != null ? entryNum.hashCode() : 0);
        result = 31 * result + (groupType != null ? groupType.hashCode() : 0);
        result = 31 * result + (blindSetting != null ? blindSetting.hashCode() : 0);
        result = 31 * result + (randomWay != null ? randomWay.hashCode() : 0);
        result = 31 * result + (planEntryNum != null ? planEntryNum.hashCode() : 0);
        result = 31 * result + (treatTime != null ? treatTime.hashCode() : 0);
        result = 31 * result + (followTime != null ? followTime.hashCode() : 0);
        result = 31 * result + (diseaseId != null ? diseaseId.hashCode() : 0);
        result = 31 * result + (studyFlag != null ? studyFlag.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateUserName != null ? updateUserName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Study{" +
               "id=" + id +
               ", type=" + type +
               ", name='" + name + '\'' +
               ", testStage=" + testStage +
               ", entryNum=" + entryNum +
               ", groupType=" + groupType +
               ", blindSetting=" + blindSetting +
               ", randomWay=" + randomWay +
               ", planEntryNum=" + planEntryNum +
               ", treatTime=" + treatTime +
               ", followTime=" + followTime +
               ", diseaseId=" + diseaseId +
               ", studyFlag=" + studyFlag +
               ", deleted=" + deleted +
               ", status=" + status +
               ", createTime=" + createTime +
               ", createUser=" + createUser +
               ", createUserName='" + createUserName + '\'' +
               ", updateTime=" + updateTime +
               ", updateUser=" + updateUser +
               ", updateUserName='" + updateUserName + '\'' +
               '}';
    }
}