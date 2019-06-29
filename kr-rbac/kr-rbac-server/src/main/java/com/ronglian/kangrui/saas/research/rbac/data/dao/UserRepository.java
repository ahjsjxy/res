package com.ronglian.kangrui.saas.research.rbac.data.dao;

import java.math.BigInteger;
import java.util.List;

import com.ronglian.kangrui.saas.research.common.context.BaseContextHandler;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ronglian.kangrui.saas.research.commonrbac.entity.User;

import javax.persistence.Column;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByDeletedNotAndEnableFlagAndAuditStatus(Integer deleted,Integer enableFlag,Integer auditStatus);


    List<User> findByIdIn(List<Long> ids);
    

    @Query(value="select user_id from base_user_role where role_id = ?1", nativeQuery = true)
    List<BigInteger> getUserIdsByRoleId(Long roleId);
    
    @Query(value="select id from base_user where deleted=0 and ( username like ?1 or name like ?1 or mobile_phone like ?1)",
           nativeQuery = true)
    List<BigInteger> findByKeyword(String keyword);




    
    
}
