package com.ronglian.kangrui.saas.research.rbac.rpc;

import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.rbac.biz.UserBiz;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-06-26 11:25
 **/
@RestController
@RequestMapping("api")
@Slf4j
public class UserService {

    @Autowired
    private  UserBiz userBiz;

    @ApiOperation("用户列表")
    @RequestMapping(value="/user/list",method = RequestMethod.GET)
    public List<User> listUserInfo(){
        List<User> users = userBiz.listUserInfo();
        return users ;
    }
}
