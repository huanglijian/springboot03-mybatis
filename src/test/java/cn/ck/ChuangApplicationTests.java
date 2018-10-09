package cn.ck;

import cn.ck.entity.*;
import cn.ck.entity.bean.DanmuJson;
import cn.ck.mapper.DanmuMapper;
import cn.ck.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

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
    DanmuMapper danmuMapper;
    @Autowired
    CollectresService collectresService;
    @Autowired
    BiddingService biddingService;
    @Autowired
    StudioService studioService;

    @Test
    public void contextLoads() throws Exception {
//        Page<Alluser> p = alluserService.selectUserByPage(new Page<>(1,1));
//        Danmu danmu = new Danmu();
//        for(int i = 0; i < 20; i++){
//            danmu.setDmResource(1);
//            danmu.setDmColor("white");
//            danmu.setDmText("我是组成弹幕");
//            danmu.setDmPosition(0);
//            danmu.setDmSendtime(new Date());
//            danmu.setDmTime(10);
//            danmuService.insert(danmu);
//        }
//        List<DanmuJson> danmuJsons = danmuMapper.selectDanmuJsonByResId(1);
//        danmuJsons = danmuService.sortListByTime(danmuJsons);
//        System.out.println(danmuJsons);
//        System.out.println(collectresService.isCollected("e9ae842a-e70f-49b8-a9b5-bee24a13c8bb", 2));
//        List l = biddingService.selectRecommendProj();
//        System.out.println(l);
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
