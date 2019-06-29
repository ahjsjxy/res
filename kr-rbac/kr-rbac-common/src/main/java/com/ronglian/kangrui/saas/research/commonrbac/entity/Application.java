package com.ronglian.kangrui.saas.research.commonrbac.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "base_application")
public class Application extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "application")
    @Fetch(FetchMode.SUBSELECT)
    private List<User> user;

    @OneToMany(fetch = FetchType.LAZY,
               cascade = {CascadeType.ALL},
               mappedBy = "application")
    @Fetch(FetchMode.SUBSELECT)
    private List<Permission> permission;
    
    @Override
    protected StringBuilder appendClazString( StringBuilder sb ) {
        sb.append(", name=").append(name);
        
        sb.append(", user count=").append(user == null? null:user.size());
        sb.append(", permission count=").append(permission == null? null:permission.size());
        return sb;
    }
    
    public String toString() {
        return super.toString();
    }
}
