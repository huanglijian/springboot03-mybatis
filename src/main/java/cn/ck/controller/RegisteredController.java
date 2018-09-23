package cn.ck.controller;

import cn.ck.entity.Alluser;
import cn.ck.entity.Promulgator;
import cn.ck.service.AlluserService;
import cn.ck.utils.DESUtil;
import cn.ck.utils.DateUtils;
import cn.ck.utils.IPUtils;
import cn.ck.utils.ResponseBo;
import cn.ck.utils.mail.HtmlMailContent;
import cn.ck.utils.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/registered")
public class RegisteredController extends AbstractController{

    @Autowired
    AlluserService alluserService;
    @Autowired
    private MailService mailService;

    /**
     * 选择页面跳转
     * @param type 用户类型，没选择之前必须有字符
     * @return
     */
    @RequestMapping("/select/{type}")
    public String selectType(@PathVariable("type") String type){
        //通过modelmap将用户注册类型put到页面，用vue处理
        switch (type) {
            case "promulgator":
                return "login/register_first";
            case "user":
                return "login/register_first";
            default:
                return "login/register_select";
        }
    }

    /**
     * 异步验证邮箱是否已经注册
     * @param email 用户邮箱
     * @return
     */
    @RequestMapping("/isExist")
    @ResponseBody
    public ResponseBo isEmailExist(String email){
        if (alluserService.isExist(email))
            return ResponseBo.error(1, "该邮箱已被注册");
        else
            return ResponseBo.ok();
    }

    /**
     * 发送验证码邮件
     * @param email 用户邮箱
     */
    @RequestMapping("/sentCode")
    @ResponseBody
    public void sentEmail(String email) {
        //加密邮箱产生验证码
        String encode = DESUtil.encodeBASE64(email);
        //生成邮件内容
        String content = new HtmlMailContent(email, encode).getStringBuffer().toString();
        //发送邮件，测试时不使用
//        mailService.sendHtmlMail(email,"创客平台注册验证",content);
    }

    /**
     * 验证验证码是否匹配
     * @param email 用户邮箱
     * @param code 验证码
     * @param userType 用户类型
     * @return
     */
    @RequestMapping("/validateEmail")
    @ResponseBody
    public ResponseBo validateEmail(String email, String code, String userType){
        if (code == null || code.equals(""))
            return ResponseBo.error(1, "验证码错误");
        //解密验证码
        String decode = DESUtil.decodeBASE64(code);
        //与用户邮箱相同，则验证码正确
        if(decode == null || !decode.equals(email))
            return ResponseBo.error(1, "验证码错误");
        //持久化用户数据
        Alluser user = alluserService.setUserUUID(new Alluser());
        user.setAllEmail(email);
        user.setAllType(userType);
//        alluserService.insert(user);

        return ResponseBo.ok().put("UUID", user.getAllId());
    }

    /**
     * 注册第二步
     * 页面跳转
     * @param uuid
     * @return
     */
    @RequestMapping("/second")
    public String secondSetp(@RequestParam("UUID")String uuid){
        return "login/register_second";
    }

    /**
     * 根据UUID得到alluser实体
     * 根据用户类型传出用户实体或发布者实体
     * @param UUID
     * @return
     */
    @RequestMapping("/getUserByID")
    @ResponseBody
    public ResponseBo getUserByID(String UUID){
        Alluser alluser = alluserService.selectByUUID(UUID);
        Object userInfo = null;
        if(alluser == null)
            return ResponseBo.error("发生不明错误, 请重新注册");
        if(alluser.getAllType().equals("发布者"))
        {
            Promulgator prom = new Promulgator();
            prom.setPromId(UUID);
        }
        return ResponseBo.ok().put("user", alluser);
    }

    @RequestMapping("/getUserMessage")
    @ResponseBody
    public ResponseBo test(@RequestBody Alluser alluser){

        return ResponseBo.ok();
    }


}
