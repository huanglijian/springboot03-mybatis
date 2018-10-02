package cn.ck.controller.promcenter;

import cn.ck.controller.FileController;
import cn.ck.entity.*;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.service.BiddingService;
import cn.ck.service.NoticeService;
import cn.ck.service.StudioService;
import cn.ck.service.ProjectService;
import cn.ck.utils.DateUtils;
import cn.ck.utils.NoticeInsert;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/promcenter")
public class PromProjController {
    @Autowired
    ProjectService projectService;
    @Autowired
    BiddingService biddingService;
    @Autowired
    StudioService studioService;
    @Autowired
    NoticeService noticeService;

    /**
     * 发布项目controller
     * @param request
     * @param img 图片
     * @param file 上传文件
     * @param project
     * @return
     */
    @PostMapping("/projectcreat")
    @ResponseBody
    public ResponseBo projectcreat(HttpServletRequest request, @RequestParam("img") MultipartFile img, @RequestParam("file") MultipartFile file, Project project){

        if(!file.isEmpty()){
            String filepath="E:/ChuangKeFile/Project/file";
            List<String> filelist= FileController.fileupload(file,filepath);
            project.setProjFile(filelist.get(0));
            project.setProjFilename(filelist.get(1));
        }

        String imgpath = "E:/ChuangKeFile/Project/img";
        List<String> imglist=FileController.fileupload(img,imgpath);
        project.setProjImg(imglist.get(0));

        Date date = new Date();//获得系统时间
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp projcreattime = Timestamp.valueOf(nowTime);//把时间转换
        project.setProjCreattime(projcreattime);

        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        project.setProjProm(user.getAllId());

        project.setProjTag(request.getParameter("label"));
        project.setProjState("竞标中");

//        System.out.println(project);

        if(projectService.insertAllColumn(project)){
            String stumessage="您好，您所发布的项目"+project.getProjName()+"已在"+nowTime+"发布成功,如需查看详细信息，请查看项目管理相关功能";
            Notice notice= NoticeInsert.insertNotice(stumessage,user.getAllId());
            noticeService.insertAllColumn(notice);
            return ResponseBo.ok().put("code","1");
        }else{
            return ResponseBo.ok().put("code","0");
        }

    }

    /**
     * 未完成竞标的项目列表
     * @return
     */
    @GetMapping("/projectbidding")
    @ResponseBody
    public List projectbidding(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        //匹配当前时间，更改目前项目竞标状态
        List<Project> proBidding=projectService.projBidTimefalse(user.getAllId());
        for (Project project1:proBidding) {
            project1.setProjState("竞标超时");
        }
        if(!proBidding.isEmpty()){
            projectService.updateAllColumnBatchById(proBidding);
        }

        //查询更新后竞标中的项目
        Set<String> set = new HashSet<>();
        set.add("proj_creattime");
        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标中").eq("proj_prom",user.getAllId()).orderDesc(set));
        List<ProjectBid> bidList1=new ArrayList<ProjectBid>();

        for (Project project1:projectList1) {
            ProjectBid projectBid = new ProjectBid();
            projectBid.setProject(project1);
            //转换时间格式
            String creatdate=DateUtils.format(project1.getProjCreattime(),DateUtils.DATE_PATTERN);
            projectBid.setCreatdate(creatdate);
            //竞标剩余时间
            projectBid.setBidday(10-projectService.projBidTimeNum(project1.getProjId()));
            //竞标个数
            int count=biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",project1.getProjId()));
            projectBid.setBidnum(count);
            bidList1.add(projectBid);
        }

//        for (ProjectBid projectBid:bidList1) {
//            System.out.println(projectBid);
//        }
        return bidList1;
    }

    /**
     * 完成竞标的项目列表
     * @return
     */
    @GetMapping("/projectbidfinish")
    @ResponseBody
    public List projectbidfinish(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        //匹配当前时间，更改目前项目竞标状态
        List<Project> proBidding=projectService.projBidTimefalse(user.getAllId());
        for (Project project1:proBidding) {
            project1.setProjState("竞标超时");
        }
        if(!proBidding.isEmpty()){
            projectService.updateAllColumnBatchById(proBidding);
        }

        //查询更新后竞标中的项目
        Set<String> set = new HashSet<>();
        set.add("proj_creattime");
        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标中止").or("proj_state='竞标超时'").eq("proj_prom",user.getAllId()).orderDesc(set));
        List<ProjectBid> bidList1=new ArrayList<ProjectBid>();

        for (Project project1:projectList1) {
            ProjectBid projectBid = new ProjectBid();
            projectBid.setProject(project1);
            //转换时间格式
            String creatdate=DateUtils.format(project1.getProjCreattime(),DateUtils.DATE_PATTERN);
            projectBid.setCreatdate(creatdate);
            //竞标人数
            int count=biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",project1.getProjId()));
            projectBid.setBidnum(count);
            bidList1.add(projectBid);
        }

//        for (ProjectBid projectBid:bidList1) {
//            System.out.println(projectBid);
//        }
        return bidList1;
    }

