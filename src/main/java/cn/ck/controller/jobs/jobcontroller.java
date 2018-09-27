package cn.ck.controller.jobs;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Alluser;
import cn.ck.entity.Jobs;
import cn.ck.mapper.JobsMapper;
import cn.ck.service.JobsService;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ForJob")
//@ResponseBody
public class jobcontroller extends AbstractController {


    @Autowired
    private JobsService jobsService;

    @RequestMapping("/search")
//    @GetMapping("/serch")
    public String search() {
        Jobs jobs = new Jobs();
//        获得所有的招聘信息
        List<Jobs> jobss = jobsService.selectList(new EntityWrapper<Jobs>());
//        System.out.println("=========== "+jobss);

//        获得招聘的个数
        int jNum = jobsService.selectCount(new EntityWrapper<Jobs>());
        System.out.println("=========jNum "+jNum);
        return "jobs/search";
    }

    @PostMapping("/search")
    @ResponseBody
    public List<Jobs> searchJson(){
        Jobs jobs = new Jobs();
        //        获得所有的招聘信息
        List<Jobs> jobss = jobsService.selectList(new EntityWrapper<Jobs>());
        System.out.println("===========all jobs "+jobss);
        return jobss;
    }
    @PostMapping("/search/jNum")
    @ResponseBody
    public ResponseBo jNum(){
        System.out.println("执行url:/search/jNum");
        //        获得招聘的个数
        int jNum = jobsService.selectCount(new EntityWrapper<Jobs>());
        System.out.println("=========jNum "+jNum);
        return ResponseBo.ok().put("jNum", 3);
    }




}
