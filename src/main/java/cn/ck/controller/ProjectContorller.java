package cn.ck.controller;

import cn.ck.entity.Bidding;

import java.util.*;

import cn.ck.entity.Project;
import cn.ck.entity.Promulgator;
import cn.ck.entity.Studio;
import cn.ck.service.BiddingService;
import cn.ck.service.ProjectService;
import cn.ck.service.PromulgatorService;
import cn.ck.service.StudioService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;


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

    String projectid = null; //记录项目详情页的项目id

    int start=1;//记录当前页 默认为“0“
    int size = 1; //记录每页条目数，默认为”10“
    String conditions = "0"; //条件
    String values     = "0";  //值

    //关于搜索 参考哔哩哔哩
    //拥有1.keyword  （按字段进行搜索）它的值是按我们---
    //2.将其余所有的条件统统摆出，
    //此时，若将（输入的值当条件，有些不妥） ，用 0 - 1 - 2 - 3 这些进行替代
    //最后用获取url? 后的函数获取条件 再加上 page
    //可以推出 我们的url应为
    //?关键字=123&属性1=dm&属性2=0&属性3=0&page=2
    //根据数据库  可以得到几个条件
    // 1 状态 State
    // State = 0 所有项目  State = 1 代表竞标中 2 代表 竞标超时
    // 3代表 开发中 4代表完工 5代表 竞标失败
    // 2. 分类 classify
    // classifiy 0 代表所有
    //3 . 项目预算 money
    // money 0 代表所有
    String state="0"; //记录状态
    String classify="0"; //记录状态
    String money="0"; //记录状态


    //当点击上方的选择框时 跳转到下方的mapping

    @RequestMapping("/pjconditione")
    public String pjpageset(
            @RequestParam(value = "state", defaultValue = "1")  String state,
            @RequestParam(value = "classify", defaultValue = "0") String classify,
            @RequestParam(value = "money", defaultValue = "0") String money){

        this.state = state;
        this.classify = classify;
        this.money = money;
        if(conditions.equals("0"));
        else
            this.conditions = conditions;

        if(values.equals("0"));
        else
            this.values = values;

        return "project/Project-Shouye";
    }





    @RequestMapping("/pjpage")
    public String pjpageset(
            @RequestParam(value = "start", defaultValue = "1")  int start,
            @RequestParam(value = "conditions", defaultValue = "0") String conditions,
            @RequestParam(value = "values", defaultValue = "0") String values){

        this.start=start;
        if(conditions.equals("0"));
        else
        this.conditions = conditions;

        if(values.equals("0"));
        else
        this.values = values;

        return "project/Project-Shouye";
    }
    //获取当前项目
    @RequestMapping("/projectpage")
    @ResponseBody
    public PageInfo<Project> get_projects(){
        PageHelper.startPage(this.start,this.size);
//        Map<String,Object> map=new HashMap<>();
//        map.put("id",this.size);
//        map.put("state",this.state);
        EntityWrapper<Project>  wrappers = new EntityWrapper<Project>();
//        conditions = "conditions"; values = "公开";
//       if(this.conditions!=null){}
//
        System.out.println("start:"+ this.start);
        System.out.println("size:"+ this.size);
        System.out.println("conditions:"+ this.conditions);
        System.out.println("values:"+ this.values);
           if(!this.conditions.equals("0"))
              wrappers.eq(conditions,values);

        List<Project> List= projectService.selectList(wrappers);

        PageInfo<Project> page = new PageInfo<>(List);
        return page;
    }


    @RequestMapping("/xwh")
    public String xia1(){
        //shfit+F6 == 重命名
       //查询所有项目的记录
//        List<Project> projects = projectService.selectList(new EntityWrapper<>());
//        for(Project attribute : projects) {
//          //  System.out.println(attribute.toString());
//        }

        return "project/Project-Shouye";
    }
    @RequestMapping("/projectShouYe")
    @ResponseBody
    public  List<Project> GetMeg3() {
//        查询所有项目的记录
        List<Project> projects = projectService.selectList(new EntityWrapper<>());


        for(Project attribute : projects) {
          //  System.out.println(attribute.toString());
        }
        System.out.println("in---project/projectShouYe");
        return projects;
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
//


    //用于获取两个推荐单之一的一个   的数据
    //order by  实现  SELECT  *  FROM `project`  DESCRIBE sex LIMIT 2;
    @RequestMapping("/projectShouYeTuijian")
    @ResponseBody
    public List<Project> GetMeg2() {

        EntityWrapper<Project> wrapper = new EntityWrapper<Project>();
        wrapper.orderBy("proj_starttime").last("limit 5");
        List<Project> projects = projectService.selectList(wrapper);
//        for(Project p: projects){
//            System.out.println(p.toString());
//        }

        return projects;
    }
    //用于获取两个推荐单之竞标最激烈的项目
    //根据v-for 的。。来确认返回的类型
    @RequestMapping("/projectShouYeHtml")
    @ResponseBody
    public  List<Project> GetMeg() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Integer> jingBiaomap = new HashMap<>();
        Map<String, String>   lastresult = new HashMap<>();
        Project projecttemp = new Project();

        // 1.竞争最激烈的top10 项目---->> 竞标人数最多的项目------>>先来五个
        // 2.首先查出所有的  项目！！！
        List<Project> projects = projectService.selectList(new EntityWrapper<Project>());
        List<Project> projectNeed = new ArrayList<Project>();

        int count;
        for(Project p: projects){
       count =  biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",p.getProjId()));
        jingBiaomap.put( p.getProjId().toString(),count);

        }
        System.out.println("1---------------------++++++");
        // -- 从网上找的对map 排序的方法
        Map<String, Integer> result = jingBiaomap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        System.out.println("1---------------------++++++");
        System.out.println(jingBiaomap);
        System.out.println(result);

        int i = 0;

        String href = null;
        //两个数组进行存储
        //
        String phref[] = new String[5];
        String pname[] = new String[5];
        for(String value : result.keySet()){
            if(i<5) {
                projecttemp = projectService.selectById(value);

            //对id 附上属性 --- href
                href =  "projectDetail?id="+ value;

//                phref[i] = href;
//                pname[i] = projecttemp.getProjName();
                projectNeed.add(projecttemp);
//                System.out.println("id="+href);
//                lastresult.put(href,projecttemp.getProjName());
                i++;
            }
        }


        map.put("hrefs",phref);
        map.put("name",pname);
        //竞标个数
//        biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",));



        return  projectNeed;
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
        //
       int restday = 10-projectService.projBidTimeNum(pid);

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
//        int restday = 1;

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
        int[] restdays = new int[5];
//        int[] restdays = {1,2,3,4,5};
        int index = 0;

        //获取5条相关记录
        //对idsd的属性进行改造  增加id参数

        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标中").ne("proj_id",pid));
        for(Project project: projectList1) {
            ids[index] = "projectDetail?id="+project.getProjId().toString();
            pros[index] = project.getProjName();
            moneys[index] = project.getProjMoney();
            tags[index] = project.getProjTag();
            restdays[index] = 10-projectService.projBidTimeNum(project.getProjId());
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
        map.put("people",count);



        return map;
    }
//    @RequestMapping("/projectShouYeHtmls")
//    @ResponseBody
//    public Map<String, String> GetMegTest() {
//        Map<String, String>   result = new HashMap<>();
//        result.put("xia","1");
//        result.put("wei","1");
//
//        System.out.println("1---------------------");
//        return  result;
//    }


}