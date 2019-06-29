package com.ronglian.kangrui.saas.research.rbac.rpc;

import com.ronglian.kangrui.saas.research.commonrbac.entity.Permission;
import com.ronglian.kangrui.saas.research.rbac.biz.PermissionBiz;
import com.ronglian.kangrui.saas.research.rbac.manager.RbacManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
public class PermissionController {
    private PermissionBiz permissionBiz;

    @Autowired
    public PermissionController(PermissionBiz permissionBiz) {
        this.permissionBiz = permissionBiz;
    }

    @GetMapping("/inner/permissions/role/id/{roleId}")
    public List<Permission> listPermissionByRoleId(@PathVariable("roleId") String roleId) {
        Long id;
        try {
            id = Long.parseLong(roleId);
        } catch (NumberFormatException e) {
            log.warn("param role id ({}) is not number format.", roleId);
            return Collections.emptyList();
        }

        return permissionBiz.listPermissionByRole(id, null);
    }

    @GetMapping("/inner/permissions/role/name/{roleName}")
    public List<Permission> listPermissionByRoleName(@PathVariable("roleName") String roleName) {
        if (StringUtils.isBlank(roleName)) {
            log.warn("param role name ({}) is blank", roleName);
            return Collections.emptyList();
        }

        return permissionBiz.listPermissionByRole(null, roleName);
    }

    @PostMapping("/inner/permissions/update")
    public void updatePermission(@RequestBody List<Permission> permission) {
        if (permission == null || permission.isEmpty()) {
            return;
        }

        permissionBiz.updatePermission(permission);
    }

    @PostMapping("/inner/permissions/delete/{permissionId}")
    public void deletePermissionWithRole(@PathVariable("permissionId") String permissionId) {
        Long longPermissionId;
        try {
            longPermissionId = Long.parseLong(permissionId);
        } catch (NumberFormatException e) {
            log.warn("param permission id ({}) is not number format.", permissionId);
            return;
        }

        permissionBiz.deletePermissionById(longPermissionId);
    }

    @PostMapping("/inner/permissions/save")
    public void savePermissionWithRole(@RequestParam("roleId") String roleId,
                                       @RequestBody List<Permission> permission) {
        Long longRoleId;
        try {
            longRoleId = Long.parseLong(roleId);
        } catch (NumberFormatException e) {
            log.warn("param role id ({}) is not number format.", roleId);
            return;
        }
        if (permission == null || permission.isEmpty()) {
            return;
        }

        permissionBiz.savePermissionWithRole(longRoleId, permission);
    }
}
