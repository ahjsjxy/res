package com.ronglian.kangrui.saas.research.commonrbac.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

// TODO： move out to admin

@Data
@Entity
@Table(name = "base_dept")
public class Department extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;

//    @ManyToMany(fetch= FetchType.EAGER,
//                cascade = {CascadeType.ALL})
//    @Fetch(FetchMode.SUBSELECT)
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
