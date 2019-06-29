package com.ronglian.kangrui.saas.research.admin.mapper;


import com.ronglian.kangrui.saas.research.admin.entity.HospDept;
import com.ronglian.kangrui.saas.research.admin.vo.user.HospDeptVo;
import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HospDeptMapper extends MyBaseMapper<HospDept> {
    public List<HospDeptVo> selectHospDeptByCenter(@Param("centerId") Long centerId, @Param("name") String name);
}