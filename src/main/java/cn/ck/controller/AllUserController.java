package cn.ck.controller;

import cn.ck.entity.Alluser;
import cn.ck.service.AlluserService;
import cn.ck.utils.ResponseBo;
import cn.ck.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.ck.utils.ShiroUtils.getUserId;

@Controller("/allUser")
public class AllUserController {

    @Autowired
    AlluserService alluserService;

    @RequestMapping("/saveuser")
    public String saveUser(Alluser alluser){
        alluserService.insert(alluser);
        return "";
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public ResponseBo updatePassword(String oldPassword, String newPassword){
//        //原密码
//        oldPassword = ShiroUtils.sha256(oldPassword, getUser().getSalt());
//        //新密码
//        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
//
//        //更新密码
//        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
//        if(!flag){
//            return ResponseBo.error("原密码不正确");
//        }

        return ResponseBo.ok();
    }
}
