package cn.ck.controller.user;


import cn.ck.controller.AbstractController;
import cn.ck.controller.FileController;
import cn.ck.entity.*;
import cn.ck.entity.bean.OriColUser;
import cn.ck.entity.bean.PjCol;
import cn.ck.entity.bean.ResCol;
import cn.ck.service.*;
import cn.ck.utils.ConstCofig;
import cn.ck.utils.ResponseBo;
import cn.ck.utils.ShiroUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static cn.ck.utils.ShiroUtils.getUserId;

@Controller
@RequestMapping("/user")
public class UsersController extends AbstractController {

    @Autowired
    AlluserService alluserService;
    @Autowired
    UsersService usersService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    ProjectService projectService;
    @Autowired
    CollectpjService collectpjService;
    @Autowired
    PromulgatorService promulgatorService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    CollectresService collectresService;
    @Autowired
    OriginalService originalService;
    @Autowired
    CollectoriService collectoriService;
    @Autowired
    StudioService studioService;

    int start;//记录当前页 默认为“0“
    int size; //记录每页条目数，默认为”10“
    String state=null; //记录当前页状态
    String pwstate="0"; //记录修改状态
    String upstate="0";//记录文件上传状态
    String userId;



    //跳转当前登录用户主页
    @RequestMapping("/user_hp")
    public String user_hp(String userId){
        this.userId=userId;
        return "users/pc_homepage";
    }

    //跳转当前登录用户个人资料页面
    @RequestMapping("/user_info")
    public String user_info(){
        return "users/pc_information";
    }

    //跳转修改用户资料页面
    @RequestMapping("/user_mfyinfo")
    public String user_mfyinfo(){
        return "users/pc_mfy_information";
    }

    //跳转修改用户修改头像页面
    @RequestMapping("/user_mfyimg")
    public String user_mfyimg(){
        return "users/pc_updphoto";
    }

