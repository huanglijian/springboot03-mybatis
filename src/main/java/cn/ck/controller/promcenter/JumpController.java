package cn.ck.controller.promcenter;

import cn.ck.entity.Alluser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /*跳转竞标中项目详细信息*/
    @RequestMapping("/proBiddetail")
    public String projBiddetail(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "/promulgator/prom_projBidDetails";
    }

    /*跳转竞标结束项目详细信息*/
    @RequestMapping("/proBidfindetail")
    public String proBidfindetail(@RequestParam("finishid") String id, Model model){
        model.addAttribute("id",id);
        return "/promulgator/prom_projBidFinDetail";
    }

    /*跳转项目订单管理界面*/
    @RequestMapping("/projManage")
    public String proManage(){
        return "/promulgator/prom_projManage";
    }

    /*跳转到头像修改页面*/
    @RequestMapping("/projpromimg")
    public String projpromimg(){
        return "/promulgator/prom_updatephoto";
    }

    /*跳转到发布者个人主页*/
    @RequestMapping("/projhomepage")
    public String projhomepage(){
        return "/promulgator/prom_homepage";
    }

    /*跳转资料更新界面*/
    @RequestMapping("/updateprom")
    public String updateprom(){
        return "/promulgator/prom_updateprom";
    }

    /*跳转资金管理界面*/
    @RequestMapping("/priceprom")
    public String priceprom(){
        return "/promulgator/prom_priManage";
    }

    /*跳转充值页面*/
    @RequestMapping("/priceinprom")
    public String priceinprom(){
        return "/promulgator/prom_payin";
    }

    /*跳转提现页面*/
    @RequestMapping("/priceoutprom")
    public String priceoutprom(){
        return "/promulgator/prom_payout";
    }

    /*跳转修改支付密码*/
    @RequestMapping("/paypwdprom")
    public String paypwdprom(){
        return "/promulgator/prom_uppaypwd";
    }

    /*跳转修改登录密码*/
    @RequestMapping("/loginpwdprom")
    public String loginpwdprom(){
        return "/promulgator/prom_uploginpwd";
    }

    /*跳转通知页面*/
    @RequestMapping("/noticeprom")
    public String noticeprom(){
        return "/promulgator/prom_Notice";
    }

    /*跳转邀请列表*/
    @RequestMapping("/inviteprom")
    public String inviteprom(){
        return "/promulgator/prom_projInvit";
    }
}
