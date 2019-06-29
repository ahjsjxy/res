package com.ronglian.kangrui.saas.research.shirobase.service;

import java.util.List;

import com.ronglian.kangrui.saas.research.commonrbac.entity.Permission;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import org.springframework.stereotype.Component;



@Component
public interface RbacServiceBase {

    User getUserByName(String name);

    List<Permission> getAllPermissions();
    

}
