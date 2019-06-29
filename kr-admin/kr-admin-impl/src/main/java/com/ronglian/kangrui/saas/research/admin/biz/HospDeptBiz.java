package com.ronglian.kangrui.saas.research.admin.biz;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronglian.kangrui.saas.research.admin.constant.AdminCommonConstant;
import com.ronglian.kangrui.saas.research.admin.entity.HospDept;
import com.ronglian.kangrui.saas.research.admin.mapper.HospDeptMapper;
import com.ronglian.kangrui.saas.research.admin.vo.user.HospDeptVo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-24 11:21
 **/
@Service
@Slf4j
public class HospDeptBiz extends BaseBiz<HospDeptMapper,HospDept> {

    @Autowired
    private HospDeptMapper  hospDeptMapper;
    public List<HospDeptVo> selectHospDeptByCenter(Long centerId, String name){
        return hospDeptMapper.selectHospDeptByCenter(centerId,name);
    }
    
    public List<HospDeptVo> listHospDept() {
        HospDept hd = new HospDept();
        hd.setDeleted(AdminCommonConstant.DELETED_NO);
        List<HospDept> depts = this.selectList(hd);
        
        List<HospDeptVo> vos = new ArrayList<>();
        depts.forEach( dept -> {
            HospDeptVo vo = new HospDeptVo();
            BeanUtils.copyProperties(dept, vo);
            vos.add(vo);
        });
        
        return vos;
    }
    
    public HospDeptVo addHospDept(HospDeptVo vo){
        if(StringUtils.isBlank(vo.getName()))
            throw new OperationException("科室名称输入错误");
        
        HospDept hd = new HospDept();
        hd.setName(vo.getName().trim());
        
        HospDept selected = this.selectOne(hd);
        if(selected == null) {
            hd.setCreateTime(new Date());
            hd.setCreateUser(ShiroService.getCurrentUser().getId());
            hd.setCreateUserName(ShiroService.getCurrentUser().getUsername());
            
            hd.setDeleted(AdminCommonConstant.DELETED_NO);
            
            hd = this.insertSelective(hd);
        }
        else if(selected != null && selected.getDeleted() == AdminCommonConstant.DELETED_YES ) 
        {
            selected.setDeleted(AdminCommonConstant.DELETED_NO);
            selected.setUpdateTime(new Date());
            selected.setUpdateUser(ShiroService.getCurrentUser().getId());
            selected.setUpdateUserName(ShiroService.getCurrentUser().getUsername());
            
            this.updateSelectiveById(selected);
            hd = selected;
        }
        else {
            throw new OperationException("科室已存在");
        }
        log.info("HospDept.id-----------" + hd.getId());
        BeanUtils.copyProperties(hd, vo);
        
        return vo;
    }
    
    public void deleteHospDept(Long id) {
        HospDept hd = this.selectById(id);
        if(hd == null)
            throw new OperationException("科室ID不存在");
        
        hd.setDeleted(AdminCommonConstant.DELETED_YES);
        hd.setUpdateTime(new Date());
        hd.setUpdateUser(ShiroService.getCurrentUser().getId());
        hd.setUpdateUserName(ShiroService.getCurrentUser().getUsername());
        
        this.updateSelectiveById(hd);
    }
    
    public HospDeptVo updateHospDept(HospDeptVo vo) {
        if(vo.getId() == null || StringUtils.isBlank(vo.getName()))
            throw new OperationException("科室名称或ID输入错误");
        
        HospDept hd = this.selectById(vo.getId());
        if(hd == null)
            throw new OperationException("科室不存在");

        hd.setName(vo.getName());
        hd.setUpdateTime(new Date());
        hd.setUpdateUser(ShiroService.getCurrentUser().getId());
        hd.setUpdateUserName(ShiroService.getCurrentUser().getUsername());
        
        this.updateSelectiveById(hd);
        
        BeanUtils.copyProperties(hd, vo);
        return vo;
    }

}
