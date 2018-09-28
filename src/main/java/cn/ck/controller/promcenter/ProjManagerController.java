package cn.ck.controller.promcenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/promcenter")
public class ProjManagerController {

    @RequestMapping("/prompauseproj")
    public void prompauseproj(@RequestParam("id") String id){
        System.out.println(id);
    }
}
