package com.ronglian.kangrui.saas.research.rbac.biz;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;
import com.ronglian.kangrui.saas.research.commonrbac.entity.LoginLog;
import com.ronglian.kangrui.saas.research.commonrbac.entity.Role;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.commonrbac.utils.Encryptor;
import com.ronglian.kangrui.saas.research.rbac.data.dao.RoleRepository;
import com.ronglian.kangrui.saas.research.rbac.data.dao.UserRepository;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserBiz {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    private User filter(User user) {
        user.setPassword(null);
        user.setPasswordSalt(null);

        user.setCreateTime(null);
        user.setCreateUser(null);
        user.setCreateUserName(null);
        user.setUpdateTime(null);
        user.setUpdateUser(null);
        user.setUpdateUserName(null);
        user.setParams(null);

        user.setRole(null);
        user.setSecurityGroup(null);

        List<LoginLog> logs = user.getLog();
        if (logs != null && !logs.isEmpty()) {
            user.setLoginCount(logs.size());
            user.setLastLoginIp(logs.get(0).getLoginIp());
            user.setLastLoginTime(logs.get(0).getLoginTime());
        }
        user.setLog(null);

        return user;
    }

    public List<User> listUserInfo() {
        List<User> users = userRepo.findByDeletedNotAndEnableFlagAndAuditStatus(1, 1, 1);
        users.forEach(u -> {
            filter(u);
        });

        users.sort((u1, u2) -> {
            if(u1.getLastLoginTime() == null && u2.getLastLoginTime() == null )
                return 0;
            else if(u1.getLastLoginTime() == null)
                return 1;
            else if(u2.getLastLoginTime() == null)
                return -1;
            else
                return u2.getLastLoginTime().compareTo(u1.getLastLoginTime());
        });

        return users;
    }

    public List<User> searchUserInfo(String keyword) {
        List<BigInteger> userIds = userRepo.findByKeyword("%" + keyword.trim() + "%");

        List<Long> ids = new ArrayList<>();
        userIds.forEach(id -> {
            ids.add(id.longValue());
        });

        List<User> users = userRepo.findByIdIn(ids);
        users.forEach(u -> {
            filter(u);
        });
        return users;
    }

    private boolean userExists(String username, Long id) {
        User user = getUserByUsername(username);
        if (user != null) {
            // 用户名存在，不论有没有虚删除
            if (id != null && !id.equals(user.getId())) {
                throw new OperationException("用户名ID不匹配!");
            }
            return true;
        } else {
            return false;
        }
    }

    private void computePassword(User user) {
        if (StringUtils.isBlank(user.getPasswordSalt())) {
            user.setPasswordSalt(Encryptor.DEFAULT_SALT);
        }

        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(Encryptor.DEFAULT_PASSWORD);
        }

        user.setPassword(Encryptor.encrypt(user.getUsername(), user.getPassword(), user.getPasswordSalt()).toString());
    }

    public User addUser(User user) {
        log.info(user.toString());
        if (userExists(user.getUsername(), null))
            throw new OperationException("用户名已存在!");

        ShiroService.setRecordCreationInfo(user);
        computePassword(user);
        user.setAuditStatus(1);
        user.setDeleted(0);
        user.setEnableFlag(1);

        // Others
        User saved = userRepo.save(user);
        return filter(saved);
    }

    public User updateUser(User user) {
        if (!userExists(user.getUsername(), user.getId())) {
            log.debug("用户名不存在或用户名ID不匹配：{} , {}", user.getUsername(), user.getId());
            throw new OperationException("用户名不存在或用户名ID不匹配!");
        }

        ShiroService.setRecordUpdateInfo(user);
        if (StringUtils.isNotBlank(user.getPassword())) {
            computePassword(user);
        }
        User old = userRepo.findOne(user.getId());
        old = old.clone(user);
        log.info(old.toString());

        User updated = userRepo.save(old);
        return filter(updated);
    }

    public void deleteUserById(Long id) {
        User user = userRepo.findOne(id);
        if (user == null) {
            log.warn("用户id={}不存在！", id);
            throw new OperationException("用户ID不存在!");
        }
        user.setDeleted(1);
        ShiroService.setRecordUpdateInfo(user);

        userRepo.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User getUserInfoByUsername(String username) {
        User user = userRepo.findByUsername(username);

        return filter(user);
    }

    public List<User> listUserByRole(Long roleId) {
        Role role = roleRepo.findOne(roleId);
        if (role == null)
            throw new OperationException("角色不存在！");

        List<User> users = role.getUser();
        users.forEach(u -> {
            filter(u);
        });
        return users;
    }

    public void clearOneRoleFromUsers(Role role) {
        List<BigInteger> userIds = userRepo.getUserIdsByRoleId(role.getId());
        List<Long> ids = new ArrayList<>();
        userIds.forEach(id -> {
            ids.add(id.longValue());
        });

        List<User> users = userRepo.findByIdIn(ids);
        users.forEach(u -> {
            u.getRole().removeIf(r -> r.getId() == role.getId());
        });
        userRepo.save(users);
    }

    public void addOneRoleToUsers(Role role) {
        List<Long> userIds = new ArrayList<>();
        role.getUser().forEach(u -> {
            userIds.add(u.getId());
        });

        List<User> users = userRepo.findByIdIn(userIds);
        users.forEach(u -> {
            u.getRole().add(role);
        });
        userRepo.save(users);
    }

    public List<Map<String, String>> listUserByIds(List<Long> ids) {
        List<User> users = userRepo.findByIdIn(ids);
        List<Map<String, String>> rets = new ArrayList<>();
        users.forEach(u -> {
            HashMap<String, String> ret = new HashMap<>();
            ret.put("username", u.getUsername());
            ret.put("name", u.getName());
            ret.put("id", String.valueOf(u.getId()));
            ret.put("mobilePhone", u.getMobilePhone());

            rets.add(ret);
        });
        return rets;
    }

}
