package com.ronglian.kangrui.saas.research.generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-06-06 13:34
 */
@Controller
@RequestMapping("")
public class HomeController{


    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(Map<String,Object> map){
        return "index";
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String home(Map<String,Object> map){
        return "index";
    }

    @RequestMapping(value = "about",method = RequestMethod.GET)
    public String about(){
        return "about";
    }
    @RequestMapping(value = "generator",method = RequestMethod.GET)
    public String user(){
        return "generator/list";
    }
}
