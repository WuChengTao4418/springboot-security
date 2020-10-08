package com.wct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    @RequestMapping({"/","/index"})
    private String toIndex(){
        return "index";
    }
    @RequestMapping("/toLogin")
    private String toLogin(){
        return "views/login";
    }

    @RequestMapping("level1/{id}")
    private String toLevel1(@PathVariable("id") int id){
        return "views/level1/"+id;
    }
    @RequestMapping("level2/{id}")
    private String toLevel2(@PathVariable("id") int id){
        return "views/level2/"+id;
    }
    @RequestMapping("level3/{id}")
    private String toLevel3(@PathVariable("id") int id){
        return "views/level3/"+id;
    }

}
