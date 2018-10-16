package cn.ck.controller;

import cn.ck.entity.*;

import java.io.*;
import java.util.*;

import cn.ck.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    CollectpjService collectpjService;

    String bid = null;
    String projectid = null; //记录项目详情页的项目id

    String stuidid = null;

    //这里给开始时间设置一个无限小的值，给结束时间设置一个无限大的值
    String strattime = "2000-0-0"; //搜寻条件之---开始时间
    String endtime   = "2099-12-31";   //搜寻条件之---结束时间

    int start=1;//记录当前页 默认为“1“
    int size = 5; //记录每页条目数，默认为”5“
//    String conditions = "0"; //条件
    String values     = "0";  //project值

    Boolean orderByMoney = false;
    Boolean orderByDate  = false;

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
    String tags="0"; //记录状态



    //当点击上方的选择框时 跳转到下方的mapping

    //判断是否登录，用户的ID是多少
    @RequestMapping("/islogin")
    public String islogin() {

        if(true)
            return "project/Project-Shouye";
        else
            return "login";

    }
    //将条件清零
    @RequestMapping("/pjClear")
    public String pjclear(){
        this.values="0";
        this.start = 1;
        this.orderByMoney=false;
        this.orderByDate =false;
        this.classify="0";
        this.state = "0";
        this.strattime="2000-1-1";
        this.endtime  ="2099-12-31";
        this.tags  ="0";
        return "project/Project-Shouye";
    }
    //接收一系列的参数



    @RequestMapping("/pjconditione")
    public String pjpageset(
            @RequestParam(value = "state", defaultValue = "1")  String state,
            @RequestParam(value = "classify", defaultValue = "0") String classify,
            @RequestParam(value = "values", defaultValue = "0") String values,
            @RequestParam(value = "strattime", defaultValue = "2000-0-0") String strattime,
            @RequestParam(value = "endtime", defaultValue = "2099-12-31") String endtime,
            @RequestParam(value = "tags", defaultValue = "0") String tags){

        this.state = state;
        this.classify = classify;
        this.strattime = strattime;
        this.endtime = endtime;
        this.values = values;
        this.tags = tags;
        return "project/Project-Shouye";
    }

    @RequestMapping("/toadduser")
    @ResponseBody
    public void toadduser(HttpServletRequest request){
       String bid = request.getParameter("bid");
        String islogin = request.getParameter("islogin");
       if(islogin.equals("true")) {
           if (bid.equals("undefined"))
               this.bid = null;
           else
               this.bid = bid;

       }
        System.out.println(islogin);

    }

    //按预算 排序
    @RequestMapping("/orderbymoney")
    public String orderbymoney(){
        //保证同时只有一个为真
        orderByMoney = true;
        orderByDate = false;
        this.start = 1;
        return "project/Project-Shouye";
    }

    //按时间排序
    @RequestMapping("/orderbydate")
    public String orderbydate(){
        orderByDate = true;
        orderByMoney = false;
        this.start = 1;
        return "project/Project-Shouye";
    }

    //value 即是项目的名字
    @RequestMapping("/pjpage")
    public String pjpageset(
            @RequestParam(value = "start", defaultValue = "1")  int start,
            @RequestParam(value = "values", defaultValue = "0") String values){
        this.start=start;
        this.values = values;
        return "project/Project-Shouye";
    }


    @RequestMapping("/projectislike")
    @ResponseBody
    public Map<String,Object> projectislike() {
        Map<String, Object> map = new HashMap<>();
        String islikes[] = new String[16];
        for(int i = 0 ; i<16;i++)
            islikes[i] = "true";
        map.put("islikes",islikes);
        return map;
    }

    //获取当前项目
    @RequestMapping("/projectpage")
    @ResponseBody
    public PageInfo<Project> get_projects(){

        System.out.println("state:"+this.state);
         System.out.println("classify:"+this.classify);
        System.out.println("start:"+ this.start);
        System.out.println("values:"+ this.values);
        System.out.println("this.strattime:"+ this.strattime);
        System.out.println("this.endtime:"+ this.endtime);
        System.out.println("this.bid:"+ this.bid);
        EntityWrapper<Project>  wrappers = new EntityWrapper<Project>();
        if(!state.equals("0"))     //状态
            wrappers.eq("proj_state",state);
        if(!classify.equals("0"))  //分类
            wrappers.eq("proj_type",classify);
        wrappers.eq("proj_secret","公开"); //级别
        if(!this.values.equals("0"))
            wrappers.like("proj_name",values);
        if(orderByMoney) {
            wrappers.orderBy("proj_money", true);
        }
        if(orderByDate) {
            wrappers.orderBy("proj_creattime", false);
        }
         wrappers.between("proj_creattime",this.strattime,this.endtime);
        if(!this.tags.equals("0")){
            String t[] = tags.split(",");
            System.out.println(t.length);
            for(int index = 0; index<t.length;index++) {
                wrappers.like("proj_tag", t[index]);
                System.out.println(t[index]);
            }
        }

        PageHelper.startPage(this.start,this.size);
        List<Project> List= projectService.selectList(wrappers);

        //未登录
//        bid="e9ae842a-e70f-49b8-a9b5-bee24a13c8bb";

//            for(Project p : List){
//                //那些加 收藏
//                p.setProjFile("true");
//            }

   if(this.bid!=null) {
       for (Project p : List) {
           //那些加 收藏
           EntityWrapper<Collectpj> wrappers1 = new EntityWrapper<Collectpj>();
//            System.out.println("in---addShuxXing");
           wrappers1.eq("colp_pjid", p.getProjId());
           wrappers1.eq("colp_user", this.bid);
           Collectpj collectpj = collectpjService.selectOne(wrappers1);
           if (collectpj == null) {
//               System.out.println("no-result");
               p.setProjFile(null);

           } else {
//               System.out.println("has-result");
               p.setProjFile("true");

           }
//           System.out.println(p.getProjId());
       }
   }
   else{
       for (Project p : List) {
           p.setProjFile(null);
       }
   }

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
    @RequestMapping("/projectHot")
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

        EntityWrapper<Project>  wrappers1 = new EntityWrapper<Project>();
//方法一
//        for(Project p: projects){
//            Date nowtime = new Date();
//            Date creattime =p.getProjCreattime();
//            creattime.setDate(creattime.getDay()+10);
//
////            if(nowtime.after(creattime)) {
////                System.out.println(nowtime+"-----"+creattime);
////                System.out.println("超标了" + p.getProjId());
////                if(p.getProjState().equals("竞标中"))
////                    p.setProjState("竞标超时");
////                //修改状态
////            }
//        }
        //方法二
                for(Project p: projects){
            Date nowtime = new Date();
            Date creattime =p.getProjCreattime();

             nowtime.setDate(nowtime.getDay()-10);

            if(nowtime.after(creattime)) {
                System.out.println(nowtime+"-----"+creattime);
                System.out.println("超标了" + p.getProjId());
                if(p.getProjState().equals("竞标中"))
                    p.setProjState("竞标超时");
                //修改状态
            }
        }


        projectService.updateAllColumnBatchById(projects);

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
    //这是一个下载的函数
//    @RequestMapping(value = "/down",method = RequestMethod.GET)
//    public void download(HttpServletRequest request,HttpServletResponse response,String fileName) {
//        try {
//            //存放地址
//            String realPath = "F:\\down";
//            //获得服务器端某个文件的完整路径
//            String fullPath = realPath + File.separator + fileName;
//            //设置响应
//            response.setContentType("application/force-download");
//           //设置响应头信息
//            response.setHeader("Content-Disposition", "attachment;fileName="+fileName);// 设置文件名
//            //文件名有中文时设置编码
//            response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("GBK"),"ISO-8859-1"));
//
//
//           File downloadFile = new File(fullPath);
//           FileInputStream inputStream = new FileInputStream(downloadFile);
//           OutputStream outputStream =  response.getOutputStream();
//           IOUtils.copy(inputStream, outputStream);
//            response.flushBuffer();
//            outputStream.flush();
//            outputStream.close();
//            inputStream.close();
//           } catch (IOException e) {
//           e.printStackTrace();
//            }
//    }

//    public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
//        // 下载本地文件
//        String fileName = "Operator.doc".toString(); // 文件的默认保存名
//        // 读到流中
//        InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
//        // 设置输出的格式
//        response.reset();
//        response.setContentType("bin");
//        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//        // 循环取出流中的数据
//        byte[] b = new byte[100];
//        int len;
//        try {
//            while ((len = inStream.read(b)) > 0)
//                response.getOutputStream().write(b, 0, len);
//            inStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void downloadNet(HttpServletResponse response) throws MalformedURLException {
//        // 下载网络文件
//        int bytesum = 0;
//        int byteread = 0;
//
//        URL url = new URL("windine.blogdriver.com/logo.gif");
//
//        try {
//            URLConnection conn = url.openConnection();
//            InputStream inStream = conn.getInputStream();
//            FileOutputStream fs = new FileOutputStream("c:/abc.gif");
//
//            byte[] buffer = new byte[1204];
//            int length;
//            while ((byteread = inStream.read(buffer)) != -1) {
//                bytesum += byteread;
//                System.out.println(bytesum);
//                fs.write(buffer, 0, byteread);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //这里是判断是否已经参与竞标的函数
    //同理他已经是组长了
    @RequestMapping("/isalreadybid")
    @ResponseBody
    public Boolean isalready(@RequestParam(value = "pidId", defaultValue = "")  String pidId
    ) {
        Boolean isalreadybid = null;
     //是否存在竞标记录
      EntityWrapper<Bidding>  wrappers = new EntityWrapper<Bidding>();
      wrappers.eq("bid_studio",this.stuidid);
      wrappers.eq("bid_proj",pidId);
      Bidding bid =  biddingService.selectOne(wrappers);
      //存在
      if(bid!=null){
          isalreadybid = true;
      }
        System.out.println("in---isalready and result is"+isalreadybid);
        return isalreadybid;
    }


    @RequestMapping("/jingbiao")
    public  String jingbiao(HttpServletRequest request) {
        //此时 已经确定是组长了
        //不是组长的话必然存在错误
        String  bidId = request.getParameter("bid1");
        int pid = 0;
        String  pidId = request.getParameter("pid1");
        System.out.println("in---jingbiao");
        System.out.println("userid:"+bidId);
        System.out.println("pid:"+pidId);
        try {
           pid = Integer.parseInt(pidId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("NumberFormatException");
        }
        EntityWrapper<Studio>  wrappers = new EntityWrapper<Studio>();
        wrappers.eq("stu_creatid",bidId);
        Studio  studio  =  studioService.selectOne(wrappers);

        if(studio==null) {
            System.out.println("不是工作室的Boss");
            return "project/Project-Detail";
            //用户名为空
        }
        else {


            System.out.println(studio.getStuName());
            System.out.println(studio.getStuId());
            Date now = new Date();
            String BidStudio = request.getParameter("BidStudio");
            String BidEmail = request.getParameter("BidEmail");
            String BidMoney = request.getParameter("BidMoney");
            String BidPhone = request.getParameter("BidPhone");
            String BidScheme = request.getParameter("BidScheme");
            String BidCycle = request.getParameter("BidCycle");

            //有两个属性不能为空

            Bidding bidding = new Bidding();
            bidding.setBidTime(now); //设置时间
            bidding.setBidProj(pid);
            bidding.setBidStudio(studio.getStuId());
            bidding.setBidMoney(BidMoney);
            bidding.setBidEmail(BidEmail);
            bidding.setBidCycle(BidCycle);
            bidding.setBidPhone(BidPhone);
            bidding.setBidState("竞标中");
            bidding.setBidScheme(BidScheme);

            boolean ok = biddingService.insert(bidding);
            System.out.println(pid);
           System.out.println("---" + ok);
            System.out.println(bidId);

        }
        return "project/Project-Detail";
    }
    //是不是组长
    @RequestMapping("/isleader")
    @ResponseBody
    public Map<String,String> isleader(@RequestParam(value = "bidId", defaultValue = "")  String bidId) {
        //获取用户id
        Map<String,String> map = new HashMap<>();

        String isleader = "false";
        System.out.println("-----" + bidId + "---------");

        if (bidId.equals("undefined")) {
            System.out.println("in---login");
            isleader ="bad";
        }
        else {
            EntityWrapper<Studio> wrappers = new EntityWrapper<Studio>();
            wrappers.eq("stu_creatid", bidId);
            Studio studio = studioService.selectOne(wrappers);

            if (studio == null) {
                System.out.println("不是工作室的Boss");

            } else {
                //知道了是组长后，将工作室的id赋值给全局变量
                this.stuidid = studio.getStuId();
                isleader = "true";
            }


        }
        map.put("keys",isleader);
        System.out.println("readytojump");
        return map;
    }

    //是否收藏
    @RequestMapping("/islike")
    @ResponseBody
    public Map<String,Object> islike(@RequestParam(value = "bidId", defaultValue = "")  String bidId,
                                        @RequestParam(value = "pidId", defaultValue = "")  String pidId
    ) {
        EntityWrapper<Collectpj>  wrappers = new EntityWrapper<Collectpj>();
        wrappers.eq("colp_pjid",pidId);
        wrappers.eq("colp_user",bidId);
        Collectpj  collectpj  =  collectpjService.selectOne(wrappers);
        Map<String,Object> map = new HashMap<>();
        System.out.println("in---islike");
        if(collectpj==null){
        map.put("k1","false");
            System.out.println("false");
        }
        else {
            map.put("k1", "true");
            System.out.println(collectpj.getColpId());
            System.out.println("true");
        }
        return map;
    }

    //收藏
    @RequestMapping("/addtolike")
    @ResponseBody
    public void addtolike(@RequestParam(value = "bidId", defaultValue = "")  String bidId,
                           @RequestParam(value = "pidId", defaultValue = "")  String pidId){
        //获取用户id  ----bidId
        //获取项目id  ----pid
        //获取当前时间
        System.out.println("in---addtolike");
        int pid = 0;
        try {
            pid = Integer.parseInt(pidId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("NumberFormatException");
        }
        Date now = new Date();
        //生成实体类1
        Collectpj collectpj = new Collectpj();
        collectpj.setColpPjid(pid);
        collectpj.setColpTime(now);
        collectpj.setColpUser(bidId);
        System.out.println(pid+bidId);
        Boolean ok =  collectpjService.insert(collectpj);
        System.out.println(ok);

    }
    @RequestMapping("/tocancellike")
    @ResponseBody
    public void tocancallike(@RequestParam(value = "bidId", defaultValue = "")  String bidId,
                              @RequestParam(value = "pidId", defaultValue = "")  String pidId) {

        System.out.println("in---tocancallike");
        //删除记录
        EntityWrapper<Collectpj>  wrappers = new EntityWrapper<Collectpj>();
        wrappers.eq("colp_pjid",pidId);
        wrappers.eq("colp_user",bidId);
        boolean ok =  collectpjService.delete(wrappers);
        System.out.println(ok);
    }



}