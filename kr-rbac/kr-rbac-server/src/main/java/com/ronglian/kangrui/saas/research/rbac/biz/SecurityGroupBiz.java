package com.ronglian.kangrui.saas.research.rbac.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;
import com.ronglian.kangrui.saas.research.commonrbac.entity.SecurityGroup;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.data.dao.SecurityGroupRepository;
import com.ronglian.kangrui.saas.research.rbac.data.dao.UserRepository;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityGroupBiz {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SecurityGroupRepository sgRepo;
    
    private SecurityGroup filter(SecurityGroup sg) {

        sg.setCreateTime(null);
        sg.setCreateUser(null);
        sg.setCreateUserName(null);
        sg.setUpdateTime(null);
        sg.setUpdateUser(null);
        sg.setUpdateUserName(null);
        sg.setParams(null);
        
        sg.setUser(null);
        
        return sg;
    }
    
    public List<SecurityGroup> listSecurityGroup() {
        List<SecurityGroup> sgs = sgRepo.findAll();
        sgs.forEach(sg -> { filter(sg); } );
        return sgs;
    }
    
    public SecurityGroup addSecurityGroup(SecurityGroup sg) {
        if(sgRepo.findByName(sg.getName()) != null)
            throw new OperationException("安全组已存在!");

        ShiroService.setRecordCreationInfo(sg);

        SecurityGroup saved = sgRepo.save(sg);
        return filter(saved);
    }
    
    private SecurityGroup getById(Long id) {
        SecurityGroup sg = sgRepo.findOne(id);
        if(sg == null)
            throw new OperationException("安全组不存在！");
        
        return sg;
    }
    
    public SecurityGroup updateSecurityGroup(SecurityGroup sg) {
        SecurityGroup old = getById(sg.getId());
        
        ShiroService.setRecordUpdateInfo(sg);
        old.clone(sg);
        
        SecurityGroup updated = sgRepo.save(old);
        return filter(updated);
    }
    
    public void deleteSecurityGroupById(Long id) {
        SecurityGroup sg = getById(id);

        sg.setDeleted(1);
        ShiroService.setRecordUpdateInfo(sg);
        sgRepo.save(sg);
    }
    
    public SecurityGroup getSecurityGroupById(Long id, boolean needFilter) {
        SecurityGroup sg = getById(id);
        
        if(needFilter)
            return filter(sg);
        else
            return sg;
    }
    
    
    public List<SecurityGroup> listSecurityGroupByUser(Long userId) {
        User user = userRepo.findOne(userId);
        if(user == null)
            throw new OperationException("用户不存在！");
        
        List<SecurityGroup> sgs = user.getSecurityGroup();
        log.info(sgs.toString());
        sgs.forEach( r -> { filter(r); });
        return sgs;
    }

    

}