    //跳转修改用户通知消息页面
    @RequestMapping("/user_sysn")
    public String user_sysn(@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "20") int size){
        this.start=start;
        this.size=size;
        return "users/pc_sysn";
    }

    //跳转修改用户通知消息页面
    @RequestMapping("/user_sysn2")
    public String user_sysn2(@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "20") int size){
        this.start=start;
        this.size=size;
        return "users/pc_sysn2";
    }

    //跳转用户项目收藏页面
    @RequestMapping("/user_pjcol")
    public String user_pjcol(@RequestParam(value = "state", defaultValue = "0") String state,@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "10") int size){
        this.state=state;
        this.start=start;
        this.size=size;
        return "users/pc_xiangmu_collection";
    }

    //跳转用户视频收藏页面
    @RequestMapping("/user_veocol")
    public String user_veocol(@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "10") int size){
        this.start=start;
        this.size=size;
        return "users/pc_video_collection";
    }

    //跳转用户代码收藏页面
    @RequestMapping("/user_codecol")
    public String user_codecol(@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "10") int size){
        this.start=start;
        this.size=size;
        return "users/pc_code_collection";
    }

    //跳转用户代码页面
    @RequestMapping("/user_yc")
    public String user_yc(@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "10") int size){
        this.start=start;
        this.size=size;
        return "users/pc_code_yuanchuang";
    }

    //跳转代码上传页面
    @RequestMapping("/user_resupload")
    public String user_resupload(){
        return "users/pc_resource_upload";
    }

    //跳转用户修改登录密码页面
    @RequestMapping("/user_uppw")
    public String user_uppw(){
        return "users/pc_updpwd";
    }

    //跳转用户修改登录密码页面
    @RequestMapping("/user_uppw2")
    public String user_uppw2(){
        return "users/pc_updpwd2";
    }

    //跳转用户修改支付密码页面
    @RequestMapping("/user_upppw")
    public String user_upppw(){
        return "users/pc_updpyp";
    }

    //跳转用户修改支付密码页面2
    @RequestMapping("/user_upppw2")
    public String user_upppw2(){
        return "users/pc_updpyp_2";
    }

    //跳转用户注销页面
    @RequestMapping("/user_delete")
    public String user_delete(){
        return "users/pc_zhuxiao";
    }

    //跳转用户资金管理页面
    @RequestMapping("/user_money")
    public String user_money(){
        return "users/pc_funs";
    }

    //跳转用户资金管理页面
    @RequestMapping("/test")
    public String test(){
        return "users/test";
    }


    //获取用户头像
    @RequestMapping("/showImg")
    public void previewsrc(HttpServletResponse response) {
        //将文件名分割成 文件名 和 格式
        //“ . " 需要用两次转义
        Users u=usersService.selectById(getUser().getAllId());
        String path = u.getUserImg();
        //获取服务器中的文件
        File imgFile = new File(ConstCofig.RootPath + ConstCofig.ImgPath + path);
        //输出到页面
        FileController.responseFile(response, imgFile);
    }

    //获取用户头像
    @RequestMapping("/showImg2")
    public void previewsrc2(HttpServletResponse response) {
        //将文件名分割成 文件名 和 格式
        //“ . " 需要用两次转义
        Users u=usersService.selectById(userId);
        String path = u.getUserImg();
        //获取服务器中的文件
        File imgFile = new File(ConstCofig.RootPath + ConstCofig.ImgPath + path);
        //输出到页面
        FileController.responseFile(response, imgFile);
    }

    //获取个人主页信息
    @RequestMapping("/hp")
    @ResponseBody
    public Map<String, Object> hp(){
        Map<String,Object> user=new HashMap<>();
        Users u = usersService.selectById(userId);
        //把当前登录用户对象放入Map传到前台
        user.put("user",u);
        user.put("alluser",getUser());
        user.put("pwstate",pwstate);
        pwstate="0";
        return user;
    }

    //获取当前登录用户信息
    @RequestMapping("/get_info")
    @ResponseBody
    public Map<String, Object> get_info(){
        Map<String,Object> user=new HashMap<>();
        Users u = usersService.selectById(getUser().getAllId());
        //把当前登录用户对象放入Map传到前台
        user.put("user",u);
        user.put("alluser",getUser());
        user.put("pwstate",pwstate);
        pwstate="0";
        return user;
    }

    //获取用户主页需要显示的信息
    @RequestMapping("/get_hp")
    @ResponseBody
    public Map<String,Object> get_hp(){
        Users users=null;
        Set<String> set = new HashSet<>();
        set.add("orig_uploadtime");
        List<Original> originals=originalService.selectList(new EntityWrapper<Original>().eq("orig_users",userId).orderDesc(set));
        Studio studio=studioService.selectById(usersService.selectById(userId).getUserStudio());
        if(!(studio==null))
            users=usersService.selectById(studio.getStuCreatid());
        for(Original original:originals){
            original.setOrigTag(original.getOrigTag().replace(","," "));
        }
        Map<String,Object> map=new HashMap<>();
        map.put("originals",originals);
        map.put("studio",studio);
        map.put("user",users);
        return map;
    }

    //获取用户原创被收藏和分享次数
    @RequestMapping("/get_share")
    @ResponseBody
    public Map<String,Object> get_share(){
        int share=0;
        int collected=0;
        List<Original> originals=originalService.selectList(new EntityWrapper<Original>().eq("orig_users",userId));
        share=originals.size();
        for(Original original:originals){
            collected+=collectoriService.selectList(new EntityWrapper<Collectori>().eq("colo_ogi",original.getOrigId())).size();
        }
        Map<String,Object> map=new HashMap<>();
        map.put("share",share);
        map.put("collected",collected);
        return map;
    }

    //获取当前登录用户的系统通知消息
    @RequestMapping("/get_sysn")
    @ResponseBody
    public PageInfo<Notice> get_sysn(){
        PageHelper.startPage(start,size,"noti_time desc");//,"noti_time desc"
        List<Notice> notices=noticeService.selectList(new EntityWrapper<Notice>().eq("noti_foreid",getUser().getAllId()).eq("noti_state","否"));
        PageInfo<Notice> page = new PageInfo<>(notices);
        return page;
    }

    //获取当前登录用户的系统通知消息
    @RequestMapping("/get_sysn2")
    @ResponseBody
    public PageInfo<Notice> get_sysn2(){
        PageHelper.startPage(start,size,"noti_time desc");//,"noti_time desc"
        List<Notice> notices=noticeService.selectList(new EntityWrapper<Notice>().eq("noti_state","是"));
        PageInfo<Notice> page = new PageInfo<>(notices);
        return page;
    }

    //获取当前登录用户的未读系统通知消息
    @RequestMapping("/get_sysnnum")
    @ResponseBody
    public int get_sysnnum(){
        List<Notice> notices=noticeService.selectList(new EntityWrapper<Notice>().eq("noti_state","否").eq("noti_foreid",getUser().getAllId()));
        return notices.size();
    }

    //获取当前登录用户的收藏项目
    @RequestMapping("/get_pjcol")
    @ResponseBody
    public Map<String,Object> get_pjcol(){
        PageHelper.startPage(this.start,this.size);
        Map<String,Object> map=new HashMap<>();
        map.put("id",getUser().getAllId());
        map.put("state",this.state);
        List<PjCol> pjColList= projectService.selectPjCol(map);
        //设置项目发布者的姓名、竞标状态和标签属性
        for(PjCol p:pjColList) {
            p.setProjProm(promulgatorService.selectById(p.getProjProm()).getPromName());
            if (p.getProjState().equals("竞标中")) ;
            else
                p.setProjState("竞标中止");
            p.setProjTag(p.getProjTag().replace(",", " "));
            p.setProjId(Integer.parseInt(state));
        }
        PageInfo<PjCol> page = new PageInfo<>(pjColList);
        map.clear();
        map.put("page",page);
        map.put("state",state);
        return map;
    }

    //获取当前登录用户的视频收藏信息
    @RequestMapping("/get_veocol")
    @ResponseBody
    public PageInfo<ResCol> get_veocol(){
        PageHelper.startPage(start,size);
        List<ResCol> resColList=resourceService.selectDesc(getUser().getAllId());
        for(ResCol resCol:resColList){
            resCol.setResTag(resCol.getResTag().replace(","," "));
        }
        PageInfo<ResCol> page = new PageInfo<>(resColList);
        return page;
    }

    //获取当前登录用户的代码收藏信息
    @RequestMapping("/get_codecol")
    @ResponseBody
    public PageInfo<OriColUser> get_codecol(){
        PageHelper.startPage(start,size);
        List<OriColUser> oriColList=originalService.selectDesc(getUser().getAllId());
        for(OriColUser oriCol:oriColList){
            oriCol.setOrigTag(oriCol.getOrigTag().replace(","," "));
        }
        PageInfo<OriColUser> page = new PageInfo<>(oriColList);
        return page;
    }

    //获取当前登录用户的上传的所有资源
    @RequestMapping("/get_yc")
    @ResponseBody
    public PageInfo<Original> get_yc(){
        PageHelper.startPage(start,size,"orig_uploadtime desc");
        List<Original> originals=originalService.selectList(new EntityWrapper<Original>().eq("orig_users",getUser().getAllId()));
        for(Original original:originals){
            original.setOrigTag(original.getOrigTag().replace(","," "));
        }
        PageInfo<Original> page=new PageInfo<>(originals);
        return page;
    }


    //更新当前登录用户个人资料
    @RequestMapping("/update_info")
    public String update_info(String username,String mobile,int age,String sex,String zhifubao,String jianjie){
        Users u=usersService.selectById(getUser().getAllId());
        u.setUserName(username);
        u.setUserAbipay(zhifubao);
        u.setUserAge(age);
        u.setUserIntro(jianjie);
        u.setUserSex(sex);
        u.setUserPhone(mobile);
        usersService.updateAllColumnById(u);
        pwstate="2";
        return "redirect:/user/user_mfyinfo";
    }

    //更新当前用户登录密码
    @RequestMapping("/update_pw")
    @ResponseBody
    public Boolean update_pw(String oldPassword,String newPassword){
        //原密码
        oldPassword = ShiroUtils.sha256(oldPassword, getUser().getAllSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getAllSalt());
        //更新密码
        boolean flag = alluserService.updatePassword(getUserId(), oldPassword, newPassword);
        if(!flag){
            return false;
        }
        pwstate="2";
        return true;
    }

    //更新当前登录用户支付密码
    @RequestMapping("/update_ppw")
    @ResponseBody
    public Boolean update_ppw(String pay_password){
        Users u=usersService.selectById(getUser().getAllId());
        u.setUserPaypwd(pay_password);
        usersService.updateAllColumnById(u);
        pwstate="2";
        return true;
    }

    //更新用户头像
    @PostMapping("/updatephoto")
    @ResponseBody
    public ResponseBo decodeBase64DataURLToImage(@RequestParam("dataURL") String dataURL) throws IOException {
        // 将dataURL开头的非base64字符删除
        String base64 = dataURL.substring(dataURL.indexOf(",") + 1);
        String path = "E:\\ChuangKeFile\\Img\\";
        String imgName = UUID.randomUUID() + ".png";
        FileOutputStream write = new FileOutputStream(new File(path + imgName));
        byte[] decoderBytes = Base64.getDecoder().decode(base64);
        write.write(decoderBytes);
        write.close();
        Users user=usersService.selectById(getUser().getAllId());
        user.setUserImg(imgName);
        usersService.updateAllColumnById(user);
        //将路径更新至数据库
        /*Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator = promulgatorService.selectID(user.getAllId());
        promulgator.setPromImg(imgName);
        promulgatorService.updateAllColumnById(promulgator);*/
        return ResponseBo.ok();
    }

    //更新消息为已读状态
    @RequestMapping("update_sysn")
    @ResponseBody
    public boolean update_sysn(String notiId){
        Notice notice=noticeService.selectById(notiId);
        notice.setNotiState("是");
        return noticeService.updateAllColumnById(notice);
    }

    //更新消息为已读状态
    @RequestMapping("update_allsysn")
    @ResponseBody
    public int update_allsysn(String notiId){
        List<Notice> notices=noticeService.selectList(new EntityWrapper<Notice>().eq("noti_state","否").eq("noti_foreid",getUser().getAllId()));
        if(notices.size()!=0){
            for (Notice notice:notices) {
                notice.setNotiState("是");
                noticeService.updateAllColumnById(notice);
            }
        }
        return notices.size();
    }

    //删除通知消息
    @RequestMapping("/delete_sysn")
    @ResponseBody
    public boolean delete_sysn(String notiId){
        if(noticeService.deleteById(Integer.parseInt(notiId)))
            return true;
        else
            return false;
    }

    //删除收藏的项目
    @RequestMapping("/delete_pjcol")
    @ResponseBody
    public boolean delete_pjcol(String pcid){
        if(collectpjService.deleteById(Integer.parseInt(pcid)))
            return true;
        else
            return false;
    }

    //删除收藏的讲堂视频
    @RequestMapping("/delete_veocol")
    @ResponseBody
    public boolean delete_veocol(String vcid){
        if(collectresService.deleteById(Integer.parseInt(vcid)))
            return true;
        else
            return false;
    }

    //删除收藏的代码资源
    @RequestMapping("/delete_codecol")
    @ResponseBody
    public boolean delete_codecol(String cecid){
        if(collectoriService.deleteById(Integer.parseInt(cecid)))
            return true;
        else
            return false;
    }

    //删除已上传的资源
    @RequestMapping("/delete_yc")
    @ResponseBody
    public boolean delete_yc(String ycid){
        if(originalService.deleteById(Integer.parseInt(ycid)))
            return true;
        else
            return false;
    }


    //验证当前登录用户登录密码
    @RequestMapping("/validate_pw")
    @ResponseBody
    public Boolean validate_pw(String used_password){
        Alluser alluser=alluserService.selectById(getUser().getAllId());
        return ShiroUtils.sha256(used_password, getUser().getAllSalt()).equals(alluser.getAllPwd());
    }

    //验证当前登录用户支付密码
    @RequestMapping("/validate_ppw")
    @ResponseBody
    public Boolean validate_ppw(String used_pay_password){
        if(usersService.selectById(getUser().getAllId()).getUserPaypwd().equals(used_pay_password))
            return true;
        else
            return false;
    }

    //获取文件上传状态
    @RequestMapping("/validate_up")
    @ResponseBody
    public String validate_up(){
        String result=upstate;
        upstate="0";
        return result;
    }

    //资源上传
    @RequestMapping("/res_upload")
    public String res_upload(@RequestParam("file") MultipartFile file,@RequestParam("codename") String codename,@RequestParam("category") String category,@RequestParam("tag") String tag,@RequestParam("jianjie") String jianjie){


        try {
            String path = "E:\\ChuangKeFile\\Ori\\";
            Original original=new Original();
            original.setOrigName(codename);
            original.setOrigTag(tag);
            original.setOrigIntro(jianjie);
            original.setOrigPath(FileController.fileupload(file,path).get(0));
            original.setOrigType(category);
            original.setOrigUsers(getUser().getAllId());
            original.setOrigUploadtime(new Date());
            original.setOrigGrade(0.0);
            if(originalService.insert(original))
                upstate="1";
            else
                upstate="2";
            return "redirect:/user/user_resupload";
        } catch (Exception e) {
            upstate="2";
            return "redirect:/user/user_resupload";
        }



    }
/*
    //验证当前登录用户支付密码
    @RequestMapping("/validate_pw")
    public String validate_pw(String used_password){
        if(usersService.selectById(getUser().getAllId()).getUserPaypwd().equals(used_password))
            return "redirect:/user/user_uppw2";
        else {
            this.pwstate="1";
            return "redirect:/user/user_uppw";
        }
    }*/


    //test()
    /*@RequestMapping("/test")
    @ResponseBody
    public List<PjCol> test(){
        List<PjCol> pjColList= projectService.selectPjCol(getUser().getAllId());
        return pjColList;
    }*/
}
