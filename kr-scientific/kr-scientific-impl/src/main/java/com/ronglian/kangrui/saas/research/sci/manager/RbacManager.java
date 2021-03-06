package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.commonrbac.entity.Permission;
import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.shirobase.service.RbacServiceBase;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(value = "kr-rbac")
@Service
public interface RbacManager extends RbacServiceBase{

    @Override
    @RequestMapping(value = "/inner/user/{name}", method = RequestMethod.GET)
    User getUserByName(@PathVariable("name") String name);
    

    @Override
    @RequestMapping(value = "/inner/permissions", method = RequestMethod.GET)
    List<Permission> getAllPermissions();
    

}
