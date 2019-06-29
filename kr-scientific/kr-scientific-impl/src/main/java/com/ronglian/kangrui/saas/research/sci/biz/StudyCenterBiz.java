package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCenter;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyCenterMapper;
import com.ronglian.kangrui.saas.research.sci.vo.StudyAddVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-27 16:41
 **/
@Service
public class StudyCenterBiz extends BaseBiz<StudyCenterMapper, StudyCenter> {

    @Autowired
    private StudyCenterMapper studyCenterMapper ;



    /**
     * 获取项目选中的中心
     * @param studyId
     * @return
     */
    public List<HospCenterVo> getSelectedCenterList(Long studyId){
        return studyCenterMapper.getSelectedCenterList(studyId) ;
    }


    /**
     * 项目-获取未选择的中心
     * @param studyId
     * @return
     */
    public List<HospCenterVo> getValidCenterList(Long studyId){
        return studyCenterMapper.getValidCenterList(studyId) ;
    }



    /**
     * 根据项目ID 删除关联的中心集合
     * @param studyId
     */
    public void deleteByStudyId(Long studyId) {
        studyCenterMapper.deleteByStudyId(studyId) ;
    }


    /**
     * 先删除项目下的原来中心，新建-项目关联的中心
     *
     * @param studyAddVo
     */
    public void saveStudyCenterInfo(StudyAddVo studyAddVo) {
        //先删除项目下的原来中心
        this.deleteByStudyId(studyAddVo.getId());

        // 新建-项目关联的中心
        List<HospCenterVo> centerList = studyAddVo.getCenterList() ;
        for (HospCenterVo hospCenterVo : centerList) {
            StudyCenter studyCenter = new StudyCenter() ;
            studyCenter.setCenterId(hospCenterVo.getId());
            studyCenter.setStudyId(studyAddVo.getId());
            this.insertSelective(studyCenter) ;
        }
    }


}
