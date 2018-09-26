package cn.ck.controller.studio;

import cn.ck.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/9/26 16:01
 **/
@Controller
@RequestMapping("/studio")
public class studioController {
    @Autowired
    private StudioService studioService;

    @RequestMapping("/studioNone")
    public String test1(){
        return "studio/studio_creat";
    }
}
