package com.ronglian.kangrui.saas.research.commonrbac.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    @Column(name ="update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//更新时间

    @Column(name = "deleted",columnDefinition="int(11) not null default '0'")
    private Integer deleted = 0;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "create_user_name")
    private String createUserName;

    @Column(name = "update_user")
    private Long updateUser;

    @Column(name = "update_user_name")
    private String updateUserName;
    
    protected BaseEntity clone(BaseEntity current) {
        if(current.updateTime != null)
            this.updateTime = current.updateTime;
        if(current.deleted != null)
            this.deleted = current.deleted;
        if(current.updateUser != null)
            this.updateUser = current.updateUser;
        if(current.updateUserName != null)
            this.updateUserName = current.updateUserName;
        
        return this;
    }
    
    
    @Transient
    private  Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }
    
    protected StringBuilder appendClazString( StringBuilder sb ) {
        //Do Nothing Here
        return sb;
    }
    
    private StringBuilder clazStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(" id=").append(id);
        sb.append(", deleted=").append(deleted);
        
        appendClazString(sb);
        
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", createUserName=").append(createUserName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateUserName=").append(updateUserName);
        sb.append("]");
        
        return sb;
    }
    
    @Override
    public String toString() {
        return clazStringBuilder().toString();
    }
}
