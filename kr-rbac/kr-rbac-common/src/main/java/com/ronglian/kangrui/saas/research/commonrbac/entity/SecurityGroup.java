package com.ronglian.kangrui.saas.research.commonrbac.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

@Data
@Entity
@Table(name = "base_security_group")
public class SecurityGroup extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;                //权限名 唯一
    @Column(name = "description")
    private String description;         //描述信息
    
    public SecurityGroup clone(SecurityGroup current) {
        super.clone(current);
        
        if(current.name != null)
            this.name = current.name;
        if(current.description != null)
            this.description = current.description;
        
        return this;
    }
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "securityGroup")
    @Fetch(FetchMode.SUBSELECT)
    private List<User> user;


    @Override
    protected StringBuilder appendClazString( StringBuilder sb ) {
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        
        sb.append(", user count=").append(user == null? null:user.size());
        return sb;
    }
    
    public String toString() {
        return super.toString();
    }
}
