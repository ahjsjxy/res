package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.CrfDictField;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface FormMapper extends Mapper<FormsVo> {


    /**
     *  根据study id查询 study
     * @param studyId
     * @return
     */
    public StudyTreeVo getStudyInfoById(@Param(value = "studyId") Long studyId) ;


    /**
     * 根据study id查询对应的crf form列表
     * @param studyId
     * @return
     */
    public List<CrfParentVo> getCrfFormListByStudyId(@Param(value = "studyId") Long studyId) ;

    /**
     * 根据crf form id查询对应的题组列表
     * @param crfFormId
     * @return
     */
    public List<CrfVo> getCrfSecondListByFormId(@Param(value = "crfFormId") Long crfFormId) ;

    /**
     * 根据crf题组ID查询对应的字典列表
     * @param crfVo
     * @return
     */
    public List<CrfDictionaryVo> getDictListByCrfId(CrfVo crfVo) ;


    /**
     * 根据 crf id 和 字典id 查询对应的字段列表
     * @param crfDictionaryVo
     * @return
     */
    public List<CrfFieldVo> getFieldListByDictId(CrfDictionaryVo crfDictionaryVo) ;

    /**
     * 更新该研究对象下显示的字段
     * @param crfDictField
     */
    public void updateCrfDictFieldDisplay(CrfDictField crfDictField) ;


    /**
     * 根据study id查询 form 列表
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getFormListByStudyId(@Param(value = "studyId") Long studyId) ;


    /**
     * 根据 crf form id查询display字段列表
     * @param crfFormId
     * @return
     */
    public List<CrfFieldVo> getDisplayFieldsByFormId(@Param(value = "crfFormId") Long crfFormId) ;


    /**
     * 根据 crf form id  查询所有已生成字段到DB的 未删除字段列表
     * @param crfFormId
     * @return
     */
    public List<CrfFieldVo> getNormalFieldsByFormId(@Param(value = "crfFormId") Long crfFormId) ;


    /**
     * 根据 crf form id查询对应的字段列表
     * @param crfFormId
     * @return
     */
    public List<CrfFieldVo> getFieldsByFormId(@Param(value = "crfFormId") Long crfFormId, @Param(value = "crfSecondId") Long crfSecondId) ;


    /**
     * 根据 field id查询对应下拉选项的列表
     * @param fieldId
     * @return
     */
    public List<FieldOptionVo> getFieldOptionListByFieldId(@Param(value = "fieldId") Long fieldId) ;


    /**
     * 根据form id查询form 详情
     * @param crfFormId
     * @return
     */
    public List<CrfParentVo> getCrfFormListById(@Param(value = "crfFormId") Long crfFormId) ;


    /**
     * 根据objectid 查询动态表单是否有数据
     * @param formConfigVo
     * @return
     */
    public List<Map> getFormDataByObjectId(FormConfigVo formConfigVo) ;


    /**
     * 插入一条新的form数据
     * @param formConfigVo
     */
    public void insertOneRowFormData(FormConfigVo formConfigVo) ;

    /**
     * 更新crf的状态
     * @param tableName
     * @param status
     * @param objectId
     */
    public void updateFormState(@Param("tableName")String tableName,
                                @Param("status")Integer status,
                                @Param("objectId") Long objectId);

    /**
     * 获取动态表单中该crf的状态
     * @param tableName
     * @param objectId
     * @return
     */
    public StatusVo selectStatus(@Param("tableName") String tableName, @Param("objectId") Long objectId);

}