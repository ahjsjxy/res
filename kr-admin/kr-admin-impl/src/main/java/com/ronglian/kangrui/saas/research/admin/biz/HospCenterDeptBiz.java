package com.ronglian.kangrui.saas.research.admin.biz;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronglian.kangrui.saas.research.admin.entity.HospCenterDept;
import com.ronglian.kangrui.saas.research.admin.mapper.HospCenterDeptMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;

import lombok.extern.slf4j.Slf4j;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 13:54
 **/
@Service
@Slf4j
public class HospCenterDeptBiz extends BaseBiz<HospCenterDeptMapper, HospCenterDept> {
    @Autowired
    private HospCenterDeptMapper  centerDeptMapper;
    
    // public void addDeptToCenter(HospCenterDept centerDept){
    // if(centerDept.getCenterId() == null || centerDept.getDeptId() == null)
    // throw new OperationException("输入错误");
    //
    // if(this.selectCount(centerDept) >0)
    // throw new OperationException("医疗机构科室已存在");
    //
    // this.insertSelective(centerDept);
    // }
    
    private void deleteExistDeptFromCenters(List<HospCenterDept> centerDepts) {
        Set<Long> centerIds = new HashSet<>();
        centerDepts.forEach(cd -> {
            centerIds.add(cd.getCenterId());
        });
        if(centerIds == null || centerIds.size() ==0)
            throw new OperationException("机构ID输入错误");
        
        log.info(centerIds.toString());
        centerDeptMapper.deleteAllDeptFromCenters(centerIds);        
    }

    @Transactional
    public void addDeptListToCenter(List<HospCenterDept> centerDepts) {
        centerDepts.forEach(cd -> {
            if (cd.getCenterId() == null || cd.getDeptId() == null)
                throw new OperationException("机构或科室ID输入错误");
            
            if(this.selectCount(cd) > 0)
                log.info("机构科室已存在, 机构ID:{}, 科室ID:{} ",
                         cd.getCenterId(),cd.getDeptId());
            else 
                this.insertSelective(cd);
        });
    }

    public List<HospCenterDept> selectHospCenterDept(Long centerId, Long deptId) {
        HospCenterDept cd = new HospCenterDept();
        cd.setCenterId(centerId);
        cd.setDeptId(deptId);
        return this.selectList(cd);
    }
}
