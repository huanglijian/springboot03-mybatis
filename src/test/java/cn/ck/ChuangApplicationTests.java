package cn.ck;

import cn.ck.entity.*;
import cn.ck.entity.bean.DanmuJson;
import cn.ck.entity.bean.ResColNum;
import cn.ck.mapper.DanmuMapper;
import cn.ck.service.*;
import cn.ck.utils.EnityUtils;
import cn.ck.utils.JsonUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
    @Autowired
    ResourceService resourceService;
    @Autowired
    DanmuService danmuService;
    @Autowired
    CollectresService collectresService;
    @Autowired
    BiddingService biddingService;
    @Autowired
    StudioService studioService;

    @Test
    public void contextLoads() throws Exception {

//        System.out.println(tags);
    }

    @Test
    public void selectUser() {
        String id = "e9ae842a-e70f-49b8-a9b5-bee24a13c8bb";
        Users users = usersService.selectById(id);
        System.out.println(users);
    }

    @Test
    public void promtest() {
        double projnum=projectService.selectCount(new EntityWrapper<Project>().addFilter("DATE_FORMAT(proj_creattime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )",""));
        double preprojnum=projectService.selectCount(new EntityWrapper<Project>().addFilter("DATE_FORMAT( CURDATE( ) , '%Y%m' ) - DATE_FORMAT(proj_creattime, '%Y%m' ) =1",""));
        int projtip=0;
        double projpercent;
        if(preprojnum==0){
            projtip=1;
            projpercent=100;
        }else if(projnum<preprojnum){
            projpercent=100-(projnum/preprojnum)*100;
            projtip=0;
        }else{
            projpercent=projnum/preprojnum;
            projtip=1;
        }
        System.out.println(projnum+"----"+preprojnum+"---"+projpercent);
    }
}
