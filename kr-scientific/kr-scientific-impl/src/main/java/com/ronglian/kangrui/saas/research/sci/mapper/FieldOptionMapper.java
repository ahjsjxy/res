package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.FieldOption;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface FieldOptionMapper extends Mapper<FieldOption> {

    /**
     * 把选项置为无效
     * @param fieldId
     */
    public void deleteFieldOptionByFieldId(@Param(value = "fieldId") Long fieldId) ;
}