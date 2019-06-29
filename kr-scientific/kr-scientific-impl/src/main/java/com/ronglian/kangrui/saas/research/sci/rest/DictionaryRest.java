package com.ronglian.kangrui.saas.research.sci.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.sci.manager.DictionaryManager;
import com.ronglian.kangrui.saas.research.sci.vo.DictionaryVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典
 *
 * @author lanyan
 * @create 2019-03-12 17:59
 **/
@RestController
@RequestMapping("dictionary")
@Slf4j
public class DictionaryRest {

    @Autowired
    private DictionaryManager dictionaryManager ;


    @ApiOperation("字典列表")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<DictionaryVo> pageList(@RequestParam(defaultValue = "10") int limit,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      String name){
        Page<DictionaryVo> result = PageHelper.startPage(page, limit,true);
        name = (StringUtils.isNoneBlank(name)) ? name.trim() : null ;
        List<DictionaryVo> dictionaryVoList = dictionaryManager.getDictionaryList(name) ;
        return new TableResultResponse<DictionaryVo>(result.getTotal(), dictionaryVoList);
    }

}
