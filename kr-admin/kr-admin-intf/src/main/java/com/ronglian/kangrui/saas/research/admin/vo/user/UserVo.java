package com.ronglian.kangrui.saas.research.admin.vo.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserVo implements Serializable{
    public Long id;
    public String username;
    public String password;
    public String name;
    private String mobilePhone;
    private String email;
    private String depName ;
    // private String facilityName;
    private Integer disableFlag;
    private Date crtTime;//创建时间
    private String crtUser;//创建人
    private String roleId;
    private Date updTime;//更新时间
    private String updUser;//更新人

    private String roleIdStr ;
    public String oldPwd ;//旧密码

    public Long[] roleIdArray ;

    public Integer checkFlag ;//是否选中标志（1：是，0：否）
    public Long groupId ;//角色组ID


    public boolean haveUserMenu ; //判断当前用户是否有User菜单


    private Integer adminFlag;// 是否平台管理员(1：是，0：否)
    private String remark;//备注
    private Integer enableFlag;// 是否启用(1:启用， 0：未启用)

    private Integer centerId; //医疗机构ID
    private Integer deptId;//科室ID
}
