package cn.ck;

import cn.ck.entity.*;
import cn.ck.mapper.PermissionMapper;
import cn.ck.service.*;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChuangApplicationTests {

    @Autowired
    AlluserService alluserService;
    @Autowired
    AdminService adminService;
    @Autowired
    UsersService usersService;
    @Autowired
    PromulgatorService promservice;
    @Autowired
    JobsService jobsService;
    @Autowired
    ProjectService projectService;

    @Test
    public void contextLoads() {
        Jobs jobs = jobsService.selectById(321);
        System.out.println(jobs);
    }

    @Test
    public void selectUser(){
        String id="e9ae842a-e70f-49b8-a9b5-bee24a13c8bb";
        Users users = usersService.selectById(id);
        System.out.println(users);
    }

    @Test
    public void promtest(){
        String id="af8cfc18-b84d-4825-a49c-e0f6cb527858";
        Promulgator prom=new Promulgator();
        prom=promservice.selectID(id);
        System.out.println(prom);
    }
}
