package com.ronglian.kangrui.saas.research.sci.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 字段
 */
@Table(name = "field")
public class Field implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 二级字典code
     */
    @Column(name = "dictionary_code")
    private String dictionaryCode;


    /**
     * code
     */
    @Column(name = "code")
    private String code ;


    /**
     * 中文名称
     */
    private String label;

    /**
     * 英文名称
     */
    private String name;

    /**
     * （1：单选题  2：多选题  3：填空  4：日期）
     */
    @Column(name = "field_type")
    private Integer fieldType;

    /**
     * 数据类型(1:单选; 2:下拉; 4:文本; 5:整数; 6:小数; 7:日期_年月日; 8:日期_年月; 9:时间_时分秒; 10:日期时间_年月日时分秒)
     */
    @Column(name = "data_type")
    private Integer dataType;

    /**
     * 是否必填（0：是；1：否）
     */
    private String required;


    /**
     * 单位
     */
    private String unit;

    /**
     * 最小值
     */
    @Column(name = "min_value")
    private Long minValue;

    /**
     * 最大值
     */
    @Column(name = "max_value")
    private Long maxValue;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;



    /**
     * 默认值
     */
    @Column(name = "default_value")
    private String defaultValue;



    /**
     * 依赖字段id
     */
    @Column(name = "parent_id")
    private Long parentId ;


    /**
     * 是否开启(0：开启  1：关闭)
     */
    private Integer status ;


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
     * @return dictionary_code
     */
    public String getDictionaryCode() {
        return dictionaryCode;
    }

    /**
     * @param dictionaryCode
     */
    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode == null ? null : dictionaryCode.trim();
    }

    /**
     * 获取中文名称
     *
     * @return label - 中文名称
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置中文名称
     *
     * @param label 中文名称
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * 获取英文名称
     *
     * @return name - 英文名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置英文名称
     *
     * @param name 英文名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取（1：单选题  2：多选题  3：填空  4：日期）
     *
     * @return field_type - （1：单选题  2：多选题  3：填空  4：日期）
     */
    public Integer getFieldType() {
        return fieldType;
    }

    /**
     * 设置（1：单选题  2：多选题  3：填空  4：日期）
     *
     * @param fieldType （1：单选题  2：多选题  3：填空  4：日期）
     */
    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * 获取数据类型(1:单选; 2:下拉; 4:文本; 5:整数; 6:小数; 7:日期_年月日; 8:日期_年月; 9:时间_时分秒; 10:日期时间_年月日时分秒)
     *
     * @return data_type - 数据类型(1:单选; 2:下拉; 4:文本; 5:整数; 6:小数; 7:日期_年月日; 8:日期_年月; 9:时间_时分秒; 10:日期时间_年月日时分秒)
     */
    public Integer getDataType() {
        return dataType;
    }

    /**
     * 设置数据类型(1:单选; 2:下拉; 4:文本; 5:整数; 6:小数; 7:日期_年月日; 8:日期_年月; 9:时间_时分秒; 10:日期时间_年月日时分秒)
     *
     * @param dataType 数据类型(1:单选; 2:下拉; 4:文本; 5:整数; 6:小数; 7:日期_年月日; 8:日期_年月; 9:时间_时分秒; 10:日期时间_年月日时分秒)
     */
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取是否必填（0：是；1：否）
     *
     * @return required - 是否必填（0：是；1：否）
     */
    public String getRequired() {
        return required;
    }

    /**
     * 设置是否必填（0：是；1：否）
     *
     * @param required 是否必填（0：是；1：否）
     */
    public void setRequired(String required) {
        this.required = required == null ? null : required.trim();
    }


    /**
     * 获取单位
     *
     * @return unit - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * 获取最小值
     *
     * @return min_value - 最小值
     */
    public Long getMinValue() {
        return minValue;
    }

    /**
     * 设置最小值
     *
     * @param minValue 最小值
     */
    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    /**
     * 获取最大值
     *
     * @return max_value - 最大值
     */
    public Long getMaxValue() {
        return maxValue;
    }

    /**
     * 设置最大值
     *
     * @param maxValue 最大值
     */
    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 获取默认值
     *
     * @return default_value - 默认值
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置默认值
     *
     * @param defaultValue 默认值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
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
        Field other = (Field) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDictionaryCode() == null ? other.getDictionaryCode() == null : this.getDictionaryCode().equals(other.getDictionaryCode()))
            && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFieldType() == null ? other.getFieldType() == null : this.getFieldType().equals(other.getFieldType()))
            && (this.getDataType() == null ? other.getDataType() == null : this.getDataType().equals(other.getDataType()))
            && (this.getRequired() == null ? other.getRequired() == null : this.getRequired().equals(other.getRequired()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getMinValue() == null ? other.getMinValue() == null : this.getMinValue().equals(other.getMinValue()))
            && (this.getMaxValue() == null ? other.getMaxValue() == null : this.getMaxValue().equals(other.getMaxValue()))
            && (this.getDefaultValue() == null ? other.getDefaultValue() == null : this.getDefaultValue().equals(other.getDefaultValue()))
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
        result = prime * result + ((getDictionaryCode() == null) ? 0 : getDictionaryCode().hashCode());
        result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFieldType() == null) ? 0 : getFieldType().hashCode());
        result = prime * result + ((getDataType() == null) ? 0 : getDataType().hashCode());
        result = prime * result + ((getRequired() == null) ? 0 : getRequired().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getMinValue() == null) ? 0 : getMinValue().hashCode());
        result = prime * result + ((getMaxValue() == null) ? 0 : getMaxValue().hashCode());
        result = prime * result + ((getDefaultValue() == null) ? 0 : getDefaultValue().hashCode());
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
        sb.append(", dictionaryCode=").append(dictionaryCode);
        sb.append(", label=").append(label);
        sb.append(", name=").append(name);
        sb.append(", fieldType=").append(fieldType);
        sb.append(", dataType=").append(dataType);
        sb.append(", required=").append(required);
        sb.append(", unit=").append(unit);
        sb.append(", minValue=").append(minValue);
        sb.append(", maxValue=").append(maxValue);
        sb.append(", defaultValue=").append(defaultValue);
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}