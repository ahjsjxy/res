package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * study关联的crf表
 */
@Table(name = "study_crf")
public class StudyCrf implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * crf表单id
     */
    @Column(name = "crf_form_id")
    private Long crfFormId;

    /**
     * 项目id
     */
    @Column(name = "study_id")
    private Long studyId;

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


    public Long getCrfFormId() {
        return crfFormId;
    }

    public void setCrfFormId(Long crfFormId) {
        this.crfFormId = crfFormId;
    }

    /**
     * 获取项目id
     *
     * @return study_id - 项目id
     */
    public Long getStudyId() {
        return studyId;
    }

    /**
     * 设置项目id
     *
     * @param studyId 项目id
     */
    public void setStudyId(Long studyId) {
        this.studyId = studyId;
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
        StudyCrf other = (StudyCrf) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCrfFormId() == null ? other.getCrfFormId() == null : this.getCrfFormId().equals(other.getCrfFormId()))
            && (this.getStudyId() == null ? other.getStudyId() == null : this.getStudyId().equals(other.getStudyId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCrfFormId() == null) ? 0 : getCrfFormId().hashCode());
        result = prime * result + ((getStudyId() == null) ? 0 : getStudyId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", crfId=").append(crfFormId);
        sb.append(", studyId=").append(studyId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}