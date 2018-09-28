package cn.ck.controller;

import cn.ck.entity.Bidding;
import cn.ck.entity.Project;
import cn.ck.entity.Promulgator;
import cn.ck.entity.Studio;
import cn.ck.service.BiddingService;
import cn.ck.service.ProjectService;
import cn.ck.service.PromulgatorService;
import cn.ck.service.StudioService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/project")
public class ProjectContorller {

    @Autowired
    ProjectService projectService;
    @Autowired
    BiddingService biddingService;
    @Autowired
    PromulgatorService promulgatorservice;
    @Autowired
    StudioService studioService;

    String projectid = null;

    @RequestMapping("/xwh")
    public String xia1(){
        //shfit+F6 == 重命名
       //查询所有项目的记录
        List<Project> projects = projectService.selectList(new EntityWrapper<>());
        for(Project attribute : projects) {
            System.out.println(attribute.toString());
        }

        return "project/Project-Shouye";
    }
    @RequestMapping("/projectDetail")
    public  String gotos(HttpServletRequest request){
        String pid=request.getParameter("id");
        projectid = pid;
//        1.获取project的id
//        2.查寻改id的所有信息
//        3.将信息全部发送出去


        return "project/Project-Detail";
    }
    @RequestMapping("/projectDetailHtml")
    @ResponseBody
    public Map<String,Object> GetMessage(){
        Map<String,Object> map = new HashMap<>();
//        1.获取project的id
//        2.查寻改id的所有信息
//        3.将信息全部发送出去
//        int pid = 1;//以后获取
//   利用全局变量将参数获取--在赋值

       if(projectid==null)
            projectid="1";

        int  pid = Integer.parseInt(projectid);
        System.out.println(pid+"-----------++++++");

        Project p = projectService.selectById(pid);  //查所有信息
        // 竞标人数
        int count=biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",pid));
        //竞标成功时承接工作室的名字
        String studioname="";
        if(p.getProjStudio()!=null){
            studioname=studioService.selectOne(new EntityWrapper<Studio>().eq("stu_id",p.getProjStudio())).getStuName();
        }
        // 发布者(公司)
        Promulgator promulgator =  promulgatorservice.selectById(p.getProjProm());
        String promName = promulgator.getPromName();
        //  System.out.println(promulgator.toString());
        // 竞标剩余时间
//        int restday =10-projectService.projBidTimeNum(pid);
        //两个时间开始&&结束
        String Starttime;
        String Endtime;
        if(p.getProjStarttime()!=null)
            Starttime = p.getProjStarttime().toString().substring(0,10);
        else
            Starttime= null;
        if(p.getProjEndtime()!=null)
            Endtime = p.getProjEndtime().toString().substring(0,10);
        else
            Endtime= null;
        int restday = 1;

        Boolean progressState1 = true;
        Boolean progressState2 = false;
        Boolean progressState3 = false;
        // 获取progress2 和progress3 的状态
         if(p.getProjState().equals("开发完成")){
             progressState2 = true;
             progressState3 = true;
         }
         else if(p.getProjState().equals("发布者中止")||p.getProjState().equals("承接方中止")||p.getProjState().equals("项目中止")){
             progressState1 = false;
             progressState2 = false;
             progressState3 = false;

         }
         else if(p.getProjState().equals("开发中")||p.getProjState().equals("竞标成功")){
             progressState2 = true;
             progressState3 = false;
         }
         else if(p.getProjState().equals("竞标中")||p.getProjState().equals("竞标中止")||p.getProjState().equals("竞标结束")){
              //不做处理
         }
         map.put("progressState1",progressState1);
         map.put("progressState2",progressState2);
         map.put("progressState3",progressState3);

        String[] ids = new String[5];
        String[] pros = new String[5];
        String[] moneys = new String[5];
        String[] tags = new String[5];
//        int[] restdays = new int[5];
        int[] restdays = {1,2,3,4,5};
        int index = 0;

        //获取5条相关记录
        //对idsd的属性进行改造  增加id参数

        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标中").ne("proj_id",pid));
        for(Project project: projectList1) {
            ids[index] = "projectDetail?id="+project.getProjId().toString();
            pros[index] = project.getProjName();
            moneys[index] = project.getProjMoney();
            tags[index] = project.getProjTag();
//            restdays[index] = 10-projectService.projBidTimeNum(project.getProjId());
            index++;
            System.out.println(project.toString());
            if(index>=5)
                break;
        }




        String[] tag   = p.getProjTag().split(",");
//        String[] files = p.getProjFile().split(",");
//        map.put("file",files);
        map.put("project",p);
        //tag--当前页面的标签-已经分隔
        //tags--相关（推荐）页面的标签（原始）
        map.put("tag",tags);
        map.put("tags",tag);
        //ids--跳转的地址
        map.put("ids",ids);
        map.put("pros",pros);
        map.put("money",moneys);
        map.put("restday",restday);
        map.put("restdays",restdays);
        map.put("startday",Starttime);
        map.put("endday",Endtime);
        map.put("studioname",studioname);
        map.put("promulgator",promName);

        return map;
    }


}