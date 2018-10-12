package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
import cn.ck.entity.bean.JobUsers;
import cn.ck.service.JobsService;
import cn.ck.service.JobuserService;
import cn.ck.service.StudioService;
import cn.ck.service.UsersService;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/9/29 11:36
 **/
@Controller
@RequestMapping("/stumember")
public class stumemberController extends AbstractController {
    @Autowired
    private StudioService studioService;
    @Autowired
    private JobsService jobsService;
    @Autowired
    private JobuserService jobuserService;
    @Autowired
    private UsersService usersService;

    /*点击确认中止，置 发布方中止 为 项目中止*/
    @PostMapping("/userApplyquit/{id}")
    @ResponseBody
    public boolean userApplyquit(@PathVariable("id") String id){

        System.out.println(id);
        Users user = usersService.selectOne(new EntityWrapper<Users>().eq("user_id",id));
//        user.setUserQuittme(new Date());

        usersService.updateAllColumnById(user);
        return true;
    }





}
