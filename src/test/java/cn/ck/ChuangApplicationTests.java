package cn.ck;

import cn.ck.entity.*;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.service.*;
import cn.ck.utils.DateUtils;
import cn.ck.utils.IPUtils;
import cn.ck.utils.ShiroUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChuangApplicationTests {

    @Autowired
    AlluserService alluserService;
    @Autowired
    AdminService adminService;
    @Autowired
    PromulgatorService promservice;
    @Autowired
    ProjectService projectService;
    @Autowired
    BiddingService biddingService;

    @Test
    public void contextLoads() {
        Project project = projectService.selectById(1);
        System.out.println(project);
    }

    @Test
    public void selectUser(){
        String id="7f628d5d-2265-49d4-b12d-d65b8f280901";
        Admin admin = new Admin();
        admin.setAdminPhone("test");
        admin.setAdminName("test");
        admin.setAdminImg("test");
        adminService.insert(admin);
        System.out.println(admin);
    }

    @Test
    public void promtest(){
        String id="af8cfc18-b84d-4825-a49c-e0f6cb527858";
//        Map<String,Object> bidmap=new HashMap<>();

        //匹配当前时间，更改目前项目竞标状态
        List<Project> proBidding=projectService.projBidTimefalse(id);
        for (Project project1:proBidding) {
            project1.setProjState("竞标结束");
        }
        if(!proBidding.isEmpty()){
            projectService.updateAllColumnBatchById(proBidding);
        }

        //查询更新后竞标中的项目
//        List<Project> projectList1=projectService.projBidTimetrue(id);
        List<Project> projectList1=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标中").eq("proj_prom",id));
//        List<Project> projectList2=projectService.selectList(new EntityWrapper<Project>().eq("proj_state","竞标结束").eq("proj_prom",id));
//        projectList1.addAll(projectList2);

        List<ProjectBid> bidList1=new ArrayList<ProjectBid>();

        for (Project project1:projectList1) {
            ProjectBid projectBid = new ProjectBid();
            projectBid.setProject(project1);
            //转换时间格式
            String creatdate=DateUtils.format(project1.getProjCreattime(),DateUtils.DATE_PATTERN);
            projectBid.setCreatdate(creatdate);
            //竞标剩余时间
            projectBid.setBidday(projectService.projBidTimeNum(project1.getProjId()));
            //竞标人数
            int count=biddingService.selectCount(new EntityWrapper<Bidding>().eq("bid_proj",project1.getProjId()));
            projectBid.setBidnum(count);
            bidList1.add(projectBid);

        }
        for (ProjectBid projectBid:bidList1) {
            System.out.println(projectBid);
        }


    }
}
