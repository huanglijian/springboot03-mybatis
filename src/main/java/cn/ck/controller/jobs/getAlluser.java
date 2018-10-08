package cn.ck.controller.jobs;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Alluser;

public class getAlluser extends  AbstractController{


    public Alluser aa(){
        System.out.println("333");
        Alluser alluser = new Alluser();
        alluser = super.getUser();
        System.out.println(alluser);
        return  alluser;
    }
}
