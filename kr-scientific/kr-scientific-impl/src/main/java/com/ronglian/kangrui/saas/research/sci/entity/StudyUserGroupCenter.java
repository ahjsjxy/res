package com.ronglian.kangrui.saas.research.sci.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户关联的 中心队列表
 */
@Table(name = "study_user_group_center")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyUserGroupCenter implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * study表的主键id
     */
    @Column(name = "study_id")
    private Long studyId;

    /**
     * user表的主键id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 队列id
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * 中心id
     */
    @Column(name = "center_id")
    private Long centerId;

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
     * 获取study表的主键id
     *
     * @return study_id - study表的主键id
     */
    public Long getStudyId() {
        return studyId;
    }

    /**
     * 设置study表的主键id
     *
     * @param studyId study表的主键id
     */
    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    /**
     * 获取user表的主键id
     *
     * @return user_id - user表的主键id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置user表的主键id
     *
     * @param userId user表的主键id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取队列id
     *
     * @return group_id - 队列id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * 设置队列id
     *
     * @param groupId 队列id
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取中心id
     *
     * @return center_id - 中心id
     */
    public Long getCenterId() {
        return centerId;
    }

    /**
     * 设置中心id
     *
     * @param centerId 中心id
     */
    public void setCenterId(Long centerId) {
        this.centerId = centerId;
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
        StudyUserGroupCenter other = (StudyUserGroupCenter) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStudyId() == null ? other.getStudyId() == null : this.getStudyId().equals(other.getStudyId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getCenterId() == null ? other.getCenterId() == null : this.getCenterId().equals(other.getCenterId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStudyId() == null) ? 0 : getStudyId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getCenterId() == null) ? 0 : getCenterId().hashCode());
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
        sb.append(", groupId=").append(groupId);
        sb.append(", centerId=").append(centerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}