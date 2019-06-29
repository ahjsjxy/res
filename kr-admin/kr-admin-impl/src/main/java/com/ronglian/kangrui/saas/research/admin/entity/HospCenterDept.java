package com.ronglian.kangrui.saas.research.admin.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "base_hosp_center_dept")
public class HospCenterDept implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 医疗机构ID
     */
    @Column(name = "center_id")
    private Long centerId;

    /**
     * 科室ID
     */
    @Column(name = "dept_id")
    private Long deptId;

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

    /**
     * 获取科室ID
     *
     * @return dept_id - 科室ID
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置科室ID
     *
     * @param deptId 科室ID
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
        HospCenterDept other = (HospCenterDept) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCenterId() == null ? other.getCenterId() == null : this.getCenterId().equals(other.getCenterId()))
            && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCenterId() == null) ? 0 : getCenterId().hashCode());
        result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", centerId=").append(centerId);
        sb.append(", deptId=").append(deptId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}