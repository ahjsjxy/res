package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.FieldOption;
import com.ronglian.kangrui.saas.research.sci.mapper.FieldOptionMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.vo.FieldOptionVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class FieldOptionBiz extends BaseBiz<FieldOptionMapper,FieldOption> {

    @Autowired
    private FieldOptionMapper fieldOptionMapper ;


    /**
     * 新增列 下拉选项
     * @param fieldVo
     */
    public void addFieldOption(FieldVo fieldVo) {
        // insert field option
        List<FieldOptionVo> fieldOptionVoList = fieldVo.getFieldOptionVoList() ;
        if (fieldOptionVoList!=null && fieldOptionVoList.size()>0) {
            int num = 1 ;
            for (FieldOptionVo fieldOptionVo : fieldOptionVoList) {
                FieldOption fieldOption = new FieldOption() ;
                fieldOptionVo.setId(null);
                BeanUtils.copyProperties(fieldOptionVo, fieldOption);

                fieldOption.setDisplayOrder(new BigDecimal(num));
                fieldOption.setFieldId(fieldVo.getId());
                fieldOption.setStatus(ResearchConsts.DELETED_NO);
                fieldOption.setDeleted(ResearchConsts.DELETED_NO);
                fieldOption.setUpdateTime(new Date()) ;
                fieldOptionMapper.insertSelective(fieldOption) ;
                num++ ;
            }
        }

    }


    /**
     * 编辑列  下拉选项
     */
    public void updateFieldOption(FieldVo fieldVo) {
        // 根据field id 删除 field_option的相关值
        fieldOptionMapper.deleteFieldOptionByFieldId(fieldVo.getId()) ;

        // 新增列 下拉选项
        this.addFieldOption(fieldVo);
    }

}
