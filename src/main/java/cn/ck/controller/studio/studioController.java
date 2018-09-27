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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(getUser().getAllId());
        if(u.getUserStudio() == null)
            return "studio/studio_creat";
        else
            return "studio/studio_index";
    }

    @RequestMapping("/studioList")
    @ResponseBody
    public Map<String, Object> list(){

        Map<String,Object> studio = new HashMap<>();
        Studio stu = studioService.selectByzzid(getUser().getAllId());

        System.out.println(stu.getStuField());
        studio.put("studio",stu);
        return studio;
    }

    @RequestMapping("/studioAdd")
    public void add(HttpServletRequest req, Studio studio){
        String yhid = getUser().getAllId();
        String stuId = studioService.insertStudio(studio,yhid);
        usersService.updateStuid(yhid,stuId);
    }
}
