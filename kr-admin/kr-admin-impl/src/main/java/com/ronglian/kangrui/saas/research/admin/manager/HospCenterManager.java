package com.ronglian.kangrui.saas.research.admin.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronglian.kangrui.saas.research.admin.biz.HospCenterBiz;
import com.ronglian.kangrui.saas.research.admin.biz.HospCenterDeptBiz;
import com.ronglian.kangrui.saas.research.admin.biz.HospCenterDeptUserBiz;
import com.ronglian.kangrui.saas.research.admin.entity.HospCenterDept;
import com.ronglian.kangrui.saas.research.admin.entity.HospCenterDeptUser;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 14:03
 **/
@Service
public class HospCenterManager {
    @Autowired
    private HospCenterBiz centerBiz ;
    
    @Autowired
    private HospCenterDeptBiz centerDeptBiz ;
    
    @Autowired
    private HospCenterDeptUserBiz deptUserBiz ;

    @Transactional
    public void addUserToDept(Long centerId, Long deptId, Long userId) {
        HospCenterDept param = new HospCenterDept();
        param.setCenterId(centerId);
        param.setDeptId(deptId);
        
        HospCenterDept dept = centerDeptBiz.selectOne(param);
        if(dept != null) {
            deptUserBiz.addOrUpdate(dept.getId(), userId);
        }
        else {
            HospCenterDept cd = centerDeptBiz.insertSelective(param);
            deptUserBiz.addOrUpdate(cd.getId(), userId);
        }
    }
    
    public void deleteHospCenter(Long centerId) {
        HospCenterDept cd = new HospCenterDept();
        cd.setCenterId(centerId);;
        if(centerDeptBiz.selectCount(cd) > 0) {
            throw new OperationException("该机构被使用，无法删除");
        }
        centerBiz.deleteHospCenter(centerId);
    }
    
    public void deleteDeptFromCenter(HospCenterDept centerDept) {
        if (centerDept.getCenterId() == null || centerDept.getDeptId() == null)
            throw new OperationException("机构或科室ID输入错误");
        
        HospCenterDept hcd = centerDeptBiz.selectOne(centerDept);
        
        HospCenterDeptUser cdu = new HospCenterDeptUser();
        cdu.setCenterDeptId(hcd.getId());
        if(deptUserBiz.selectCount(cdu) > 0)
            throw new OperationException("该机构科室存在用户，无法删除");
            
        centerDeptBiz.delete(hcd);
    }
}
