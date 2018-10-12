package cn.ck.controller.jobs;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Alluser;

public class getAlluser extends  AbstractController{


    public Alluser aa(){
        Alluser alluser = new Alluser();
        alluser = super.getUser();
        return  alluser;
    }
}
