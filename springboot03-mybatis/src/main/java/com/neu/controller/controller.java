package com.neu.controller;

import com.neu.bean.Enroll;
import com.neu.bean.Publish;
import com.neu.mapper.PublishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class controller {

    @Autowired
    private PublishMapper publishMapper;
    @Autowired Publish publish;
    @Autowired
    private Enroll enroll;


    @RequestMapping("/test")
    public String test(@RequestParam("id") int id,@RequestParam("name") String name){
        System.out.println("id=="+id);
        int i = id;
        System.out.println("i=="+i);
        System.out.println("name=="+name);
        return "455";
    }


//    发布活动
    @RequestMapping("/fabu")
    public String fabu(@RequestParam("theme") String pub_theme,@RequestParam("scene") String pub_scence,
                       @RequestParam("intro")String pub_intro,@RequestParam("user")String pub_user){

        publish.setPubTheme(pub_theme);
        publish.setPubScene(pub_scence);
        publish.setPubIntro(pub_intro);

        //获取当前时间
        Date date = new Date();
        System.out.println(date);

        publish.setPubDate(date);
        publish.setPubWatchnum(0);
        publish.setPubState(0);
        publish.setPubUser(pub_user);

        int cg = publishMapper.insert(publish);

        return "1";
    }


    //报名活动
    @RequestMapping("/baoming")
    public String baoming(@RequestParam("user") String enr_user,@RequestParam("publish") int enr_publish,
                          @RequestParam("note")String enr_note){
//        enroll.set

        return "";
    }


}
