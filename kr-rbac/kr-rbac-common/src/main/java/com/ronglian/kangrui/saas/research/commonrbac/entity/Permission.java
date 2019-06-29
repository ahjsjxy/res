package com.ronglian.kangrui.saas.research.commonrbac.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "base_permission")
public class Permission extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;                //权限名 唯一

    @Column(name = "url")
    private String url; 

    @Column(name = "description")
    private String description;         //描述信息

    @ManyToOne
    private Application application;
    
    @Override
    protected StringBuilder appendClazString( StringBuilder sb ) {
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
        sb.append(", description=").append(description);
        sb.append(", application=").append(application == null ? null:application.getName());
        return sb;
    }
    
    public String toString() {
        return super.toString();
    }
}
