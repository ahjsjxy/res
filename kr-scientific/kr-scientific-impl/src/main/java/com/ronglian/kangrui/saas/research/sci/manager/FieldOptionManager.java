package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.FieldOptionBiz;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 下拉选项服务
 *
 * @author lanyan
 * @create 2019-03-18 14:34
 **/
@Service
public class FieldOptionManager {

    @Autowired
    private FieldOptionBiz fieldOptionBiz ;


    /**
     * 新增列 下拉选项
     * @param fieldVo
     */
    public void addFieldOption(FieldVo fieldVo) {
        fieldOptionBiz.addFieldOption(fieldVo);

    }


    /**
     * 编辑列  下拉选项
     */
    public void updateFieldOption(FieldVo fieldVo) {
        fieldOptionBiz.updateFieldOption(fieldVo);
    }

}
