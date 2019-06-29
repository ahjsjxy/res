package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 字段选项
 */
@Table(name = "field_option")
public class FieldOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * field的主键id
     */
    @Column(name = "field_id")
    private Long fieldId;

    /**
     * 当字段类型是(1：单选题; 2：多选题)时的下拉选项值
     */
    @Column(name = "field_option")
    private String fieldOption;

    /**
     * 下拉选项值的显示顺序
     */
    @Column(name = "display_order")
    private BigDecimal displayOrder;

    /**
     * 是否启用(0：启用  1：未启用)
     */
    private Integer status;

    /**
     * 是否删除(0：未删除  1：删除)
     */
    private Integer deleted;

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
     * 获取field的主键id
     *
     * @return field_id - field的主键id
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * 设置field的主键id
     *
     * @param fieldId field的主键id
     */
    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 获取当字段类型是(1：单选题; 2：多选题)时的下拉选项值
     *
     * @return field_option - 当字段类型是(1：单选题; 2：多选题)时的下拉选项值
     */
    public String getFieldOption() {
        return fieldOption;
    }

    /**
     * 设置当字段类型是(1：单选题; 2：多选题)时的下拉选项值
     *
     * @param fieldOption 当字段类型是(1：单选题; 2：多选题)时的下拉选项值
     */
    public void setFieldOption(String fieldOption) {
        this.fieldOption = fieldOption == null ? null : fieldOption.trim();
    }

    /**
     * 获取下拉选项值的显示顺序
     *
     * @return display_order - 下拉选项值的显示顺序
     */
    public BigDecimal getDisplayOrder() {
        return displayOrder;
    }

    /**
     * 设置下拉选项值的显示顺序
     *
     * @param displayOrder 下拉选项值的显示顺序
     */
    public void setDisplayOrder(BigDecimal displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 获取是否启用(0：启用  1：未启用)
     *
     * @return status - 是否启用(0：启用  1：未启用)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否启用(0：启用  1：未启用)
     *
     * @param status 是否启用(0：启用  1：未启用)
     */
    public void setStatus(Integer status) {
        this.status = status;
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
        FieldOption other = (FieldOption) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFieldId() == null ? other.getFieldId() == null : this.getFieldId().equals(other.getFieldId()))
            && (this.getFieldOption() == null ? other.getFieldOption() == null : this.getFieldOption().equals(other.getFieldOption()))
            && (this.getDisplayOrder() == null ? other.getDisplayOrder() == null : this.getDisplayOrder().equals(other.getDisplayOrder()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateUserName() == null ? other.getCreateUserName() == null : this.getCreateUserName().equals(other.getCreateUserName()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateUserName() == null ? other.getUpdateUserName() == null : this.getUpdateUserName().equals(other.getUpdateUserName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFieldId() == null) ? 0 : getFieldId().hashCode());
        result = prime * result + ((getFieldOption() == null) ? 0 : getFieldOption().hashCode());
        result = prime * result + ((getDisplayOrder() == null) ? 0 : getDisplayOrder().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateUserName() == null) ? 0 : getCreateUserName().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateUserName() == null) ? 0 : getUpdateUserName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fieldId=").append(fieldId);
        sb.append(", fieldOption=").append(fieldOption);
        sb.append(", displayOrder=").append(displayOrder);
        sb.append(", status=").append(status);
        sb.append(", deleted=").append(deleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", createUserName=").append(createUserName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateUserName=").append(updateUserName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}