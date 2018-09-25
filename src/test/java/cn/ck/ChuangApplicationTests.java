package cn.ck;

import cn.ck.entity.Admin;
import cn.ck.entity.Alluser;
import cn.ck.entity.Project;
import cn.ck.entity.Promulgator;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.service.AdminService;
import cn.ck.service.AlluserService;
import cn.ck.service.ProjectService;
import cn.ck.service.PromulgatorService;
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
        Map<String,Object> bidmap=new HashMap<>();

        //匹配当前时间，更改目前项目竞标状态
        List<Project> proBidding=projectService.projBidTimefalse(id);
        for (Project project1:proBidding) {
            project1.setProjState("竞标结束");
        }
        if(!proBidding.isEmpty()){
            projectService.updateAllColumnBatchById(proBidding);
        }

        //查询更新后竞标中的项目
        List<Project> projectList1=projectService.projBidTimetrue(id);

        List<ProjectBid> bidList1=new ArrayList<ProjectBid>();

        for (Project project1:projectList1) {
            ProjectBid projectBid = new ProjectBid();
            projectBid.setProject(project1);
            String creatdate=DateUtils.format(project1.getProjCreattime(),DateUtils.DATE_TIME_PATTERN);
            projectBid.setCreatdate(creatdate);
            bidList1.add(projectBid);
        }
        for (ProjectBid projectBid1:bidList1) {
            System.out.println(bidList1);
        }

    }
}
