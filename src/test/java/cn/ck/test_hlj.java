package cn.ck;

import cn.ck.entity.Jobs;
import cn.ck.service.JobsService;
import cn.ck.service.impl.JobsServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import cn.ck.utils.MyBatisGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test_hlj {
    private SqlSession sqlSession;

    @Autowired
    private JobsService jobsService;

    @Before
    public void before() {
//        获取sqlaction

    }

    @Test
    public void test() throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateformat.parse("2016-6-19");
        System.out.println(dateformat.format(date));
    }

    @Test
    public void testInsert(){
        Date date = new Date();
        System.out.println(date);

        Jobs jobs = new Jobs();

//        jobs.setJobId(3211);
        jobs.setJobCreattime(date);
        jobs.setJobMoney("1231");
        jobs.setJobName("999");
        jobs.setJobStudio("cf20d9f3c7554dfdaa9a45a41494f2c4");
        jobs.setJobIntro("4564564");
        jobs.setJobRequire("1231231");
        jobs.setJobState("4123");
        jobs.setJobNum(12);
        jobs.setJobType("45");

        jobsService.insertAllColumn(jobs);
        jobsService.insertAllColumn(jobs);
        jobsService.insertAllColumn(jobs);
    }

    @Test
    public void testConcend() throws ParseException {
        //        生成Date类型数据
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = dateformat.parse("2016-6-19");
//        System.out.println(dateformat.format(date));
        Date date = new Date();
        System.out.println(date);

        Jobs jobs = new Jobs();

        jobs.setJobId(321);
        jobs.setJobCreattime(date);
        jobs.setJobMoney("1231");
        jobs.setJobName("999");
        jobs.setJobStudio("321");
        jobs.setJobIntro("4564564");
        jobs.setJobRequire("1231231");
        jobs.setJobState("4123");
        jobs.setJobNum(12);
        jobs.setJobType("45");
        jobsService.updateAllColumnById(jobs);
    }

    @Test
    public void testThird(){
        Jobs jobs = new Jobs();
//        List<Jobs> list = new ArrayList<Jobs>();//创建集合对象；
        jobs = jobsService.selectById(3212);
        System.out.println(jobs);
    }

    @Test
    public void testdelect(){

        Date date = new Date();
        System.out.println(date);

        Jobs jobs = new Jobs();

        jobs.setJobId(327);
        jobs.setJobCreattime(date);
        jobs.setJobMoney("1231");
        jobs.setJobName("999");
        jobs.setJobStudio("321");
        jobs.setJobIntro("4564564");
        jobs.setJobRequire("1231231");
        jobs.setJobState("4123");
        jobs.setJobNum(12);
        jobs.setJobType("45");

        boolean a = jobsService.delete(new EntityWrapper<Jobs>().eq("job_id",jobs.getJobId()));
        System.out.println("======= "+a);
    }

    @Test
    public void testSelectCount(){
        int jNum = jobsService.selectCount(new EntityWrapper<Jobs>());
        System.out.println("=========jNum "+jNum);
    }

}
