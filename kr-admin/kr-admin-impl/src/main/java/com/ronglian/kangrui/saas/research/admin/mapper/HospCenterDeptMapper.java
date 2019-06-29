package com.ronglian.kangrui.saas.research.admin.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.ronglian.kangrui.saas.research.admin.entity.HospCenterDept;
import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;

public interface HospCenterDeptMapper extends MyBaseMapper<HospCenterDept> {
    
    public void deleteAllDeptFromCenters(@Param("centerIds") Set<Long> centerIds);
    
}