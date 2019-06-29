package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Disease;
import com.ronglian.kangrui.saas.research.sci.entity.DiseaseField;
import com.ronglian.kangrui.saas.research.sci.mapper.DiseaseMapper;
import com.ronglian.kangrui.saas.research.sci.vo.DiseaseVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class DiseaseBiz extends BaseBiz<DiseaseMapper,Disease> {

    @Autowired
    private DiseaseMapper diseaseMapper ;



    /***
     * 查询疾病列表
     * @param name
     * @return
     */
    public List<DiseaseVo> getDiseaseList(String name) {
        return diseaseMapper.getDiseaseList(name) ;
    }



    /**
     * 保存疾病
     * @param disease
     * @return
     */
    public Msg saveDiseaseInfo(Disease disease) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        boolean repeatFlag = this.checkDiseaseNameExists(disease.getName().trim(), disease.getId()) ;
        if (!repeatFlag) {
            if (disease!=null && disease.getId()!=null) {
                //编辑
                disease = this.updateDiseaseById(disease) ;
            } else {
                //新增
                disease = this.addDiseaseInfo(disease) ;
            }

            msg.setSucFlag(Boolean.TRUE);
            msg.setData(disease);

        } else {
            // 存在重复
            msg.setDesc(RestCodeConstants.STATUS_CODE_SAVE_CHECK_REPEAT);

            log.info("校验疾病名称，存在重复================");
            log.info("name=={}, diseaseId=={}", disease.getName(),  disease.getId());
        }

        return msg ;
    }


    /**
     * 新增疾病
     * @param temp
     * @return
     */
    private Disease addDiseaseInfo(Disease temp) {
        Disease disease = new Disease() ;
        disease.setName(temp.getName());
        disease.setDeleted(ResearchConsts.DELETED_NO) ;// 0:未删除
        disease.setStatus(ResearchConsts.DELETED_NO);// 0:开启
        diseaseMapper.insertSelective(disease) ;

        disease.setCode(disease.getId()+"");
        disease.setUpdateTime(new Date());
        diseaseMapper.updateByPrimaryKeySelective(disease) ;

        return disease ;
    }




    /**
     * 编辑疾病保存
     * @param disease
     * @return
     */
    private Disease updateDiseaseById(Disease disease) {
        Disease temp = diseaseMapper.selectByPrimaryKey(disease.getId()) ;
        temp.setName(disease.getName());
        temp.setUpdateTime(new Date());
        diseaseMapper.updateByPrimaryKeySelective(temp) ;
        return temp ;
    }




    /**
     * 校验疾病名称是否重复
     * @param name
     * @param diseaseId
     * @return
     */
    public boolean checkDiseaseNameExists(String name, Long diseaseId) {
        boolean isRepeat = Boolean.FALSE ;

        List<DiseaseVo> repeatList = null ;
        List<DiseaseVo> diseaseVoList = this.getDiseaseList(null) ;
        if (diseaseVoList!=null && diseaseVoList.size()>0) {
            if (diseaseId!=null) {
                Disease disease = new Disease() ;
                disease.setId(diseaseId);
                disease.setName(name);
                repeatList = diseaseMapper.checkDiseaseNameExists(disease) ;
            } else {
                repeatList = diseaseVoList.stream().filter(diseaseVo -> name.equals(diseaseVo.getName())).collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }



    /**
     * 删除疾病
     * @param objectIdStr
     * @return
     */
    @Transactional
    public Msg deleteDisease(String objectIdStr) {
        Msg msg = new Msg() ;

        List<Long> objectList = Arrays.asList(objectIdStr.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(toList());
        for (Long diseaseId : objectList) {
            //  置 disease 表的deleted状态为 已删除
            updateDiseaseDeleted(diseaseId) ;
        }

        msg.setSucFlag(Boolean.TRUE);
        msg.setDesc(ResearchConsts.DISEASE_DEL_SUC_MSG);

        return msg ;
    }



    /**
     * 置disease 表  单个字段的deleted状态为 已删除
     * @param diseaseId
     */
    private void updateDiseaseDeleted(Long diseaseId) {
        Disease disease = diseaseMapper.selectByPrimaryKey(diseaseId) ;
        disease.setUpdateTime(new Date());
        disease.setDeleted(ResearchConsts.DELETED_YES);
        diseaseMapper.updateByPrimaryKeySelective(disease) ;
    }



    /**
     * 把某疾病置为 开启/关闭
     * @param diseaseId
     * @return
     */
    public Boolean updateDiseaseStatus(Long diseaseId, Integer status) {
        boolean flag = Boolean.FALSE ;

        if (diseaseId!=null) {
            Disease temp = diseaseMapper.selectByPrimaryKey(diseaseId);
            temp.setStatus(status);// 是否开启(0：开启  1：关闭)
            temp.setUpdateTime(new Date());
            diseaseMapper.updateByPrimaryKeySelective(temp);

            flag = Boolean.TRUE ;
        }

        return flag ;
    }





    /***
     * 查询这个疾病下面这个字典  选中的字段
     * @param diseaseId
     * @param dictId
     * @param fieldId
     * @return
     */
    public  List<FieldVo> getSelectFieldByDiseaseId(Long diseaseId, Long dictId, Long fieldId) {
        // 查询这个疾病下面这个字典  选中的字段
        DiseaseField diseaseField = new DiseaseField() ;
        diseaseField.setDictId(dictId);
        diseaseField.setDiseaseId(diseaseId);
        diseaseField.setFieldId(fieldId);
        List<FieldVo> selectFieldList = diseaseMapper.getSelectFieldByDiseaseId(diseaseField) ;
        return selectFieldList ;
    }


}
