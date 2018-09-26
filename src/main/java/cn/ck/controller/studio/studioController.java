package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Studio;
import cn.ck.entity.Users;
import cn.ck.mapper.UsersMapper;
import cn.ck.service.StudioService;
import cn.ck.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/9/26 16:01
 **/
@Controller
@RequestMapping("/studio")
public class studioController extends AbstractController {
    @Autowired
    private StudioService studioService;
    @Autowired
    private UsersService usersService;

    @RequestMapping("/studioNone")
    public String test1(){
        Users u = usersService.selectById(getUser().getAllId());
        if(u.getUserStudio() == null)
            return "studio/studio_creat";
        else
            return "studio/studio_index";
    }

    @RequestMapping("/studioList")
    public Object list(){
        Studio studio = studioService.selectById(getUser().getAllId());
        return studio;
    }

    @RequestMapping("/studioAdd")
    public void add(HttpServletRequest req, Studio studio){
        String yhid = getUser().getAllId();
        String stuId = studioService.insertStudio(studio,yhid);
        usersService.updateStuid(yhid,stuId);
    }
}
