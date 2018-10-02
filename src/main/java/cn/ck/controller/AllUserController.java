package cn.ck.controller;

import cn.ck.service.AlluserService;
import cn.ck.utils.ResponseBo;
import cn.ck.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.ck.utils.ShiroUtils.getUserId;

@Controller
@RequestMapping("/allUser")
public class AllUserController extends AbstractController{

    @Autowired
    AlluserService alluserService;

    /**
     * 修改密码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 如果成功返回code=0，msg=操作成功
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public ResponseBo updatePassword(String oldPassword, String newPassword){
        //原密码
        oldPassword = ShiroUtils.sha256(oldPassword, getUser().getAllSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getAllSalt());

        //更新密码
        boolean flag = alluserService.updatePassword(getUserId(), oldPassword, newPassword);
        if(!flag){
            return ResponseBo.error("原密码不正确");
        }
        return ResponseBo.ok();
    }
}
