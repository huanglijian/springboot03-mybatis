package cn.ck;

import cn.ck.entity.Alluser;
import cn.ck.entity.Promulgator;
import cn.ck.service.AlluserService;
import cn.ck.service.PromulgatorService;
import cn.ck.utils.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChuangApplicationTests {

    @Autowired
    AlluserService alluserService;
    @Autowired
    PromulgatorService promservice;

    @Test
    public void contextLoads() {
        Alluser user = new Alluser();

        String userID = UUID.randomUUID().toString();
        user.setAllId(userID);

        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setAllSalt(salt);

        String pwd = ShiroUtils.sha256("123456", user.getAllSalt());
        user.setAllPwd(pwd);

        user.setAllEmail("2");
        user.setAllState("1");
        user.setAllType("发布者");

        alluserService.insert(user);
    }

    @Test
    public void promtest(){
        String id="af8cfc18-b84d-4825-a49c-e0f6cb527858";
        Promulgator prom=new Promulgator();
        prom=promservice.selectID(id);
        System.out.println(prom);
    }
}
