package com.ronglian.kangrui.saas.research.rbac.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;
import com.ronglian.kangrui.saas.research.commonrbac.entity.Role;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.data.dao.RoleRepository;
import com.ronglian.kangrui.saas.research.rbac.data.dao.UserRepository;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RoleBiz {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    
    private Role filter(Role role) {

        role.setCreateTime(null);
        role.setCreateUser(null);
        role.setCreateUserName(null);
        role.setUpdateTime(null);
        role.setUpdateUser(null);
        role.setUpdateUserName(null);
        role.setParams(null);
        
        role.setPermission(null);
        role.setUser(null);
        
        return role;
    }
    
    public List<Role> listRole() {
        List<Role> roles = roleRepo.findAll();
        roles.forEach(r -> { filter(r); } );
        return roles;
    }
    
    public Role addRole(Role role) {
        if(roleRepo.findByName(role.getName()) != null)
            throw new OperationException("角色名已存在!");

        ShiroService.setRecordCreationInfo(role);

        Role saved = roleRepo.save(role);
        return filter(saved);
        //TODO: add users
    }
    
    private Role getById(Long id) {
        Role role = roleRepo.findOne(id);
        if(role == null)
            throw new OperationException("角色不存在！");
        
        return role;
    }
    
    public Role updateRoleInfo(Role role) {
        Role old = getById(role.getId());
        
        ShiroService.setRecordUpdateInfo(role);
        old.clone(role);
        
        Role updated = roleRepo.save(old);
        return filter(updated);
    }
    
    public void deleteRoleById(Long id) {
        Role role = getById(id);

        role.setDeleted(1);
        ShiroService.setRecordUpdateInfo(role);
        roleRepo.save(role);
    }
    
    public Role getRoleById(Long id, boolean needFilter) {
        Role role = getById(id);
        
        if(needFilter)
            return filter(role);
        else
            return role;
    }
    
    
    public List<Role> listRoleByUser(Long userId) {
        User user = userRepo.findOne(userId);
        if(user == null)
            throw new OperationException("用户不存在！");
        
        List<Role> roles = user.getRole();
        log.info(roles.toString());
        roles.forEach( r -> { filter(r); });
        return roles;
    }

    

}
