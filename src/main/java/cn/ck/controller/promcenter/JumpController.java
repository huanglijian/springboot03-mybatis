package cn.ck.controller.promcenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*prom跳转controller*/
@Controller
@RequestMapping("/pcjump")
public class JumpController {

    /*跳转账户页面*/
    @RequestMapping("/account")
    public String accountjump(){
        return "/promulgator/prom_Account";
    }

    /*跳转发布项目页面*/
    @RequestMapping("/projectcreat")
    public String creatproject(){
        return "/promulgator/prom_projCreat";
    }

    /*跳转发布者竞标管理页面*/
    @RequestMapping("/proBid")
    public String projBidding(){
        return "/promulgator/prom_projBidding";
    }

    /*跳转竞标项目详细信息*/
    @RequestMapping("/proBiddetail")
    public String projBiddetail(){
        return "/promulgator/prom_projBidDetails";
    }

}
