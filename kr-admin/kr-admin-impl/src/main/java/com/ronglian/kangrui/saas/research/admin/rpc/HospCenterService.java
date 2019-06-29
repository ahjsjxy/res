package com.ronglian.kangrui.saas.research.admin.rpc;

import java.util.List;

import com.ronglian.kangrui.saas.research.commonrbac.entity.User;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ronglian.kangrui.saas.research.admin.biz.HospCenterBiz;
import com.ronglian.kangrui.saas.research.admin.manager.HospCenterManager;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 14:12
 **/
@RestController
@RequestMapping("api")
@Slf4j
public class HospCenterService {
    
    @Autowired
    private HospCenterBiz centerBiz ;

    @Autowired
    private HospCenterManager centerManager ;


    @ApiOperation("医疗机构加入用户")
    @RequestMapping(value = "/addUserToDept",method = RequestMethod.GET)
    public void addUserToDept(Long centerId, Long deptId, Long userId) {
        
        log.info(ShiroService.getCurrentUser().toString());
        centerManager.addUserToDept(centerId, deptId, userId);
    }


    @ApiOperation("医疗机构列表")
    @RequestMapping(value = "/hospCenter/list",method = RequestMethod.GET)
    public List<HospCenterVo> list(String name) {
        return centerBiz.selectHospCenter(name);
    }



    @ApiOperation("用户默认医疗机构")
    @RequestMapping(value = "/hospCenter/defaultHospCenter",method = RequestMethod.GET)
    public HospCenterVo selectDefaultCenter(Long userId) {
        // Long userId = ShiroService.getCurrentUser().getId() ;
        return centerBiz.selectDefaultCenter(userId);
    }


}
