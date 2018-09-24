package cn.ck.controller.jobs;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Alluser;
import cn.ck.entity.Jobs;
import cn.ck.mapper.JobsMapper;
import cn.ck.service.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ForJob")
public class jobcontroller extends AbstractController {

    private JobsService jobsService;

    @GetMapping(value = "/search/jobs/{id}")
    public Jobs findJobsById(@PathVariable("id") int id){
        System.out.println("123123");
        return jobsService.selectById(id);
    }

    @RequestMapping("/search")
    public String search() {

        return "/jobs/search";
    }
}
