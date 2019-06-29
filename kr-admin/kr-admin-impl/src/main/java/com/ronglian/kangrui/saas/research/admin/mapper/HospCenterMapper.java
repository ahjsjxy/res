package com.ronglian.kangrui.saas.research.admin.mapper;


import com.ronglian.kangrui.saas.research.admin.entity.HospCenter;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HospCenterMapper extends MyBaseMapper<HospCenter> {


    public List<HospCenterVo> selectHospCenter(@Param("name") String name);


    public HospCenterVo selectDefaultCenter(Long userId);

}