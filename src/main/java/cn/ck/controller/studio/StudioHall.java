package cn.ck.controller.studio;


import cn.ck.entity.*;
import cn.ck.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/stuhall")
public class StudioHall {
    @Autowired
    StudioService studioService;
    @Autowired
    UsersService usersService;
    @Autowired
    ProjectService projectService;
    @Autowired
    InviteService inviteService;
    @Autowired
    InvitenoticeService invitenoticeService;

    int start;//记录当前页 默认为“0“
    int size; //记录每页条目数，默认为”10“
    String type;
    String local;
    String studioName;
    String sort;
    String stuid;
    String pormId;
    Integer proId;

    //跳转工作室大厅
    @RequestMapping("/hall")
    public String hall(@RequestParam(value = "type",defaultValue = "不限") String type,@RequestParam(value = "local",defaultValue = "不限") String local,@RequestParam(value = "studioName",defaultValue = "") String studioName,@RequestParam(value = "sort",required = false) String sort,@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "4") int size){
        this.type=type;
        this.local=local;
        this.studioName=studioName;
        this.start=start;
        this.size=size;
        this.sort=sort;
        return "studio/studio_hall";
    }

    //跳转工作室大厅
    @RequiresRoles("promulgator")
    @RequestMapping("/hall2")
    public String hall2(@RequestParam(value = "type",defaultValue = "不限") String type,@RequestParam(value = "local",defaultValue = "不限") String local,@RequestParam(value = "studioName",defaultValue = "") String studioName,@RequestParam(value = "sort",required = false) String sort,@RequestParam(value = "proId",required = true) Integer proId,@RequestParam(value = "pormId",required = true) String pormId,@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "4") int size){
        this.pormId=pormId;
        this.proId=proId;
        this.type=type;
        this.local=local;
        this.studioName=studioName;
        this.start=start;
        this.size=size;
        this.sort=sort;
        return "studio/studio_hall2";
    }

    //跳转工作室详情页
    @RequestMapping("/detail")
    public String detail(@RequestParam(value = "stuid")String stuid){
        this.stuid=stuid;
        return "studio/studio_homepage";
    }

    //获取工作室大厅信息
    @RequestMapping("/get_hall")
    @ResponseBody
    public Map<String,Object> get_hall(){
        PageHelper.startPage(start,size);
        List<Studio> studioList=studioService.selectForHall(type,local,studioName,sort);
        PageInfo<Studio> page=new PageInfo<>(studioList);
        Map<String,Object> map=new HashMap<>();
        map.put("page",page);
        map.put("type",type);
        map.put("local",local);
        map.put("studioName",studioName);
        map.put("sort",sort);
        map.put("pormId",pormId);
        map.put("proId",proId);
        return map;
    }


    //获取工作室详情
    @RequestMapping("/get_detail")
    @ResponseBody
    public Map<String,Object> get_detail(){
        Map<String,Object> map=new HashMap<>();
        Studio studio=studioService.selectById(stuid);
        Users user=usersService.selectById(studio.getStuCreatid());
        List<Project> projectList=projectService.selectList(new EntityWrapper<Project>().eq("proj_studio",studio.getStuId()));
        map.put("studio",studio);
        map.put("user",user);
        map.put("projects",projectList);
        return map;
    }

    //插入邀请竞标表
    @RequestMapping("/insert_invite")
    @ResponseBody
    public String insert_invite(String stuId){
        Invite invite=inviteService.selectOne(new EntityWrapper<Invite>().eq("inv_project",proId).eq("inv_studio",stuId).eq("inv_prom",pormId));
        if(invite==null){
            Invite invite2=new Invite();
            invite2.setInvProject(proId);
            invite2.setInvCreattime(new Date());
            invite2.setInvProm(pormId);
            invite2.setInvStudio(stuId);
            Invitenotice notice=new Invitenotice();
            Project project=projectService.selectOne(new EntityWrapper<Project>().eq("proj_id",proId));
            notice.setInnoProid(proId);
            notice.setInnoProname(project.getProjName());
            notice.setInnoTime(new Date());
            notice.setInnoState("否");
            notice.setInnoForeid(studioService.selectOne(new EntityWrapper<Studio>().eq("stu_id",stuId)).getStuCreatid());
            if(invitenoticeService.insertAllColumn(notice)&&inviteService.insertAllColumn(invite2))
                return "1";
            else
                return "0";
        }
        return "2";
    }


}
