package cn.ck.controller.promcenter;

import cn.ck.entity.Bidding;
import cn.ck.entity.Studio;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.service.BiddingService;
import cn.ck.service.StudioService;
import cn.ck.utils.FileController;
import cn.ck.entity.Alluser;
import cn.ck.entity.Project;
import cn.ck.service.ProjectService;
import cn.ck.utils.DateUtils;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/promcenter")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    BiddingService biddingService;
    @Autowired
    StudioService studioService;

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
            String filepath="E:/ck/project/file";
            String fileurl=FileController.fileupload(file,filepath);
            project.setProjFile(fileurl);
        }

        String imgpath = "E:/ck/project/img";
        String imgurl=FileController.fileupload(img,imgpath);
        project.setProjImg(imgurl);
//        System.out.println(imgurl);

        Date date = new Date();//获得系统时间
        String nowTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        Timestamp projcreattime = Timestamp.valueOf(nowTime);//把时间转换
        project.setProjCreattime(projcreattime);

        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        project.setProjProm(user.getAllId());

        project.setProjTag(request.getParameter("label"));
        project.setProjState("竞标中");

//        System.out.println(project);

        if(projectService.insertAllColumn(project)){
            return ResponseBo.ok("发布成功");
        }else{
            return ResponseBo.error("发布失败");
        }

    }

    /**
     * 未完成竞标的项目列表
     * @return
     */
    @GetMapping("/projectbidding")
    @ResponseBody
    public List projectbid(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        //匹配当前时间，更改目前项目竞标状态
        List<Project> proBidding=projectService.projBidTimefalse(user.getAllId());
        for (Project project1:proBidding) {
            project1.setProjState("竞标中止");
        }
        if(!proBidding.isEmpty()){
            projectService.updateAllColumnBatchById(proBidding);
        }

        //查询更新后竞标中的项目
        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标中").eq("proj_prom",user.getAllId()));
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
    public List projectbidunfinish(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        //匹配当前时间，更改目前项目竞标状态
        List<Project> proBidding=projectService.projBidTimefalse(user.getAllId());
        for (Project project1:proBidding) {
            project1.setProjState("竞标中止");
        }
        if(!proBidding.isEmpty()){
            projectService.updateAllColumnBatchById(proBidding);
        }

        //查询更新后竞标中的项目
        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标中止").eq("proj_prom",user.getAllId()));
        List<Project> projectList2=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标结束").eq("proj_prom",user.getAllId()));
        projectList1.addAll(projectList2);
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
     * 竞标中项目及竞标详细信息
     * @param id 项目id
     * @return
     */
    @PostMapping("/projbiddetail/{id}")
    @ResponseBody
    public ResponseBo probidunfinish(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
        //竞标剩余时间
        int day=10-projectService.projBidTimeNum(project.getProjId());
        //竞标个数
        int count=biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",project.getProjId()));
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
        return ResponseBo.ok().put("project",project).put("day",day).put("count",count).put("probid",projectBidList);
    }


}
