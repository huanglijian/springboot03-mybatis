package cn.ck.controller.promcenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/promcenter")
public class ProjectController {

    @PostMapping("/projectcreat")
    @ResponseBody
    public void projectcreat(){

    }
}
