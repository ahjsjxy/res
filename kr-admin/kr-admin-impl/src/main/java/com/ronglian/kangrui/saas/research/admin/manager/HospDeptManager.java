package com.ronglian.kangrui.saas.research.admin.manager;


import com.ronglian.kangrui.saas.research.admin.biz.HospCenterDeptBiz;
import com.ronglian.kangrui.saas.research.admin.biz.HospCenterDeptUserBiz;
import com.ronglian.kangrui.saas.research.admin.biz.HospDeptBiz;
import com.ronglian.kangrui.saas.research.admin.entity.HospCenterDept;
import com.ronglian.kangrui.saas.research.admin.entity.HospCenterDeptUser;
import com.ronglian.kangrui.saas.research.admin.vo.user.HospDeptVo;
import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;
import com.ronglian.kangrui.saas.research.commonrbac.entity.LoginLog;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-24 11:21
 **/
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class HospDeptManager {

    @Autowired
    private HospDeptBiz deptBiz;
    
    @Autowired
    private HospCenterDeptBiz centerDeptBiz;
    
    @Autowired
    private HospCenterDeptUserBiz deptUserBiz;

    @Autowired
    private RbacManager rbacMgr;
    
    public List<HospDeptVo> selectHospDeptByCenter(Long centerId, String name){
        return deptBiz.selectHospDeptByCenter(centerId,name);
    }
    
    public List<HospDeptVo> selectHospDeptNotInCenter(Long centerId) {
        List<HospDeptVo> all = deptBiz.listHospDept();
        List<HospDeptVo> contained = deptBiz.selectHospDeptByCenter(centerId,null);
        
        all.removeIf( contained::contains );
        
        return all;
    }
    
    public void deleteHospDept(Long deptId) {
        HospCenterDept cd = new HospCenterDept();
        cd.setDeptId(deptId);
        if(centerDeptBiz.selectCount(cd) > 0) {
            throw new OperationException("该科室被使用，无法删除");
        }
        deptBiz.deleteHospDept(deptId);
    }

    public List<Map<String, String> > listUser(Long centerId, Long deptId) {
        log.info((new Date()).toString());
        
        List<HospCenterDept>  cds = centerDeptBiz.selectHospCenterDept(centerId, deptId);
        if(cds == null || cds.size() != 1) 
            throw new OperationException("医疗机构科室不存在或多于一个");
        
        log.info((new Date()).toString());
        List<Long> userIds = deptUserBiz.listUserId(cds.get(0).getId());
        
        log.info((new Date()).toString());
        List<Map<String, String> > users = rbacMgr.listUserByIds(userIds);
        log.info((new Date()).toString());
        
        return users;
    }
    
    
    public HospCenterDept getUserCenterDept(Long userId){
        HospCenterDeptUser cdu = new HospCenterDeptUser();
        cdu.setUserId(userId);
        List<HospCenterDeptUser> selected = deptUserBiz.selectList(cdu);
        if(selected == null || selected.size() == 0)
            throw new OperationException("用户不属于任何机构科室");
        
        if(selected.size() > 1)
            throw new OperationException("用户所属机构科室大于一个");
        
        HospCenterDept cd = new HospCenterDept();
        cd.setId(selected.get(0).getCenterDeptId());
        return centerDeptBiz.selectOne(cd);
        
    }
    
    

}
