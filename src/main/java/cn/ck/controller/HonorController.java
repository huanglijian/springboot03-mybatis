package cn.ck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/honor")
public class HonorController {

    @RequestMapping("honorPage")
    public String honorPage(){
        return "honor/honor";
    }
}
