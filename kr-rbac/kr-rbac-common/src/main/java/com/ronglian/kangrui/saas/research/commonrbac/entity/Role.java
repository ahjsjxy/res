package com.ronglian.kangrui.saas.research.commonrbac.entity;


import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.List;

@Data
@Entity
@Table(name = "base_role")
public class Role extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;                    //角色名 唯一
    @Column(name = "description")
    private String description;             //描述信息
    
    public Role clone(Role current) {
        super.clone(current);
        
        if(current.name != null)
            this.name = current.name;
        if(current.description != null)
            this.description = current.description;
        
        return this;
    }
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @Fetch(FetchMode.SUBSELECT)
    private List<User> user;
    
    @ManyToMany(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "base_role_permission",
               joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permission;   //一个用户角色对应多个权限
    
    @Override
    protected StringBuilder appendClazString( StringBuilder sb ) {
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        
        sb.append(", user count=").append(user == null? null:user.size());
        sb.append(", permission count=").append(permission == null? null:permission.size());
        
        return sb;
    }
    
    public String toString() {
        return super.toString();
    }
}
