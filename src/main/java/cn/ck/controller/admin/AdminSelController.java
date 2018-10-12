package cn.ck.controller.admin;

import cn.ck.entity.*;
import cn.ck.entity.bean.AdminJob;
import cn.ck.entity.bean.AdminProj;
import cn.ck.entity.bean.Adminoriginal;
import cn.ck.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminSelController {
    @Autowired
    ProjectService projectService;
    @Autowired
    PromulgatorService promulgatorService;
    @Autowired
    StudioService studioService;
    @Autowired
    BiddingService biddingService;
    @Autowired
    FundsService fundsService;
    @Autowired
    AlluserService alluserService;
    @Autowired
    UsersService usersService;
    @Autowired
    OriginalService originalService;
    @Autowired
    CollectoriService collectoriService;
    @Autowired
    CollectpjService collectpjService;
    @Autowired
    JobsService jobsService;
    @Autowired
    JobuserService jobuserService;

    /**
     * 跳转项目管理
     * @return
     */
    @RequestMapping("/proj")
    public String proj(){
        return "admin/admin_project";
    }

    @RequestMapping("/adminproj")
    @ResponseBody
    public List<AdminProj> adminproj(){
        List<AdminProj> adminProjList=new ArrayList<>();
        List<Project> projectList=projectService.selectList(new EntityWrapper<>());
        for (Project project:projectList) {
            AdminProj adminProj=new AdminProj();
            adminProj.setProjId(project.getProjId());
            adminProj.setProjName(project.getProjName());
            adminProj.setProjSecret(project.getProjSecret());
            adminProj.setProjType(project.getProjType());
            adminProj.setProjMoney(project.getProjMoney());
            adminProj.setProjCreattime(project.getProjCreattime());
            adminProj.setProjStarttime(project.getProjStarttime());
            adminProj.setProjEndtime(project.getProjEndtime());
            adminProj.setProjCycletime(project.getProjCycletime());
            adminProj.setProjIntro(project.getProjIntro());
            adminProj.setProjState(project.getProjState());
            adminProj.setProjProm(project.getProjProm());
            Promulgator promulgator=promulgatorService.selectID(project.getProjProm());
            adminProj.setProPromName(promulgator.getPromName());
            adminProj.setProjStudio(project.getProjStudio());
            if(project.getProjStudio()!=null){
                Studio studio=studioService.selectById(project.getProjStudio());
                adminProj.setProStuName(studio.getStuName());
            }else{
                adminProj.setProStuName("");
            }
            int count=biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",project.getProjId()));
            adminProj.setBidnum(count);
            int projcount=collectpjService.selectCount(new EntityWrapper<Collectpj>().eq("colp_pjid",project.getProjId()));
            adminProj.setProjnum(projcount);
            adminProjList.add(adminProj);
        }
        return adminProjList;
    }

    /**
     * 跳转资金管理
     * @return
     */
    @RequestMapping("/funds")
    public String funds(){
        return "admin/admin_funds";
    }

    @RequestMapping("/adminfund")
    @ResponseBody
    public List<Funds> adminfund(){
        List<Funds> fundsList=fundsService.selectList(new EntityWrapper<Funds>().eq("fund_income","平台"));
        for (Funds funds:fundsList) {
            String id=funds.getFundOutlay();
            Alluser alluser=alluserService.selectById(id);
            if(alluser.getAllType().equals("发布者")){
                Promulgator promulgator=promulgatorService.selectById(id);
                funds.setFundRemark(promulgator.getPromName());
            } else if(alluser.getAllType().equals("普通用户")){
                Users users=usersService.selectById(id);
                funds.setFundRemark(users.getUserName());
            } else{
                funds.setFundRemark(funds.getFundOutlay());
            }
        }
        return fundsList;
    }

    /**
     * 跳转原创中心界面
     * @return
     */
    @RequestMapping("/origin")
    public String origin(){
        return "admin/admin_origin";
    }

    @RequestMapping("/adminorigin")
    @ResponseBody
    public List<Adminoriginal> adminorigin(){
        List<Adminoriginal> adminoriginalList=new ArrayList<>();
        List<Original> originalList=originalService.selectList(new EntityWrapper<>());
        for (Original original:originalList) {
            Adminoriginal adminoriginal=new Adminoriginal();
            adminoriginal.setOrigId(original.getOrigId());
            adminoriginal.setOrigGrade(original.getOrigGrade());
            adminoriginal.setOrigName(original.getOrigName());
            adminoriginal.setOrigIntro(original.getOrigIntro());
            adminoriginal.setOrigTag(original.getOrigTag());
            adminoriginal.setOrigType(original.getOrigType());
            adminoriginal.setOrigUploadtime(original.getOrigUploadtime());

            Users users=usersService.selectById(original.getOrigUsers());
            adminoriginal.setUserName(users.getUserName());

            int count=collectoriService.selectCount(new EntityWrapper<Collectori>().eq("colo_ogi",original.getOrigId()));
            adminoriginal.setCount(count);
            adminoriginalList.add(adminoriginal);
        }
        return adminoriginalList;
    }

    /**
     * 跳转职位界面
     * @return
     */
    @RequestMapping("/jobs")
    public String jobs(){
        return "admin/admin_jobs";
    }

    @RequestMapping("/adminjob")
    @ResponseBody
    public List<AdminJob> adminjob(){
        List<AdminJob> adminJobList=new ArrayList<>();
        List<Jobs> jobsList=jobsService.selectList(new EntityWrapper<>());
        for (Jobs jobs:jobsList) {
            AdminJob adminJob=new AdminJob();
            adminJob.setJobId(jobs.getJobId());
            adminJob.setJobName(jobs.getJobName());
            adminJob.setJobCreattime(jobs.getJobCreattime());
            adminJob.setJobIntro(jobs.getJobIntro());
            adminJob.setJobMoney(jobs.getJobMoney());
            adminJob.setJobRequire(jobs.getJobRequire());
            adminJob.setJobState(jobs.getJobState());
            adminJob.setJobType(jobs.getJobType());
            adminJob.setJobNum(jobs.getJobNum());
            Studio studio=studioService.selectById(jobs.getJobStudio());
            adminJob.setJobStudio(studio.getStuName());
            int count=jobuserService.selectCount(new EntityWrapper<Jobuser>().eq("ju_jobs",jobs.getJobId()));
            adminJob.setUsernum(count);
            adminJobList.add(adminJob);
        }
        return  adminJobList;
    }
}
