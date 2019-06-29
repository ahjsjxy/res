package com.ronglian.kangrui.saas.research.rbac.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ronglian.kangrui.saas.research.commonrbac.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    Role findByIdOrName(Long id, String name);
}
