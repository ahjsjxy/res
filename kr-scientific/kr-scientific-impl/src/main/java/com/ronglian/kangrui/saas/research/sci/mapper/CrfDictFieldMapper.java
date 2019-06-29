package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.CrfDictField;
import com.ronglian.kangrui.saas.research.sci.vo.CrfDictFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.CrfFieldVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CrfDictFieldMapper extends Mapper<CrfDictField> {

    /**
     * 先把 crf_dict_field 更新deleted=1，根据crf id 和dictId 和fieldId 集合
     * @param crfDictFieldVo
     */
    public void updateByCrfId(CrfDictFieldVo crfDictFieldVo) ;


    /**
     *  查询crf 题组 关联的字段列表
     * @param crfDictField
     * @return
     */
    public List<FieldVo> getSelectFieldByCrfId(CrfDictField crfDictField) ;




    /**
     * 把 crf 表单或者题组下的所有的有效字段改为已删除
     * @param crfDictField
     */
    public void updateCrfDictFieldByCrf(CrfDictField crfDictField) ;


    /**
     *  项目-校验  1：存在未删除的CRF且还没创建表 2：CRF存在字段未创建到数据库列的
     * @param crfDictFieldVo
     * @return
     */
    public List<CrfFieldVo> getCrfAndFieldGenerateToDbNoList(CrfDictFieldVo crfDictFieldVo) ;



    /**
     * Crf表单-校验存在未删除的字段  1: CRF 已经创建表到DB  2：CRF存在字段未创建到数据库列的
     * @param crfDictFieldVo
     * @return
     */
    public List<CrfFieldVo> getFieldsGenerateToDbByCrfFormId(CrfDictFieldVo crfDictFieldVo) ;


    /**
     * 查询CRF以及所有字段都已经创建列
     * @param crfDictFieldVo
     * @return
     */
    public List<CrfFieldVo> getCrfAndFieldGenerateToDbList(CrfDictFieldVo crfDictFieldVo) ;


    /**
     * 查询字段ID是否已经被CRF表单引用(CRF表单下选择的有效字段)
     * @param fieldIdList
     * @return
     */
    public List<CrfDictField> getCrfDictFieldListByFieldIds(@Param("fieldIdList") List<Long> fieldIdList) ;

}