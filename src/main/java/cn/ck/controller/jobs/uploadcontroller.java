package cn.ck.controller.jobs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/upload")
public class uploadcontroller {

    @GetMapping("/sub")
    public String submit(String user){
        System.out.println("======uesr: "+user);
        return "/ForJob/search";
    }
}
