package cn.ck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ckadmin")
public class CkAdminContorller {

    @RequestMapping("/cwh")
    public String cwhTest(){
        return "resource/resource_player";
    }

    @RequestMapping("/hlj")
    public String test(){
        return "search";
    }
}
