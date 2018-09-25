package cn.ck.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 此控制器
 * 用于测试页面
 */

@Controller
@RequestMapping("/ckadmin")
public class CkAdminContorller {

    @RequestMapping("/cwh")
    @RequiresRoles("admin")
    public String cwhTest(){
        return "resource/resource_player";
    }

    @RequestMapping("/hlj")
    public String test(){
        return "search";
    }

    @RequestMapping("/lxn")
    public String lxntest(){
        return "promulgator/prom_uppaypwd";
    }

    @RequestMapping("/mzb")
    public String test1(){
        return "studio/studio_index";
    }
}