    /**
     * 竞标项目及竞标详细信息
     * @param id 项目id
     * @return
     */
    @PostMapping("/projbiddetail/{id}")
    @ResponseBody
    public ResponseBo projbiddetail(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
        //竞标剩余时间
        int day=10-projectService.projBidTimeNum(project.getProjId());
        //竞标个数
        int count=biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",project.getProjId()));
        //竞标成功时承接工作室
        String studioname="工作室";
        if(project.getProjStudio()!=null){
            Studio stu=new Studio();
            stu=studioService.selectOne(new EntityWrapper<Studio>().eq("stu_id",project.getProjStudio()));
            studioname=stu.getStuName();
        }

        List<Bidding> biddingList=biddingService.selectList(new EntityWrapper<Bidding>().eq("bid_proj",id));
        List<ProjectBid> projectBidList=new ArrayList<>();

        for (Bidding bidding:biddingList) {
            ProjectBid projectBid=new ProjectBid();
            projectBid.setBidding(bidding);
            Studio studio=new Studio();
            studio=studioService.selectById(bidding.getBidStudio());
            projectBid.setStudio(studio);
            projectBidList.add(projectBid);
        }
        for (ProjectBid projectBid:projectBidList) {
            System.out.println(projectBid);
        }
        return ResponseBo.ok().put("project",project).put("day",day).put("count",count).put("studioname",studioname).put("probid",projectBidList);
    }

    /**
     * 中止项目竞标，将竞标状态改为“竞标中止”
     * @param id 项目id
     * @return
     */
    @RequestMapping("/projdiscontinueBid/{id}")
    @ResponseBody
    public ResponseBo projdiscontinueBid(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
        String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if(project.getProjState().equals("竞标中")){
            project.setProjState("竞标中止");
            projectService.updateById(project);
            //更新成功则通知发布者
            String projmessage="您好，您所发布的项目"+project.getProjName()+"已在"+nowdate+"中止竞标,如需查看详细信息，请查看竞标项目相关功能";
            Notice projnotice= NoticeInsert.insertNotice(projmessage,project.getProjProm());
            noticeService.insertAllColumn(projnotice);
            //将与之相连竞标表中的状态改为“竞标中止”，并向工作室发送系统通知
            int count=biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",project.getProjId()));
            List<Bidding> biddingList=new ArrayList<>();
            if(count!=0){
                biddingList=biddingService.selectList(new EntityWrapper<Bidding>().eq("bid_proj",project.getProjId()));
                for (Bidding bidding:biddingList) {
                    bidding.setBidState("竞标中止");
                    bidding.setBidEndtime(new Date());
                    String stumessage="您好，您所竞标的项目"+project.getProjName()+"已在"+nowdate+"中止竞标,如需查看详细信息，请查看竞标项目相关功能";
                    Notice stunotice= NoticeInsert.insertNotice(stumessage,bidding.getBidStudio());
                    noticeService.insertAllColumn(stunotice);
                }
                biddingService.updateAllColumnBatchById(biddingList);
            }
            return ResponseBo.ok().put("code","1");
        }else{
            return ResponseBo.ok().put("code","0");
        }
//        model.addAttribute("id",id);
//        return "/promulgator/prom_projBidFinDetail";
    }

    /**
     * 未完成项目列表
     * @return
     */
    @GetMapping("/projmangering")
    @ResponseBody
    public ResponseBo projmangering(){
        Set<String> set = new HashSet<>();
        set.add("proj_starttime");
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","开发中").or("proj_state='发布者中止'").or("proj_state='承接方中止'").eq("proj_prom",user.getAllId()).orderDesc(set));
        return ResponseBo.ok().put("project",projectList1);
    }

    /**
     * 已完成项目列表
     * @return
     */
    @GetMapping("/projmangerfinsh")
    @ResponseBody
    public ResponseBo projmangerfinsh(){
        Set<String> set = new HashSet<>();
        set.add("proj_endtime");
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","项目中止").or("proj_state='开发完成'").eq("proj_prom",user.getAllId()).orderDesc(set));
        return ResponseBo.ok().put("project",projectList1);
    }
}
