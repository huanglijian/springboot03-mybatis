package cn.ck.controller;

import cn.ck.entity.Alluser;
import cn.ck.entity.Jobs;
import cn.ck.service.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 此控制器
 * 用于测试页面
 */

@Controller
@RequestMapping("/ckadmin")
public class CkAdminContorller extends AbstractController {
    private JobsService jobsService;
    @RequestMapping("/cwh")
    public String cwhTest(){
        return "resource/resource_player";
    }





    @RequestMapping("/search")
    public String search() {
//        Alluser alluser = getUser();
//        System.out.println("======" + alluser.getAllId());


        return "/jobs/search";
    }

    @RequestMapping("login")
    public String login() {
        return "login/login";
    }

    @RequestMapping("pwd_forget_2")
    public String pwd_forget_2() {
        return "login/pwd_forget_2";
    }

    @RequestMapping("pwd_forget_3")
    public String pwd_forget_3() {
        return "login/pwd_forget_3";
    }

    @RequestMapping("pwd_forget_4")
    public String pwd_forget_4() {
        return "login/pwd_forget_4";
    }

    private Jobs jobs;
    @GetMapping(value = "/search/jobs/{id}")
    public Jobs findJobsById(@PathVariable("id") int id){


//        System.out.println("123123");
//        jobs = jobsService.selectById(id);
//        System.out.println("++");
//        System.out.println(jobs);
//        return jobsService.selectById(id);
        return null;

    }

    @RequestMapping("/lxn")
    public String lxntest() {
        return "promulgator/prom_Account";
    }

    @RequestMapping("/psw")
    public String pswtest() {
        return "users/pc_mfy_information";
    }
}