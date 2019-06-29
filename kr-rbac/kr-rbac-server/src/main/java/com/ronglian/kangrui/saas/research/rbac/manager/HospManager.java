package com.ronglian.kangrui.saas.research.rbac.manager;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "kr-admin")
@Service
public interface HospManager {

    @RequestMapping(value = "/api/addUserToDept", method = RequestMethod.GET)
    public void addUserToDept(@RequestParam("centerId") Long centerId,
                              @RequestParam("deptId") Long deptId,
                              @RequestParam("userId")Long userId);

}
