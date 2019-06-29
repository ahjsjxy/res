package com.ronglian.kangrui.saas.research.admin.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "base_hosp_center_dept_user")
public class HospCenterDeptUser implements Serializable {
    /**
     * 医疗机构科室ID
     */
    @Column(name = "center_dept_id")
    private Long centerDeptId;

    /**
     * 科室ID
     */
    @Column(name = "user_id")
    private Long userId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取医疗机构ID
     *
     * @return center_id - 医疗机构ID
     */
    public Long getCenterDeptId() {
        return centerDeptId;
    }

    /**
     * 设置医疗机构科室ID
     *
     * @param centerId 医疗机构ID
     */
    public void setCenterDeptId(Long centerDeptId) {
        this.centerDeptId = centerDeptId;
    }

    /**
     * 获取User ID
     *
     * @return dept_id - USER ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置UserID
     *
     * @param deptId 科室ID
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
        HospCenterDeptUser other = (HospCenterDeptUser) that;
        return (this.getCenterDeptId() == null ? other.getCenterDeptId() == null : this.getCenterDeptId().equals(other.getCenterDeptId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCenterDeptId() == null) ? 0 : getCenterDeptId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", centerDeptId=").append(centerDeptId);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}