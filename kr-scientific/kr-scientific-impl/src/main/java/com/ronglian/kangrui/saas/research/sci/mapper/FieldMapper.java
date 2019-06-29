package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.Field;
import com.ronglian.kangrui.saas.research.sci.vo.FieldOptionVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FieldMapper extends Mapper<Field> {

    /**
     * 根据二级字典ID查询字段列表
     * @param fieldVo
     * @return
     */
    public List<FieldVo> getFieldListByDictId(FieldVo fieldVo) ;


    /**
     * 编辑的时候判断name是否重复
     * @param fieldVo
     * @return
     */
    public List<FieldVo> checkFieldNameExists(FieldVo fieldVo) ;


    /**
     * 编辑的时候判断label是否重复
     * @param fieldVo
     * @return
     */
    public List<FieldVo> checkFieldLabelExists(FieldVo fieldVo) ;


    /**
     * 根据字段ID 查出所有的父级ID
     * @return
     */
    public List<FieldVo>  getParentListById(@Param(value = "fieldId") Long fieldId) ;


    /**
     *  根据字段ID 查出所有的子级ID
     * @param fieldId
     * @return
     */
    public List<FieldVo> getChildListById(@Param(value = "fieldId") Long fieldId) ;



    /**
     *  根据字段ID 查询该字段下拉列表选项
     * @param fieldId
     * @return
     */
    public List<FieldOptionVo> getOptionListByFieldId(@Param(value = "fieldId") Long fieldId) ;

}