package cn.ck.controller.user;


import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
import cn.ck.entity.bean.PjCol;
import cn.ck.entity.bean.ResCol;
import cn.ck.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
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
    @Autowired
    ProjectService projectService;
    @Autowired
    CollectpjService collectpjService;
    @Autowired
    PromulgatorService promulgatorService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    CollectresService collectresService;

    int start=0;//记录当前页 默认为“0“
    int size=5; //记录每页条目数，默认为”10“
    String state=null;    //记录当前页状态


    //跳转当前登录用户个人资料页面
    @RequestMapping("/pc_information.html")
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
    public String user_sysn(@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "1") int size){
        this.start=start;
        this.size=size;
        return "users/pc_sysn";
    }

    //跳转用户项目收藏页面
    @RequestMapping("/user_pjcol")
    public String user_pjcol(@RequestParam(value = "state", defaultValue = "0")
              String state,@RequestParam(value = "start", defaultValue = "1")
            int start,@RequestParam(value = "size", defaultValue = "5") int size){
        this.state=state;
        this.start=start;
        this.size=size;
        return "users/pc_xiangmu_collection";
    }

    //跳转用户视频收藏页面
    @RequestMapping("/user_veocol")
    public String user_yccol(@RequestParam(value = "start", defaultValue = "1") int start,
                             @RequestParam(value = "size", defaultValue = "1") int size){
        this.start=start;
        this.size=size;
        return "users/pc_video_collection";
    }

    //获取当前登录用户信息
    @RequestMapping("/get_info")
    @ResponseBody
    public Map<String, Object> huoqu_info(){
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
    public PageInfo<Notice> get_sysn(){
        PageHelper.startPage(start,size);//,"noti_time desc"
        List<Notice> notices=noticeService.selectNoti(getUser().getAllId());
        PageInfo<Notice> page = new PageInfo<>(notices);
        return page;
    }

    //获取当前登录用户的收藏项目
    @RequestMapping("/get_pjcol")
    @ResponseBody
    public PageInfo<PjCol> get_pjcol(){

        PageHelper.startPage(this.start,this.size);
        Map<String,Object> map=new HashMap<>();
        map.put("id",getUser().getAllId());
        map.put("state",this.state);
        List<PjCol> pjColList= projectService.selectPjCol(map);
        //设置项目发布者的姓名、竞标状态和标签属性
        for(PjCol p:pjColList) {
            p.setProjProm(promulgatorService.selectById(p.getProjProm()).getPromName());
            if (p.getProjState().equals("竞标中")) ;
            else
                p.setProjState("竞标中止");
            p.setProjTag(p.getProjTag().replace(",", "  "));
            p.setProjId(Integer.parseInt(state));
        }
        PageInfo<PjCol> page = new PageInfo<>(pjColList);
        return page;
    }

    //获取当前登录用户的视频收藏信息
    @RequestMapping("/get_veocol")
    @ResponseBody
    public PageInfo<ResCol> get_veocol(){
        PageHelper.startPage(start,size);
        List<ResCol> resColList=resourceService.selectDesc(getUser().getAllId());
        for(ResCol resCol:resColList){
            resCol.setResTag(resCol.getResTag().replace("，","  "));
        }
        PageInfo<ResCol> page = new PageInfo<>(resColList);
        return page;
    }

    //删除通知消息
    @RequestMapping("/delete_sysn")
    @ResponseBody
    public boolean delete_sysn(String notiId){
        if(noticeService.deleteById(Integer.parseInt(notiId)))
            return true;
        else
            return false;
    }

    //删除收藏的项目
    @RequestMapping("/delete_pjcol")
    @ResponseBody
    public boolean delete_pjcol(String pcid){
        if(collectpjService.deleteById(Integer.parseInt(pcid)))
            return true;
        else
            return false;
    }

    //删除收藏的讲堂视频
    @RequestMapping("/delete_veocol")
    @ResponseBody
    public boolean delete_veocol(String vcid){
        if(collectresService.deleteById(Integer.parseInt(vcid)))
            return true;
        else
            return false;
    }

    //test()
    /*@RequestMapping("/test")
    @ResponseBody
    public List<PjCol> test(){
        List<PjCol> pjColList= projectService.selectPjCol(getUser().getAllId());
        return pjColList;
    }*/
}
