package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.CrfDictField;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCrf;
import com.ronglian.kangrui.saas.research.sci.mapper.CrfDictFieldMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.CrfMapper;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyCrfMapper;
import com.ronglian.kangrui.saas.research.sci.vo.CrfFormVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class CrfBiz extends BaseBiz<CrfMapper,Crf> {

    @Autowired
    private CrfDictFieldMapper crfDictFieldMapper;

    @Autowired
    private CrfMapper crfMapper ;

    @Autowired
    private StudyCrfMapper studyCrfMapper ;



    /**
     * 判断字段是否被项目引用
     *
     * @param fieldIdList
     * @return
     */
    public List<CrfDictField> checkReferenceListByFieldId(List<Long> fieldIdList) {
        return crfDictFieldMapper.getCrfDictFieldListByFieldIds(fieldIdList) ;
    }



    /**
     * 新建CRF form 保存
     * @param crfFormVo
     * @return
     */
    public Msg saveCrfForm(CrfFormVo crfFormVo) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);


        // 校验-项目下Crf表单名称是否重复
        boolean repeatFlag = this.checkCrfFormNameExists(crfFormVo.getName().trim(), crfFormVo.getStudyId(), crfFormVo.getId()) ;
        if (!repeatFlag) {

            Crf crf = new Crf() ;
            BeanUtils.copyProperties(crfFormVo, crf);

            if (crfFormVo.getId()!=null) {
                // 编辑crf
                crf.setUpdateTime(new Date());
                crfMapper.updateByPrimaryKeySelective(crf) ;

            } else {
                // 新建crf
                crf.setCreateTime(new Date());
                crf.setType(ResearchConsts.CRF_TYPE_FORM);//表单类型 1:CRF
                crf.setDeleted(ResearchConsts.DELETED_NO);
                crf.setParentId(ResearchConsts.CRF_FORM_PARENT_ROOT);
                crf.setLevel(ResearchConsts.CRF_TYPE_FORM);//层级 1: CRF
                crf.setGenerateToDb(ResearchConsts.GENERATE_TO_DB_NO);//动态表单是否已创建（0：未创建）
                crfMapper.insertSelective(crf) ;

                // 保存 study_crf (新建的时候，保存关系；编辑不需要)
                StudyCrf studyCrf = new StudyCrf() ;
                studyCrf.setStudyId(crfFormVo.getStudyId());
                studyCrf.setCrfFormId(crf.getId());
                studyCrfMapper.insertSelective(studyCrf) ;

            }

            msg.setSucFlag(Boolean.TRUE);
            msg.setData(crf);

        } else {
            // 存在重复
            msg.setDesc(RestCodeConstants.STATUS_CODE_SAVE_CHECK_REPEAT);

            log.info("校验项目下Crf表单名称，存在重复================");
            log.info("name=={}, studyId=={}, crfFormId={}", crfFormVo.getName(), crfFormVo.getStudyId(), crfFormVo.getId());
        }

        return msg ;
    }



    /**
     * 校验项目下Crf表单重复（1：项目下的crf表单不重复  2：未删除的表单）
     * @param name
     * @param crfFormId
     * @return
     */
    public boolean checkCrfFormNameExists(String name, Long studyId, Long crfFormId) {
        boolean isRepeat = Boolean.FALSE ;

        // 准备查询参数
        CrfFormVo crfFormVoTemp = new CrfFormVo() ;
        crfFormVoTemp.setStudyId(studyId);
        List<CrfFormVo> crfFormVoList = crfMapper.getCrfListByStudyId(crfFormVoTemp) ;
        List<CrfFormVo> repeatList = null ;
        if (crfFormVoList!=null && crfFormVoList.size()>0) {
            if (crfFormId!=null) {
                CrfFormVo temp = new CrfFormVo() ;
                temp.setId(crfFormId);
                temp.setStudyId(studyId);
                temp.setName(name);
                // 编辑的时候判断name是否重复
                repeatList = crfMapper.checkCrfFormNameExists(temp) ;
            } else {
                repeatList = crfFormVoList.stream().filter(crfFormVo -> name.equals(crfFormVo.getName())).collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }





    /**
     * 新建题组保存
     * @param crfFormVo
     * @return
     */
    public Msg saveCrfTestlets(CrfFormVo crfFormVo) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);


        // 校验-Crf表单下题组名称是否重复
        boolean repeatFlag = this.checkTestletsNameExists(crfFormVo.getName().trim(), crfFormVo.getParentId(), crfFormVo.getId()) ;
        if (!repeatFlag) {
            Crf crf = new Crf() ;
            BeanUtils.copyProperties(crfFormVo, crf);

            if (crfFormVo.getId()!=null) {
                // 编辑题组
                crf.setUpdateTime(new Date());
                crfMapper.updateByPrimaryKeySelective(crf) ;
            }else {
                // 新建题组
                crf.setCreateTime(new Date());
                crf.setType(ResearchConsts.CRF_TYPE_TESTLETS);//表单类型 2:题组
                crf.setDeleted(ResearchConsts.DELETED_NO);
                crf.setParentId(crfFormVo.getParentId());
                crf.setLevel(ResearchConsts.CRF_TYPE_TESTLETS);//层级 2:题组
                crfMapper.insertSelective(crf) ;
            }

            msg.setSucFlag(Boolean.TRUE);
            msg.setData(crf);

        }else {
            // 存在重复
            msg.setDesc(RestCodeConstants.STATUS_CODE_SAVE_CHECK_REPEAT);

            log.info("校验Crf表单下的题组名称，存在重复================");
            log.info("name=={}, crfFormId=={}, crfId={}", crfFormVo.getName(), crfFormVo.getParentId(), crfFormVo.getId());
        }

        return msg ;
    }




    /**
     * 校验Crf表单下的题组不重复（1：Crf表单下的题组不重复  2：未删除的题组）
     * @param name
     * @param crfFormId
     * @return
     */
    public boolean checkTestletsNameExists(String name, Long crfFormId, Long crfId) {
        boolean isRepeat = Boolean.FALSE ;

        // 准备查询参数
        CrfFormVo crfFormVoTemp = new CrfFormVo() ;
        crfFormVoTemp.setParentId(crfFormId);
        List<CrfFormVo> crfFormVoList = crfMapper.getCrfTestletsListByCrfFormId(crfFormVoTemp) ;
        List<CrfFormVo> repeatList = null ;
        if (crfFormVoList!=null && crfFormVoList.size()>0) {
            if (crfId!=null) {
                CrfFormVo temp = new CrfFormVo() ;
                temp.setId(crfId);
                temp.setParentId(crfFormId);
                temp.setName(name);
                // 编辑的时候判断name是否重复
                repeatList = crfMapper.checkCrfTestletsNameExists(temp) ;
            } else {
                repeatList = crfFormVoList.stream().filter(crfFormVo -> name.equals(crfFormVo.getName())).collect(toList());
            }
        }

        isRepeat = (repeatList!=null && repeatList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;
        return isRepeat ;
    }




    /**
     * 查询动态表单是否有数据
     * @param crfFormVo
     * @return
     */
    public List<Map> getFormData(CrfFormVo crfFormVo){
        return crfMapper.getFormData(crfFormVo) ;
    }




    /**
     * crf 表单无数据，1:把CRF表单置为无效  2: crf 下的所有题组置为无效 3:CRF下的所有字段置为无效
     *
     * @param studyId
     * @param crfFormId
     */
    public Msg updateCrfFormDeleted(Long studyId, Long crfFormId) {
        Msg msg = new Msg() ;

        // crf 表单无数据，1:把CRF表单置为无效
        Crf temp = new Crf() ;
        temp.setId(crfFormId);
        temp.setDeleted(ResearchConsts.DELETED_YES);
        temp.setUpdateTime(new Date());
        crfMapper.updateByPrimaryKeySelective(temp);

        // 2: crf 下的所有题组置为无效
        temp = new Crf() ;
        temp.setParentId(crfFormId);
        crfMapper.updateTestletsDelByCrfFormId(temp) ;


        // 3:CRF下的所有字段置为无效
        CrfDictField crfDictField = new CrfDictField() ;
        crfDictField.setCrfFormId(crfFormId);
        crfDictFieldMapper.updateCrfDictFieldByCrf(crfDictField) ;

        // 4 : 删除study_crf
        StudyCrf studyCrf = new StudyCrf() ;
        studyCrf.setCrfFormId(crfFormId);
        studyCrf.setStudyId(studyId);
        studyCrfMapper.delete(studyCrf) ;

        msg.setSucFlag(Boolean.TRUE);
        msg.setDesc(ResearchConsts.CRF_FORM_DEL_SUC_MSG);

        return msg;

    }




    /**
     * 判断题组下已创建字段的列，是否已存储数据
     * @param crfFormVo
     * @return
     */
    public List<Map> getFieldsInfoData(CrfFormVo crfFormVo){
        return crfMapper.getFieldsInfoData(crfFormVo) ;
    }




    /**
     *  题组无数据，1:把题组置为无效  2: 题组下的所有字段置为无效
     * @param crfSecondId
     */
    public Msg updateCrfTestletsDeleted(Long crfSecondId) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            // 题组无数据，1:把题组置为无效
            Crf temp = new Crf() ;
            temp.setId(crfSecondId);
            temp.setDeleted(ResearchConsts.DELETED_YES);
            temp.setUpdateTime(new Date());
            crfMapper.updateByPrimaryKeySelective(temp);


            // 3:CRF下的所有字段置为无效
            CrfDictField crfDictField = new CrfDictField() ;
            crfDictField.setCrfId(crfSecondId);
            crfDictFieldMapper.updateCrfDictFieldByCrf(crfDictField) ;

            msg.setSucFlag(Boolean.TRUE);
            msg.setDesc(ResearchConsts.CRF_TESTLETS_DEL_SUC_MSG);

        } catch (Exception e) {
            msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_EXCEPTION);
            e.printStackTrace();
        }

        return msg;

    }


    /**
     * 查询 study下面crf form 列表
     * @param studyId
     * @return
     */
    public List<CrfFormVo> getCrfFormListByStudyId(Long studyId){
        CrfFormVo crfFormVo = new CrfFormVo() ;
        crfFormVo.setStudyId(studyId);
        return crfMapper.getCrfFormListByStudyId(crfFormVo) ;
    }




    /**
     * 更新CRF的table_name和generate_to_db
     * @param crfFormVo
     */
    public void updateCrfFormGenerateToDB(CrfFormVo crfFormVo) {
        Crf crf = new Crf() ;
        BeanUtils.copyProperties(crfFormVo, crf);
        crf.setGenerateToDb(ResearchConsts.GENERATE_TO_DB_YES);
        crf.setUpdateTime(new Date());
        crfMapper.updateByPrimaryKeySelective(crf) ;
    }


}
