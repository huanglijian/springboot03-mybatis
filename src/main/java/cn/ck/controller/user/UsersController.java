package cn.ck.controller.user;


import cn.ck.controller.AbstractController;
import cn.ck.entity.Alluser;
import cn.ck.entity.Notice;
import cn.ck.entity.Users;
import cn.ck.service.NoticeService;
import cn.ck.service.UsersService;
import cn.ck.utils.ResponseBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UsersController extends AbstractController {

    @Autowired
    UsersService usersService;
    @Autowired
    NoticeService noticeService;

    //跳转当前登录用户个人资料页面
    @RequestMapping("/user_info")
    public String user_info(){
        return "users/pc_information";
    }

    //跳转修改用户资料页面
    @RequestMapping("/user_mfyinfo")
    public String user_mfyinfo(){
        return "users/pc_mfy_information";
    }

    //跳转修改用户通知消息页面
    @RequestMapping("/user_sysn")
    public String user_sysn(){
        return "users/pc_sysn";
    }

    //获取当前登录用户信息
    @RequestMapping("/get_info")
    @ResponseBody
    public Map<String, Object> huoqu_info(){
//        System.out.println("+++++++++++++++++++++++++++++++++");
//        System.out.println(getUser().getAllEmail());
//        getUser().setAllEmail("1174060989@qq.com");
        Map<String,Object> user=new HashMap<>();
        Users u = usersService.selectById(getUser().getAllId());
        //把当前登录用户对象放入Map传到前台
        user.put("user",u);
        user.put("alluser",getUser());
        return user;
    }

    //获取当前登录用户的系统通知消息
    @RequestMapping("/get_sysn")
    @ResponseBody
    public List<Notice> get_sysn(){
        Map<String,Object> cm=new HashMap<>();
        cm.put("noti_foreid",getUser().getAllId());
        List<Notice> notice=noticeService.selectByMap(cm);
//        for (Notice no:notice) {
//            System.out.println(no.getNotiMsg());
//        }
        return notice;
    }

}
