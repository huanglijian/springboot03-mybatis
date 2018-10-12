package cn.ck.controller.studio;

import cn.ck.controller.jobs.getAlluser;
import cn.ck.entity.*;
import cn.ck.service.*;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.catalina.User;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author 黄栎健
 */

@Controller
@RequestMapping("/jobdeal")
public class jobSendController {

    @Autowired
    private JobsService jobsService;
    @Autowired
    private AlluserService alluserService;
    @Autowired
    private StudioService studioService;
    @Autowired
    private JobuserService jobuserService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/jobsend")
    public String jobsend(String jobName,int jobNum,String jobMoney,String jobDemand,String jobIntro){
        Jobs j = new Jobs();
        Date date = new Date();
        j.setJobCreattime(date);
        j.setJobMoney(jobMoney);
        j.setJobIntro(jobIntro);
        j.setJobRequire(jobDemand);
        j.setJobState("招聘中");
        j.setJobNum(jobNum);
//        j.setJobType();
        j.setJobName(jobName);

        //获取当前登录用户
        getAlluser getAlluser = new getAlluser();
        Alluser alluser = new Alluser();
        alluser = getAlluser.aa();
        String uid = alluser.getAllId();

//        获取工作室id
        Studio studio =new Studio();
        studio = studioService.selectOne(new EntityWrapper<Studio>().eq("stu_creatid",uid));
        j.setJobStudio(studio.getStuId());

        boolean a = jobsService.insert(j);

        return "redirect:/studioPage/jobInfo";
//        return  "jobs/detail";
    }

    @GetMapping("/jobdelete/{jid}")
    public String jobdelete(@PathVariable("jid") int jid, Model model){
        System.out.println("-------jid "+jid);
        //根据jobid删除招聘信息
        boolean a = jobsService.deleteById(jid);
        System.out.println("------a "+a);

        return "redirect:/studioPage/jobInfo";
    }

//    通过审核
    @GetMapping("/pass/{uid}/{id}")
    public String jobpass(@PathVariable("id") String id,@PathVariable("uid")String uid){

//        System.out.println("-----jobuserid "+id+"--------uid "+uid);
//        System.out.println("--------uid "+uid);
        Jobuser jobuser = new Jobuser();
        jobuser = jobuserService.selectById(id);
//        System.out.println("--------jobuser "+jobuser);
        jobuser.setJuState("已通过");
        boolean a = jobuserService.update(jobuser,new EntityWrapper<Jobuser>().eq("ju_id",id));

        Users users = new Users();
        users = usersService.selectById(uid);
        users.setUserStudio(id);
        Date date = new Date();
        users.setUserEntrytime(date);
        boolean b = usersService.update(users,new EntityWrapper<Users>().eq("user_id",uid));

        return "redirect:/studioPage/jobReview";
    }

//    不同意||忽略审核
    @GetMapping("/nopass/{id}")
    public String jobNotPass(@PathVariable("id") String id){
        System.out.println("-------id "+id);
        Jobuser jobuser = new Jobuser();
        jobuser = jobuserService.selectById(id);
        jobuser.setJuState("未通过");
        boolean a = jobuserService.update(jobuser,new EntityWrapper<Jobuser>().eq("ju_id",id));

        return "redirect:/studioPage/jobReview";
    }

//    删除jobuers
    @GetMapping("/jUDel/{id}")
    public String jobUserDel(@PathVariable("id") int id){
        System.out.println("-------id "+id);
        boolean a = jobuserService.deleteById(id);
        System.out.println("------------a "+a);

        return "redirect:/studioPage/jobReview";
    }
}
