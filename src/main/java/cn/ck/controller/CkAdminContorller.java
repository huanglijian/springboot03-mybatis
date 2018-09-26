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
        return "jobs/search";
    }
    @RequestMapping("/search")
    public String search(){
        return "/jobs/search";
    }

    @RequestMapping("login")
    public String login(){
        return "login/login";
    }

    @RequestMapping("/lxn")
    public String lxntest(){
        return "promulgator/prom_Account";
    }

    @RequestMapping("/psw")
    public String pswtest(){
        return "users/pc_mfy_information";
    }
    @RequestMapping("/mzb")
    public String mzbtest(){
        return "studio/studio_creat";
    }

}