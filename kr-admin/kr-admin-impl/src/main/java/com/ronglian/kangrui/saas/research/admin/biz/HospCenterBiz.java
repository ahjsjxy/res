package com.ronglian.kangrui.saas.research.admin.biz;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronglian.kangrui.saas.research.admin.constant.AdminCommonConstant;
import com.ronglian.kangrui.saas.research.admin.entity.HospCenter;
import com.ronglian.kangrui.saas.research.admin.mapper.HospCenterMapper;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 13:54
 **/
@Service
@Slf4j
public class HospCenterBiz extends BaseBiz<HospCenterMapper, HospCenter> {

    @Autowired
    private HospCenterMapper hospCenterMapper;

    public List<HospCenterVo> selectHospCenter(String name) {
        return hospCenterMapper.selectHospCenter(name);
    }

    public HospCenterVo addHospCenter(HospCenterVo vo){
        if(StringUtils.isBlank(vo.getName()))
            throw new OperationException("医疗机构名输入错误");
        
        HospCenter hc = new HospCenter() ;
        hc.setName(vo.getName());

        HospCenter selected = this.selectOne(hc);
        if(selected == null) {
            hc.setRemark(vo.getRemark());
            hc.setDeleted(AdminCommonConstant.DELETED_NO);
            hc.setCreateTime(new Date());
            hc.setCreateUser(ShiroService.getCurrentUser().getId());
            hc.setCreateUserName(ShiroService.getCurrentUser().getUsername());
            
            hc = this.insertSelective(hc);
        }
        else if(selected != null && selected.getDeleted() == AdminCommonConstant.DELETED_YES ) 
        {
            selected.setRemark(vo.getRemark());
            selected.setDeleted(AdminCommonConstant.DELETED_NO);
            selected.setUpdateTime(new Date());
            selected.setUpdateUser(ShiroService.getCurrentUser().getId());
            selected.setUpdateUserName(ShiroService.getCurrentUser().getUsername());
            
            this.updateSelectiveById(selected);
            hc = selected;
        }
        else {
            throw new OperationException("医疗机构已存在");
        }

        log.info("HospCenter.id-----------" + hc.getId());
        BeanUtils.copyProperties(hc, vo);
        
        return vo;
    }
    
    public void deleteHospCenter(Long id) {
        HospCenter hc = this.selectById(id);
        if(hc == null)
            throw new OperationException("医疗机构ID不存在");
        
        hc.setDeleted(AdminCommonConstant.DELETED_YES);
        hc.setUpdateTime(new Date());
        hc.setUpdateUser(ShiroService.getCurrentUser().getId());
        hc.setUpdateUserName(ShiroService.getCurrentUser().getUsername());
        
        this.updateSelectiveById(hc);
    }
    
    public HospCenterVo updateHospCenter(HospCenterVo vo) {
        if(vo.getId() == null || StringUtils.isBlank(vo.getName()))
            throw new OperationException("医疗机构信息输入错误");
        
        HospCenter hc = this.selectById(vo.getId());
        if(hc == null)
            throw new OperationException("医疗机构不存在");
        

        hc.setName(vo.getName());
        hc.setRemark(vo.getRemark());
        hc.setUpdateTime(new Date());
        hc.setUpdateUser(ShiroService.getCurrentUser().getId());
        hc.setUpdateUserName(ShiroService.getCurrentUser().getUsername());
        
        this.updateSelectiveById(hc);
        
        BeanUtils.copyProperties(hc, vo);
        return vo;
        
    }



    public HospCenterVo selectDefaultCenter(Long userId){
        return hospCenterMapper.selectDefaultCenter(userId) ;
    }

}
