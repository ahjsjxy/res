package com.ronglian.kangrui.saas.research.rbac.biz;

import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;
import com.ronglian.kangrui.saas.research.commonrbac.entity.Permission;
import com.ronglian.kangrui.saas.research.commonrbac.entity.Role;
import com.ronglian.kangrui.saas.research.rbac.data.dao.PermissionRepository;
import com.ronglian.kangrui.saas.research.rbac.data.dao.RoleRepository;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionBiz {
    private static final Integer DELETED = 1;
    private RoleRepository roleRepo;
    private PermissionRepository permRepo;

    @Autowired
    public PermissionBiz(RoleRepository roleRepo,
                         PermissionRepository permRepo) {
        this.roleRepo = roleRepo;
        this.permRepo = permRepo;
    }
    
    private Permission filter(Permission perm) {

        perm.setCreateTime(null);
        perm.setCreateUser(null);
        perm.setCreateUserName(null);
        perm.setUpdateTime(null);
        perm.setUpdateUser(null);
        perm.setUpdateUserName(null);
        perm.setParams(null);
        
        return perm;
    }

    public List<Permission> listAllPermissions() {
        return permRepo.findAll();
    }

    public List<Permission> listPermissionByRole(Long roleId, String roleName) {
        Role role = roleRepo.findByIdOrName(roleId, roleName);
        return role.getPermission();
    }
    
    public Permission addPermission(Permission perm) {
        Permission found = permRepo.findByName(perm.getName());
        if(found != null)
            throw new OperationException("该权限已存在");
        
        ShiroService.setRecordCreationInfo(perm);
        Permission saved = permRepo.save(perm);
        return filter(saved);
    }

    public void updatePermission(List<Permission> permission) {
        permission.removeIf(each -> each.getId() == null);
        permission.forEach( p -> ShiroService.setRecordUpdateInfo(p));
        permRepo.save(permission);
    }

    public void deletePermissionById(Long permissionId) {
        Permission permission = permRepo.findOne(permissionId);
        if (permission == null) {
            return;
        }
        permission.setDeleted(DELETED);
        ShiroService.setRecordUpdateInfo(permission);
        permRepo.save(permission);
    }

    public void savePermissionWithRole(Long roleId, List<Permission> permission) {
        Role role = roleRepo.findByIdOrName(roleId, null);
        if (role == null) {
            return;
        }
        List<Permission> mergePermission = role.getPermission();
        Set<Permission> existPermission = new HashSet<>(permission);
        mergePermission.removeIf(existPermission::contains);
        mergePermission.addAll(permission);
        roleRepo.save(role);
    }
}
