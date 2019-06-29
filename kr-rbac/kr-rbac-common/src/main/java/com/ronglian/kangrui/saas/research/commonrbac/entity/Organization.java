package com.ronglian.kangrui.saas.research.commonrbac.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

//TODO： move out to admin

@Data
@Entity
@Table(name = "base_org")
public class Organization extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;

//    @ManyToMany(fetch= FetchType.EAGER,
//                cascade = {CascadeType.ALL})
//    @Fetch(FetchMode.SUBSELECT)
//    @JoinTable(name = "base_org_dept",
//               joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "id"),
//               inverseJoinColumns = @JoinColumn(name = "dept_id", referencedColumnName = "id"))
//    private List<Department> dept;
//
//    @ManyToMany(fetch= FetchType.EAGER,
//                cascade = {CascadeType.ALL})
//    @Fetch(FetchMode.SUBSELECT)
//    @JoinTable(name = "base_org_role",
//               joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "id"),
//               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
//    private List<Role> role;
//
//    @OneToMany(fetch = FetchType.EAGER,
//               cascade = {CascadeType.ALL},
//               mappedBy = "org")
//    @Fetch(FetchMode.JOIN)
//    private List<User> user;
    @Override
    protected StringBuilder appendClazString( StringBuilder sb ) {
        sb.append(", name=").append(name);
        return sb;
    }
    
    public String toString() {
        return super.toString();
    }
}
