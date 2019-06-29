package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "study_center")
public class StudyCenter implements Serializable {
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
     * 医疗机构ID
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
     * 获取医疗机构ID
     *
     * @return center_id - 医疗机构ID
     */
    public Long getCenterId() {
        return centerId;
    }

    /**
     * 设置医疗机构ID
     *
     * @param centerId 医疗机构ID
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
        StudyCenter other = (StudyCenter) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStudyId() == null ? other.getStudyId() == null : this.getStudyId().equals(other.getStudyId()))
            && (this.getCenterId() == null ? other.getCenterId() == null : this.getCenterId().equals(other.getCenterId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStudyId() == null) ? 0 : getStudyId().hashCode());
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
        sb.append(", centerId=").append(centerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}