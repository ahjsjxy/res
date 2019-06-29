package com.ronglian.kangrui.saas.research.commonrbac.entity;


import javax.persistence.*;

import lombok.Data;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 公有云：                                                                        私有化：
 *  1) Org对应多中心;                  1) Org对应医院和【医院科室(部门)】
 *  2) Dpt对应【临床学科】;              2) 可能需新增岗位Pos概念
 *  3) Org和Dpt对Role无影响；                             3) Org和岗位对Role及SG均有影响
 *  4) Org在科研端中影响SG
 *
 *                         Application
 *                              |             Position(Title)?
 *                              |               /
 *         Department  1----*  User *----1 Organization
 *                            /    \
 *                           /      \
 *                        Role    SecurityGroup
 *                         |
 *                     Permission
 */


@Data
@Entity
@Table(name = "base_user")
public class User extends BaseEntity {
    @Column(name = "username", unique = true)
    private String username;//用户名 唯一
    @Column(name ="name")
    private String name;
    @Column(name = "password")
    private String password;//用户密码
    @Column(name = "password_salt")
    private String passwordSalt;//用户密码加密盐值
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column(name = "admin_flag",columnDefinition="int(11)  not null default '0'")
    private Integer adminFlag = 0;
    @Column(name = "remark")
    private String remark;
    @Column(name = "enable_flag",columnDefinition="int(11) not null default '1'")
    private Integer enableFlag = 1;
    @Column(name = "audit_status",columnDefinition="int(11)  not null default '1'")
    private Integer auditStatus = 1; //目前开放审核
    
    public User clone(User current) {
        super.clone(current);
        
        if(current.password != null)
            this.password = current.password;
        if(current.passwordSalt != null)
            this.passwordSalt = current.passwordSalt;
        if(current.mobilePhone != null)
            this.mobilePhone = current.mobilePhone;
        if(current.name != null)
            this.name = current.name;
        if(current.adminFlag != null)
            this.adminFlag = current.adminFlag;
        if(current.remark != null)
            this.remark = current.remark;
        if(current.enableFlag != null)
            this.enableFlag = current.enableFlag;
        if(current.auditStatus != null)
            this.auditStatus = current.auditStatus;
        
        if(current.role != null)
            this.role = current.role;
        if(current.securityGroup != null)
            this.securityGroup = current.securityGroup;
 
        return this;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "base_user_role",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> role;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "base_user_security_group",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "security_group_id", referencedColumnName = "id"))
    private List<SecurityGroup> securityGroup;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "base_user_application",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "application_id", referencedColumnName = "id"))
    private List<Application> application;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.JOIN)
//    private Organization org;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.JOIN)
//    private Major major;

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size=5)
    @OrderBy("loginTime DESC")
    private List<LoginLog> log;
    
    @Transient
    private Long centerId;
    @Transient
    private Long deptId;
    @Transient
    private Integer loginCount;
    @Transient
    private String lastLoginIp;
    @Transient
    private Date lastLoginTime;
    
    @Override
    protected StringBuilder appendClazString( StringBuilder sb ) {
        sb.append(", username=").append(username);
        sb.append(", name=").append(name);
        sb.append(", mobilePhone=").append(mobilePhone);
        sb.append(", remark=").append(remark);
        sb.append(", adminFlag=").append(adminFlag);
        sb.append(", enableFlag=").append(enableFlag);
        sb.append(", auditStatus=").append(auditStatus);
        sb.append(", password=").append(password);
        
        sb.append(", role count=").append(role == null? null:role.size());
        sb.append(", securityGroup count=").append(securityGroup == null? null:securityGroup.size());
        sb.append(", application count=").append(application == null? null:application.size());
        
        return sb;
    }
    
    public String toString() {
        return super.toString();
    }
}
