package com.ronglian.kangrui.saas.research.rbac.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ronglian.kangrui.saas.research.commonrbac.entity.Permission;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);


}
