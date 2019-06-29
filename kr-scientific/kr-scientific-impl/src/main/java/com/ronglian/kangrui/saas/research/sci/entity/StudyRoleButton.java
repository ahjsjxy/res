package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色按钮表-(角色关联的按钮)
 */
@Table(name = "study_role_button")
public class StudyRoleButton implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * study_role表的主键ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 按钮代码(portal_button表的code)
     */
    @Column(name = "button_code")
    private String buttonCode;

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
     * 获取study_role表的主键ID
     *
     * @return role_id - study_role表的主键ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置study_role表的主键ID
     *
     * @param roleId study_role表的主键ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    public String getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
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
        StudyRoleButton other = (StudyRoleButton) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getButtonCode() == null ? other.getButtonCode() == null : this.getButtonCode().equals(other.getButtonCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getButtonCode() == null) ? 0 : getButtonCode().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", buttonCode=").append(buttonCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}