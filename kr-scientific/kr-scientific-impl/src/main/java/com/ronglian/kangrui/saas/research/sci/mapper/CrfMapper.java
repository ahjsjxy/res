package com.ronglian.kangrui.saas.research.sci.mapper;

import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.vo.CrfFormVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface CrfMapper extends Mapper<Crf> {

    /**
     * 查询项目下的有效的crf表单
     */
    public List<CrfFormVo> getCrfListByStudyId(CrfFormVo crfFormVo) ;


    /**
     * 校验Crf表单不重复（1：项目下的crf表单不重复  2：未删除的表单）
     * @return
     */
    public List<CrfFormVo> checkCrfFormNameExists(CrfFormVo crfFormVo) ;



    /**
     * 查询Crf表单下有效的题组
     */
    public List<CrfFormVo> getCrfTestletsListByCrfFormId(CrfFormVo crfFormVo) ;



    /**
     * 校验Crf表单下的题组不重复（1：Crf表单下的题组不重复  2：未删除的题组）
     * @return
     */
    public List<CrfFormVo> checkCrfTestletsNameExists(CrfFormVo crfFormVo) ;



    /**
     * 查询动态表单是否有数据
     * @param crfFormVo
     * @return
     */
    public List<Map> getFormData(CrfFormVo crfFormVo);


    /**
     * crf 下的所有题组置为无效
     * @param crf
     */
    public void updateTestletsDelByCrfFormId(Crf crf) ;


    /**
     * 判断题组下已创建字段的列，是否已存储数据
     * @return
     */
    public List<Map> getFieldsInfoData(CrfFormVo crfFormVo) ;


    /**
     * 查询 study下面crf form 列表
     * @param crfFormVo
     * @return
     */
    public List<CrfFormVo> getCrfFormListByStudyId(CrfFormVo crfFormVo) ;


}