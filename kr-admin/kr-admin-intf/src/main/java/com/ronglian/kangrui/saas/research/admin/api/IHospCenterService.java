package com.ronglian.kangrui.saas.research.admin.api;

import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 14:44
 **/
@FeignClient(value = "kr-admin")
public interface IHospCenterService {
    @RequestMapping(value="/api/hospCenter/list",method = RequestMethod.GET)
    public List<HospCenterVo> getHospCenterList(@RequestParam(value = "name", required = false)String name) ;

    @RequestMapping(value = "/api/hospCenter/defaultHospCenter",method = RequestMethod.GET)
    public HospCenterVo selectDefaultCenter(@RequestParam(value = "userId", required = false)Long userId);


}
