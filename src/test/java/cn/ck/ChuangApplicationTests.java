package cn.ck;

import cn.ck.entity.*;
import cn.ck.service.*;
import com.baomidou.mybatisplus.plugins.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        Page<Alluser> p = alluserService.selectUserByPage(new Page<>(1,1));

        System.out.println(p.getRecords().size());
        System.out.println(p.toString());
        System.out.println(p.getRecords().get(0));
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
