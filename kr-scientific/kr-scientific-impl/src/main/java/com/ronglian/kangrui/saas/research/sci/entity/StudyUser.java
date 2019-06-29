package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "study_user")
public class StudyUser implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 项目ID
     */
    @Column(name = "study_id")
    private Long studyId;

    /**
     * 成员ID
     */
    @Column(name = "user_id")
    private Long userId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取项目ID
     *
     * @return study_id - 项目ID
     */
    public Long getStudyId() {
        return studyId;
    }

    /**
     * 设置项目ID
     *
     * @param studyId 项目ID
     */
    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    /**
     * 获取成员ID
     *
     * @return user_id - 成员ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置成员ID
     *
     * @param userId 成员ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        StudyUser other = (StudyUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStudyId() == null ? other.getStudyId() == null : this.getStudyId().equals(other.getStudyId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStudyId() == null) ? 0 : getStudyId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", studyId=").append(studyId);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}