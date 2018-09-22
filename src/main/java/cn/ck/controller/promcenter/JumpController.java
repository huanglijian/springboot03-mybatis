package cn.ck.controller.promcenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/pcjump")
public class JumpController {

    @RequestMapping("/account")
    public String accountjump(){
        return "/promulgator/prom_Account";
    }


}
