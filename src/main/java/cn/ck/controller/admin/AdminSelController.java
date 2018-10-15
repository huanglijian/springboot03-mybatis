package cn.ck.controller.admin;

import cn.ck.entity.*;
import cn.ck.entity.bean.AdminJob;
import cn.ck.entity.bean.AdminProj;
import cn.ck.entity.bean.Adminoriginal;
import cn.ck.service.*;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    @Autowired
    AccountService accountService;

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

    /**
     * 跳转主页界面
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "admin/admin_index";
    }

    @RequestMapping("/adminindex")
    @ResponseBody
    public ResponseBo adminindex(){
        //账户余额
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
        //当前月份
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        //本月活跃用户人数
        int promnum=promulgatorService.selectCount(new EntityWrapper<Promulgator>().addFilter("DATE_FORMAT(prom_logintime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )",""));
        int usernum=usersService.selectCount(new EntityWrapper<Users>().addFilter("DATE_FORMAT(user_logintime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )",""));
        int activeusernum=promnum+usernum;
        //本月新项目数
        int projnum=projectService.selectCount(new EntityWrapper<Project>().addFilter("DATE_FORMAT(proj_creattime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )",""));
        int preprojnum=projectService.selectCount(new EntityWrapper<Project>().addFilter("DATE_FORMAT( CURDATE( ) , '%Y%m' ) - DATE_FORMAT(proj_creattime, '%Y%m' ) =1",""));
        System.out.println(projnum+"----"+preprojnum);
        int projtip=0;
        int projpercent=0;
        if(preprojnum==0){
            projtip=1;
            projpercent=100;
        }else if(projnum<preprojnum){
            projpercent=100-(projnum/preprojnum)*100;
            projtip=0;
        }else{
            projpercent=(projnum/preprojnum)*100-100;
            projtip=1;
        }
        //本月资金
        double fundnum=0;double fundprenum=0;
        List<Funds> fundsList=fundsService.selectList(new EntityWrapper<Funds>().eq("fund_income","平台").addFilter("DATE_FORMAT(fund_datetime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )",""));
        for (Funds funds:fundsList) {
            fundnum=fundnum+funds.getFundMoney();
        }
        List<Funds> fundpreList=fundsService.selectList(new EntityWrapper<Funds>().eq("fund_income","平台").addFilter("DATE_FORMAT( CURDATE( ) , '%Y%m' ) - DATE_FORMAT(fund_datetime, '%Y%m' ) = 1",""));
        for (Funds funds:fundpreList) {
            fundprenum=fundprenum+funds.getFundMoney();
        }
        double fundtip=0;double fundpercent=0;
        if(fundprenum==0){
            fundtip=1;
            fundpercent=100;
        }else if(fundnum<fundprenum){
            fundpercent=100-(fundnum/fundprenum)*100;
            fundtip=0;
        }else{
            fundpercent=(fundnum/fundprenum)*100-100;
            fundtip=1;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        fundnum=Double.valueOf(df.format(fundnum));
        fundpercent=Double.valueOf(df.format(fundpercent));

        return ResponseBo.ok().put("account",account.getAccMoney()).put("month",month).put("usernum",activeusernum)
                .put("projnum",projnum).put("projtip",projtip).put("projpercent",projpercent)
                .put("fundnum",fundnum).put("fundtip",fundtip).put("fundpercent",fundpercent);
    }


    @RequestMapping("/projchart")
    @ResponseBody
    public ResponseBo projchart(){


        return ResponseBo.ok();
    }

}
