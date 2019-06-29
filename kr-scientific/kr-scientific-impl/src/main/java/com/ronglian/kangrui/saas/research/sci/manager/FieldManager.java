package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.biz.CrfBiz;
import com.ronglian.kangrui.saas.research.sci.biz.FieldBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.CrfDictField;
import com.ronglian.kangrui.saas.research.sci.entity.Field;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-13 14:16
 **/
@Service
@Slf4j
public class FieldManager {

    @Autowired
    private FieldBiz fieldBiz ;

    @Autowired
    private CrfBiz crfBiz ;



    /**
     * 根据二级字典ID查询字段列表
     * @param dictionaryId
     * @param name
     * @return
     */
    public List<FieldVo> getFieldListByDictId(Long dictionaryId, String name) {
        return fieldBiz.getFieldListByDictId(dictionaryId, name) ;
    }


    /**
     * 查询当前字段的所有子节点id
     * @param fieldId
     * @return
     */
    public List<FieldVo> getChildListById(Long fieldId) {
        return fieldBiz.getChildListById(fieldId) ;
    }



    /**
     * 根据字段ID 查出所有的父节点id
     * @param fieldId
     * @return
     */
    public List<FieldVo> getParentListById(Long fieldId) {
        return fieldBiz.getParentListById(fieldId) ;
    }



    /**
     * 获取有效的下拉列
     * @param dictionaryId
     * @param fieldId
     * @param name
     * @return
     */
    public List<FieldVo> getValidFieldList(Long dictionaryId, Long fieldId, String name) {
        return fieldBiz.getValidFieldList(dictionaryId, fieldId, name) ;
    }




    /**
     * 新增字段
     * @param dictionaryId
     * @param fieldVo
     * @return
     */
    @Transactional
    public Field saveFieldInfo(Long dictionaryId, FieldVo fieldVo) {
        Msg msg = fieldBiz.saveFieldInfo(dictionaryId, fieldVo) ;

        Field field = null ;
        if (msg.getSucFlag()) {
            field = (Field)msg.getData() ;
        }
        return field ;
    }




    /**
     * 编辑 field
     * @param fieldVo
     * @return
     */
    public Field updateField(FieldVo fieldVo) {
        return fieldBiz.updateField(fieldVo) ;
    }




    /**
     * 删除字段
     * @param objectIdStr
     * @return
     */
    public Msg deleteFieldInfo(String objectIdStr) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            List<Long> objectList = Arrays.asList(objectIdStr.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(toList());
            List<CrfDictField> crfDictFieldList = crfBiz.checkReferenceListByFieldId(objectList) ;
            if (crfDictFieldList!=null && crfDictFieldList.size()>0) {
                //不允许删除
                msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_NOT_ALLOWED);
                msg.setDesc(ResearchConsts.FIELD_NOT_DELETED_MSG);

            } else {
                // 修改 某个字段以及所有的子级字段 为删除
                msg = fieldBiz.updateFieldDeleted(objectList);
            }

        } catch (Exception e) {
            log.error(e.getMessage());

            //删除异常
            msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_EXCEPTION);
        }
        return msg ;
    }





    /**
     * 把某个字段置为 开启/关闭
     * @param fieldId
     * @return
     */
    public Boolean updateFieldStatus(Long fieldId, Integer status) {
        return fieldBiz.updateFieldStatus(fieldId, status) ;
    }



    /**
     * 校验英文名称，在该字典下不重复（未删除）
     * @param dictionaryId
     * @param name
     * @param fieldId
     * @return
     */
    public boolean checkFieldNameExists(Long dictionaryId, String name, Long fieldId) {
        return fieldBiz.checkFieldNameExists(dictionaryId, name, fieldId) ;
    }



    /**
     * 校验中文名称，在该字典下不重复（未删除）
     * @param dictionaryId
     * @param label
     * @param fieldId
     * @return
     */
    public boolean checkFieldLabelExists(Long dictionaryId, String label, Long fieldId) {
        return fieldBiz.checkFieldLabelExists(dictionaryId, label, fieldId);
    }



    /**
     * 根据field id 编辑field
     * @param fieldId
     * @return
     */
    public FieldVo getFieldById(Long fieldId) {
        return fieldBiz.getFieldById(fieldId) ;
    }

}
