package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Dictionary;
import com.ronglian.kangrui.saas.research.sci.entity.Field;
import com.ronglian.kangrui.saas.research.sci.mapper.FieldMapper;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.mapper.DictionaryMapper;
import com.ronglian.kangrui.saas.research.sci.vo.FieldOptionVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class FieldBiz extends BaseBiz<FieldMapper,Field> {

    @Autowired
    private FieldMapper fieldMapper ;

    @Autowired
    private DictionaryMapper dictionaryMapper ;

    @Autowired
    private FieldOptionBiz fieldOptionBiz ;



    /**
     * 根据二级字典ID查询字段列表
     * @param dictionaryId
     * @param name
     * @return
     */
    public List<FieldVo> getFieldListByDictId(Long dictionaryId, String name) {
        FieldVo temp = new FieldVo() ;
        temp.setDictionaryId(dictionaryId);
        temp.setName(name);
        return fieldMapper.getFieldListByDictId(temp) ;
    }


    /**
     * 查询当前字段的所有子节点id
     * @param fieldId
     * @return
     */
    public List<FieldVo> getChildListById(Long fieldId) {
        return fieldMapper.getChildListById(fieldId) ;
    }



    /**
     * 根据字段ID 查出所有的父节点id
     * @param fieldId
     * @return
     */
    public List<FieldVo> getParentListById(Long fieldId) {
        return fieldMapper.getParentListById(fieldId) ;
    }



    /**
     * 获取有效的下拉列
     * @param dictionaryId
     * @param fieldId
     * @param name
     * @return
     */
    public List<FieldVo> getValidFieldList(Long dictionaryId, Long fieldId, String name) {
        FieldVo temp = new FieldVo() ;
        temp.setDictionaryId(dictionaryId);
        temp.setName(name);
        temp.setStatus(ResearchConsts.DELETED_NO);
        List<FieldVo> fieldVoList = fieldMapper.getFieldListByDictId(temp) ;

        // 编辑时
        if (fieldId!=null) {
            // 查询当前字段的所有子节点id
            List<FieldVo> childFieldList = this.getChildListById(fieldId) ;
            if (childFieldList!=null && childFieldList.size()>0) {
                // 该字典下的可用字段 减去 子级所有字段 再减去自己
                List<FieldVo> validFieldList = fieldVoList.stream().filter(item -> !childFieldList.contains(item)).collect(toList());
                if (validFieldList!=null && validFieldList.size()>0) {
                    validFieldList = validFieldList.stream().filter(item -> fieldId.longValue()!=item.getId()).collect(toList());
                    fieldVoList = validFieldList ;
                }
            }
        }

        return fieldVoList ;
    }




    /**
     * 新增字段
     *
     * @param dictionaryId
     * @param fieldVo
     * @return
     */
    @Transactional
    public Msg saveFieldInfo(Long dictionaryId, FieldVo fieldVo) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            // 校验英文名称，在该字典下不重复
            boolean repeatNameFlag = this.checkFieldNameExists(dictionaryId, fieldVo.getName().trim(), fieldVo.getId()) ;

            // 校验中文名称，在该字典下不重复
            boolean repeatLabelFlag = checkFieldLabelExists(dictionaryId, fieldVo.getLabel().trim(), fieldVo.getId()) ;
            if (!repeatNameFlag && !repeatLabelFlag) {

                Field field = null;
                if (fieldVo.getId()!=null) {
                    // 编辑
                    field = this.updateField(fieldVo) ;
                } else {
                    // 新增
                    field = this.addField(dictionaryId, fieldVo) ;
                }

                msg.setSucFlag(Boolean.TRUE);
                msg.setData(field);

            } else {
                // 存在重复
                msg.setDesc(RestCodeConstants.STATUS_CODE_SAVE_CHECK_REPEAT);

                log.info("校验英文名称或中文名称 存在重复============");
                log.info("dictionaryId=={}, name=={}, fieldId={}", dictionaryId, fieldVo.getName(), fieldVo.getId());
                log.info("dictionaryId=={}, label=={}, fieldId={}", dictionaryId, fieldVo.getLabel(), fieldVo.getId());

            }

        } catch (Exception e) {
            log.error(e.getMessage());
            //保存异常
            msg.setCode(RestCodeConstants.STATUS_CODE_SAVE_EXCEPTION);
        }


        return msg ;
    }


    /**
     * 新增 field
     * @param dictionaryId
     * @param fieldVo
     * @return
     */
    private Field addField(Long dictionaryId, FieldVo fieldVo) {
        Field field = new Field() ;

        Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(dictionaryId) ;
        fieldVo.setDictionaryCode(dictionary.getCode());

        // insert field
        BeanUtils.copyProperties(fieldVo, field);
        fieldMapper.insertSelective(field) ;
        log.info("id========{}", field.getId());
        field.setCode(field.getId()+"");
        fieldMapper.updateByPrimaryKeySelective(field) ;

        // insert field option
        fieldVo.setId(field.getId());
        fieldOptionBiz.addFieldOption(fieldVo);

        return field ;
    }




    /**
     * 编辑 field
     * @param fieldVo
     * @return
     */
    public Field updateField(FieldVo fieldVo) {
        Field field = new Field() ;
        BeanUtils.copyProperties(fieldVo, field);

        // 编辑字段
        field.setUpdateTime(new Date());
        fieldMapper.updateByPrimaryKeySelective(field) ;

        // 编辑field option
        fieldOptionBiz.updateFieldOption(fieldVo);

        return field ;
    }




    /**
     * 置field表  单个字段的deleted状态为 已删除
     * @param fieldId
     */
    public void updateSingleFieldDeleted(Long fieldId) {
        Field temp = fieldMapper.selectByPrimaryKey(fieldId) ;
        temp.setUpdateTime(new Date());
        temp.setDeleted(ResearchConsts.DELETED_YES);
        fieldMapper.updateByPrimaryKeySelective(temp) ;
    }



    /**
     * 把某个字段置为 开启/关闭
     * @param fieldId
     * @return
     */
    public Boolean updateFieldStatus(Long fieldId, Integer status) {
        boolean flag = Boolean.FALSE ;

        if (fieldId!=null) {
            Field temp = fieldMapper.selectByPrimaryKey(fieldId) ;
            temp.setStatus(status);// 是否开启(0：开启  1：关闭)
            temp.setUpdateTime(new Date());
            fieldMapper.updateByPrimaryKeySelective(temp) ;

            // 若是字段停用，则对其依赖的字段需要同步停用
            if (status == ResearchConsts.DELETED_YES) {
                List<FieldVo> childFieldList = this.getChildListById(fieldId) ;
                if (childFieldList!=null && childFieldList.size()>0) {
                    for (FieldVo fieldVo :childFieldList) {
                        Field field = new Field() ;
                        field.setId(fieldVo.getId());
                        field.setStatus(ResearchConsts.DELETED_YES);// 1：关闭
                        field.setUpdateTime(new Date());
                        fieldMapper.updateByPrimaryKeySelective(field) ;
                    }
                }
            }

            flag = Boolean.TRUE ;
        }

        return flag ;
    }




    /**
     * 校验英文名称，在该字典下不重复（未删除）
     * @param dictionaryId
     * @param name
     * @param fieldId
     * @return
     */
    public boolean checkFieldNameExists(Long dictionaryId, String name, Long fieldId) {
        boolean isRepeat = Boolean.FALSE ;

        List<FieldVo> fieldVoList = this.getFieldListByDictId(dictionaryId, null) ;
        List<FieldVo> repeatList = null ;
        if (fieldVoList!=null && fieldVoList.size()>0) {
            if (fieldId!=null) {
                FieldVo temp = new FieldVo() ;
                temp.setId(fieldId);
                temp.setName(name);
                // 编辑的时候判断name是否重复
                repeatList = fieldMapper.checkFieldNameExists(temp) ;
            } else {
                repeatList = fieldVoList.stream().filter(field -> name.equals(field.getName())).collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }



    /**
     * 校验中文名称，在该字典下不重复（未删除）
     * @param dictionaryId
     * @param label
     * @param fieldId
     * @return
     */
    public boolean checkFieldLabelExists(Long dictionaryId, String label, Long fieldId) {
        boolean isRepeat = Boolean.FALSE ;

        List<FieldVo> fieldVoList = this.getFieldListByDictId(dictionaryId, null) ;

        List<FieldVo> repeatList = null ;
        if (fieldVoList!=null && fieldVoList.size()>0) {
            if (fieldId!=null) {
                FieldVo temp = new FieldVo() ;
                temp.setId(fieldId);
                temp.setLabel(label);
                // 编辑的时候判断label是否重复
                repeatList = fieldMapper.checkFieldLabelExists(temp) ;
            } else {
                repeatList = fieldVoList.stream().filter(field -> label.equals(field.getLabel())) .collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }



    /**
     * 根据field id 编辑field
     * @param fieldId
     * @return
     */
    public FieldVo getFieldById(Long fieldId) {
        FieldVo fieldVo = new FieldVo() ;

        Field field = fieldMapper.selectByPrimaryKey(fieldId) ;
        BeanUtils.copyProperties(field, fieldVo);

        if (field!=null && (field.getFieldType()==1 || field.getFieldType()==2)) {
            // 如果字段类型是单选题或者多选题，需要查询下拉选项
            List<FieldOptionVo> fieldOptionVoList = fieldMapper.getOptionListByFieldId(fieldId) ;
            fieldVo.setFieldOptionVoList(fieldOptionVoList);
        }
        return fieldVo ;
    }



    

    /**
     *  修改某个字段以及所有的子级字段 为删除
     *
     * @param objectList
     */
    @Transactional
    public Msg updateFieldDeleted(List<Long> objectList) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {

            for (Long fieldId : objectList) {
                // 1, 置field表的deleted状态为 已删除
                this.updateSingleFieldDeleted(fieldId);

                // 2,查询当前字段的所有子节点id，并置 所有子节点的字段状态为  已删除
                List<FieldVo> childFieldList = this.getChildListById(fieldId) ;
                if (childFieldList!=null && childFieldList.size()>0) {
                    for (FieldVo fieldVo : childFieldList) {
                        this.updateSingleFieldDeleted(fieldVo.getId()) ;
                    }
                }
            }

            msg.setSucFlag(Boolean.TRUE);
            msg.setDesc(ResearchConsts.FIELD_DEL_SUC_MSG);//删除指标成功

        } catch (Exception e) {
            log.error(e.getMessage());

            //删除异常
            msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_EXCEPTION);
        }
        return msg ;
    }



}
