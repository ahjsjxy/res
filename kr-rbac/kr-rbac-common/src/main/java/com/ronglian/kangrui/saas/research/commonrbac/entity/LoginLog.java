package com.ronglian.kangrui.saas.research.commonrbac.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "base_login_log")
public class LoginLog extends BaseEntity {
    @Column(name = "login_time")
    private Date loginTime;
    @Column(name = "login_ip")
    private String loginIp;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    
    @Override
    protected StringBuilder appendClazString( StringBuilder sb ) {
        sb.append(", loginTime=").append(loginTime);
        sb.append(", loginIp=").append(loginIp);
        
        sb.append(", user=").append(user == null ? null:user.getUsername());
        return sb;
    }
    
    public String toString() {
        return super.toString();
    }
}
