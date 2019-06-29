package com.ronglian.kangrui.saas.research.admin.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ronglian.kangrui.saas.research.admin.entity.HospCenterDeptUser;
import com.ronglian.kangrui.saas.research.admin.mapper.HospCenterDeptUserMapper;
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
public class HospCenterDeptUserBiz extends BaseBiz<HospCenterDeptUserMapper, HospCenterDeptUser> {

    public void addOrUpdate(Long centerDeptId, Long userId) {
        if (centerDeptId == null || userId == null)
            throw new OperationException("科室用户ID输入错误");

        HospCenterDeptUser deptUser = new HospCenterDeptUser();
        deptUser.setUserId(userId);

        HospCenterDeptUser selected = this.selectOne(deptUser);
        if (selected != null) {
            log.info("医疗机构科室中用户已存在");
            this.delete(selected);
        }
        deptUser.setCenterDeptId(centerDeptId);
        this.insertSelective(deptUser);
    }

    public void deleteDeptUser(HospCenterDeptUser deptUser) {
        if (deptUser.getCenterDeptId() == null || deptUser.getUserId() == null)
            throw new OperationException("输入错误");

        HospCenterDeptUser du = this.selectOne(deptUser);

        this.delete(deptUser);
    }

    public List<Long> listUserId(Long centerDeptId) {
        HospCenterDeptUser deptUser = new HospCenterDeptUser();
        deptUser.setCenterDeptId(centerDeptId);
        List<HospCenterDeptUser> deptUsers = this.selectList(deptUser);

        List<Long> ids = new ArrayList<>();
        deptUsers.forEach(du -> {
            ids.add(du.getUserId());
        });

        return ids;
    }
}
