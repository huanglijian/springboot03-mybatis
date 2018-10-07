package cn.ck.controller.studio;

import cn.ck.controller.jobs.getAlluser;
import cn.ck.entity.Alluser;
import cn.ck.entity.Danmu;
import cn.ck.entity.Jobs;
import cn.ck.entity.Studio;
import cn.ck.service.AlluserService;
import cn.ck.service.JobsService;
import cn.ck.service.JobuserService;
import cn.ck.service.StudioService;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/jobdeal")
public class jobSendController {

    @Autowired
    private JobsService jobsService;
    @Autowired
    private AlluserService alluserService;
    @Autowired
    private StudioService studioService;

    @PostMapping("/jobsend")
    public String jobsend(String jobName,int jobNum,String jobMoney,String jobDemand,String jobIntro){
        System.out.println("---------招聘岗位"+jobName);
        System.out.println("---------人数"+jobNum);
        System.out.println("---------薪资"+jobMoney);
        System.out.println("---------要求"+jobDemand);
        System.out.println("---------简介"+jobIntro);

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
        System.out.println("--------uid "+uid);

        Studio studio =new Studio();
        studio = studioService.selectOne(new EntityWrapper<Studio>().eq("stu_creatid",uid));
        System.out.println("----------stuid "+studio.getStuId());
        j.setJobStudio(studio.getStuId());

        boolean a = jobsService.insert(j);
        System.out.println("-----a "+a);

        return "redirect:/studioPage/jobInfo";
//        return  "jobs/detail";
    }

}
