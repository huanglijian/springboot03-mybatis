package cn.ck.controller;

import cn.ck.entity.Alluser;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/registered")
public class RegisteredController extends AbstractController{

    @Autowired
    AlluserService alluserService;
    @Autowired
    private MailService mailService;

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

    @RequestMapping("/isExist")
    @ResponseBody
    public ResponseBo isEmailExist(String email){
        if (alluserService.isExist(email))
            return ResponseBo.error(1, "该邮箱已被注册");
        else
            return ResponseBo.ok();
    }

    @RequestMapping("/sentCode")
    @ResponseBody
    public void sentEmail(String email) {
        String encode = DESUtil.encodeBASE64(email);
        String content = new HtmlMailContent(email, encode).getStringBuffer().toString();
//        mailService.sendHtmlMail(email,"创客平台注册验证",content);
    }

    @RequestMapping("/validateEmail")
    @ResponseBody
    public ResponseBo validateEmail(String email, String code, String userType){
        if (code == null || code.equals(""))
            return ResponseBo.error(1, "验证码错误");

        String decode = DESUtil.decodeBASE64(code);

        if(decode == null || !decode.equals(email))
            return ResponseBo.error(1, "验证码错误");

        Alluser user = alluserService.setUserUUID(new Alluser());
        user.setAllEmail(email);
        user.setAllType(userType);
//        alluserService.insert(user);

        return ResponseBo.ok().put("UUID", user.getAllId());
    }

    @RequestMapping("/second")
    public String secondSetp(@RequestParam("UUID")String uuid, ModelMap modelMap){
        modelMap.put("uuid", uuid);
        return "login/register_second";
    }


}
