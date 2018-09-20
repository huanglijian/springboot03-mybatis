package cn.ck.controller.promcenter;


import cn.ck.controller.AbstractController;
import cn.ck.entity.Alluser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/prom")
public class AccountController {

    @RequestMapping("/account")
    @ResponseBody
    public String account() {
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();

        return "promulgator/prom_Account";
    }
}
