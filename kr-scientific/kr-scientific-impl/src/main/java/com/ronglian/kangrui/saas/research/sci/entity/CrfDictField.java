package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * crf字典关联的字段
 */
@Table(name = "crf_dict_field")
public class CrfDictField implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * crf题组id(crf的二级ID)
     */
    @Column(name = "crf_id")
    private Long crfId;

    /**
     * crf的id(crf的一级ID)
     */
    @Column(name = "crf_form_id")
    private Long crfFormId;

    /**
     * 字典id
     */
    @Column(name = "dict_id")
    private Long dictId;

    /**
     * 字段id
     */
    @Column(name = "field_id")
    private Long fieldId;

    /**
     * 数据库字段DB列名
     */
    @Column(name = "field_name")
    private String fieldName ;



    /**
     * 动态表单字段是否创建（0：未创建  1：已创建）
     */
    @Column(name = "generate_to_db")
    private Integer generateToDb ;



    /**
     * 是否显示(0：显示；1隐藏)
     */
    @Column(name = "display")
    private Integer display;


    /**
     * 是否删除(0：未删除  1：删除)
     */
    @Column(name = "deleted")
    private Integer deleted;

    /**
     * 创建时间
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
     * 获取crf题组id(crf的二级ID)
     *
     * @return crf_id - crf题组id(crf的二级ID)
     */
    public Long getCrfId() {
        return crfId;
    }

    /**
     * 设置crf题组id(crf的二级ID)
     *
     * @param crfId crf题组id(crf的二级ID)
     */
    public void setCrfId(Long crfId) {
        this.crfId = crfId;
    }

    /**
     * 获取crf的id(crf的一级ID)
     *
     * @return crf_form_id - crf的id(crf的一级ID)
     */
    public Long getCrfFormId() {
        return crfFormId;
    }

    /**
     * 设置crf的id(crf的一级ID)
     *
     * @param crfFormId crf的id(crf的一级ID)
     */
    public void setCrfFormId(Long crfFormId) {
        this.crfFormId = crfFormId;
    }

    /**
     * 获取字典id
     *
     * @return dict_id - 字典id
     */
    public Long getDictId() {
        return dictId;
    }

    /**
     * 设置字典id
     *
     * @param dictId 字典id
     */
    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    /**
     * 获取字段id
     *
     * @return field_id - 字段id
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * 设置字段id
     *
     * @param fieldId 字段id
     */
    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 获取是否显示(0：显示；1隐藏)
     *
     * @return display - 是否显示(0：显示；1隐藏)
     */
    public Integer getDisplay() {
        return display;
    }

    /**
     * 设置是否显示(0：显示；1隐藏)
     *
     * @param display 是否显示(0：显示；1隐藏)
     */
    public void setDisplay(Integer display) {
        this.display = display;
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
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
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


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public Integer getGenerateToDb() {
        return generateToDb;
    }

    public void setGenerateToDb(Integer generateToDb) {
        this.generateToDb = generateToDb;
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
        CrfDictField other = (CrfDictField) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCrfId() == null ? other.getCrfId() == null : this.getCrfId().equals(other.getCrfId()))
            && (this.getCrfFormId() == null ? other.getCrfFormId() == null : this.getCrfFormId().equals(other.getCrfFormId()))
            && (this.getDictId() == null ? other.getDictId() == null : this.getDictId().equals(other.getDictId()))
            && (this.getFieldId() == null ? other.getFieldId() == null : this.getFieldId().equals(other.getFieldId()))
            && (this.getDisplay() == null ? other.getDisplay() == null : this.getDisplay().equals(other.getDisplay()))
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
        result = prime * result + ((getCrfId() == null) ? 0 : getCrfId().hashCode());
        result = prime * result + ((getCrfFormId() == null) ? 0 : getCrfFormId().hashCode());
        result = prime * result + ((getDictId() == null) ? 0 : getDictId().hashCode());
        result = prime * result + ((getFieldId() == null) ? 0 : getFieldId().hashCode());
        result = prime * result + ((getDisplay() == null) ? 0 : getDisplay().hashCode());
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
        sb.append(", crfId=").append(crfId);
        sb.append(", crfFormId=").append(crfFormId);
        sb.append(", dictId=").append(dictId);
        sb.append(", fieldId=").append(fieldId);
        sb.append(", display=").append(display);
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