package com.neu;

import com.neu.bean.Enroll;
import com.neu.bean.Student;
import com.neu.bean.User;
import com.neu.mapper.EnrollMapper;
import com.neu.mapper.PublishMapper;
import com.neu.mapper.UserMapper;
import com.neu.mapper.StudentMapper;
import com.neu.service.EnrollService;
import com.neu.service.impl.EnrollServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03MybatisApplicationTests {

    @Autowired
    private UserMapper userMpa;
    @Autowired
    private StudentMapper mapper;
    @Autowired
    private EnrollMapper enrollMapper;
    @Autowired
    private PublishMapper publishMapper;

    @Test
    public void test(){
        Date date = new Date();
        System.out.println(date);

    }

    @Test
    public void contextLoads() {        //@Autowired

        User user = new User();
        user.setUserAddress("999");
        user.setUserId("99");
        user.setUserName("12");
        user.setUserPhone("45");
        int i = userMpa.insert(user);
        System.out.println(i+"-");
    }

    @Test
    public void  aa(){
        System.out.println("dd");
        List<Student> students = mapper.selectStuAll();
        System.out.println(students);

    }


    @Test
    public void vv(){
        Enroll enroll = enrollMapper.selectByPrimaryKey(5);
        System.out.println(enroll.toString());
        System.out.println("=============");
    }

//    @Autowired
//    private EnrollService enrollService;
    @Test
    public void dd(){

        List<Enroll> enrolls = enrollMapper.selectByEnr_publish(5);
        System.out.println(enrolls+"---------------");
    }

    @Test
    public void ddd(){
        List<Enroll> enrolls = enrollMapper.selectByUserId("5");
        System.out.println(enrolls+"++++++");
    }

    @Test
    public void add1(){
        int i = publishMapper.addWatchNum(5);
        System.out.println("+++"+i);
    }


}
