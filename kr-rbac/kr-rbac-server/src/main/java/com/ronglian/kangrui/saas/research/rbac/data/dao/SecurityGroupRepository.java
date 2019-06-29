package com.ronglian.kangrui.saas.research.rbac.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ronglian.kangrui.saas.research.commonrbac.entity.SecurityGroup;

public interface SecurityGroupRepository extends JpaRepository<SecurityGroup, Long> {
    SecurityGroup findByName(String name);
}
