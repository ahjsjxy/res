package com.ronglian.kangrui.saas.research.rbac.api;


import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "kr-rbac")
public interface IUserService {

    @RequestMapping(value="/api/user/list",method = RequestMethod.GET)
    public List<User> listUserInfo() ;

}