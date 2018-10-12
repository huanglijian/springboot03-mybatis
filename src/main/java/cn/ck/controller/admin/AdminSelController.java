package cn.ck.controller.admin;

import cn.ck.entity.*;
import cn.ck.entity.bean.AdminProj;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.service.*;
import cn.ck.utils.EnityUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.awt.color.ProfileDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
