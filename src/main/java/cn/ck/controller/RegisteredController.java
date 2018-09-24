package cn.ck.controller;

import cn.ck.entity.Alluser;
import cn.ck.entity.Promulgator;
import cn.ck.entity.Users;
import cn.ck.service.AlluserService;
import cn.ck.service.PromulgatorService;
import cn.ck.service.UsersService;
import cn.ck.utils.*;
import cn.ck.utils.mail.HtmlMailContent;
import cn.ck.utils.mail.MailService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/registered")
public class RegisteredController extends AbstractController{

    @Autowired
    private AlluserService alluserService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PromulgatorService promulgatorService;
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
        //发送邮件
        mailService.sendHtmlMail(email,"创客平台注册验证",content);
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
        Alluser user = new Alluser();
        user.setAllEmail(email);
        user.setAllType(userType);
        user.setAllSalt("not set");
        user.setAllPwd("not set");
        user.setAllState("2");
        alluserService.insert(user);

//        //生成该用户类型的记录，并持久化
//        if(userType.equals("普通用户")){
//            Users userInfo = new Users();
//            userInfo.setUserId(user.getAllId());
//            usersService.insert(userInfo);
//        }
//        else if(userType.equals("发布者")){
//            Promulgator promulgator = new Promulgator();
//            promulgator.setPromId(user.getAllId());
//            promulgatorService.insert(promulgator);
//        }

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
        Alluser alluser = alluserService.selectById(UUID);
        if(alluser == null)
            return ResponseBo.error("发生不明错误, 请重新注册");

        //<待修改>
        //以发布者实体作为填写信息的载体
        Promulgator prom = new Promulgator();
        prom.setPromId(UUID);

        alluser.setAllPwd("");
        //将两个实体以json数据传到页面，由vue处理
        return ResponseBo.ok().put("userAccount", alluser).put("userInfo", prom);
    }

    /**
     *
     * 从页面得到用户密码等信息
     * 以发布者实体接受
     * 到此控制器再根据用户类型转化
     * 设置盐并加密密码
     * 持久化至数据库中
     * @param data
     * @return
     * @throws Exception
     */
    @RequestMapping("/setUserInfo")
    @ResponseBody
    public ResponseBo test(@RequestBody Map<String, Object> data) throws Exception {
        //根据json数据转化成map，再转成对应的类
        Alluser userAccount = JsonUtils.map2obj((Map<String, Object>)data.get("data1"), Alluser.class);

        //<待修改>
        Promulgator promulgator = JsonUtils.map2obj((Map<String, Object>)data.get("data2"), Promulgator.class);
        //如果是普通用户，取出信息
        if(userAccount.getAllType().equals("普通用户")){
            Users userInfo = new Users();
            userInfo.setUserId(promulgator.getPromId());
            userInfo.setUserPhone(promulgator.getPromPhone());
            userInfo.setUserAbipay(promulgator.getPromAbipay());
            userInfo.setUserName(promulgator.getPromName());
            userInfo.setUserLogintime(new Date());
            userInfo.setUserImg("no");
//            usersService.updateById(userInfo);
            usersService.insert(userInfo);
        }

        else if(userAccount.getAllType().equals("发布者")){
            promulgator.setPromPaypwd("no");
            promulgator.setPromLogintime(new Date());
            promulgator.setPromImg("no");
//            promulgatorService.updateById(promulgator);
            promulgatorService.insert(promulgator);
        }

        else return ResponseBo.error("出现不明错误，请重新注册");

        //设置盐并加密密码，更新用户实体,更新用户状态
        userAccount.setAllState("1");
        String pw = userAccount.getAllPwd();
        alluserService.encodePwd(userAccount);
        alluserService.updateById(userAccount);

        //自动登录
        UsernamePasswordToken token = new UsernamePasswordToken(userAccount.getAllEmail(), pw);
        Subject subject = SecurityUtils.getSubject();
        try {
            //shiro的登录方法
            subject.login(token);
        } catch (UnknownAccountException e) {
            return ResponseBo.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return ResponseBo.error("账号或密码错误");
        } catch (LockedAccountException e) {
            return ResponseBo.error(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseBo.error("认证失败！");
        }

        return ResponseBo.ok();
    }

    @RequestMapping("third")
    public String third(){
        return "login/register_third";
    }

}
