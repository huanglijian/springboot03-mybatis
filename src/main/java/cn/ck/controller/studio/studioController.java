package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import cn.ck.controller.FileController;
import cn.ck.entity.*;
import cn.ck.entity.bean.JobUsers;
import cn.ck.entity.bean.ProjImg;
import cn.ck.entity.bean.StudioTag;
import cn.ck.service.*;
import cn.ck.utils.ConstCofig;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/9/26 16:01
 **/
@Controller
@RequestMapping("/studio")
public class studioController extends AbstractController {
    @Autowired
    private StudioService studioService;
    @Autowired
    private UsersService usersService;
    @Autowired
    JobsService jobsService;
    @Autowired
    private PromulgatorService promulgatorService;
    @Autowired
    private JobuserService jobuserService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/studioNone")
    public String test1(){
        Users u = usersService.selectById(getUser().getAllId());
        System.out.println(getUser().getAllId());
        if(u.getUserStudio() != null)
            return "studio/studio_index";
        else
            return "studio/studio_creat";
    }


/*    @GetMapping("/studioList")
    @ResponseBody
    public Studio slist(){
        String zzid = getUser().getAllId();
        Studio stu = studioService.selectOne(new EntityWrapper<Studio>().eq("stu_creatid",zzid));
        System.out.println(stu);
        System.out.println(getUser().getAllId());
        return stu;
    }*/
    @GetMapping("/notice")
    @ResponseBody
    public ResponseBo notice(){
        Users u = usersService.selectById(getUser().getAllId());
        String id = u.getUserId();
        List<Notice> noticeList = noticeService.selectList(new EntityWrapper<Notice>().eq("noti_foreid",id));

        return ResponseBo.ok().put("notice",noticeList);
    }


    @GetMapping("/indexList")
    @ResponseBody
    public ResponseBo mList(){
        /*Studio stu = studioService.selectByzzid(getUser().getAllId());*/

        String yhid = getUser().getAllId();
        /*用户信息*/
        Users user = usersService.selectOne(new EntityWrapper<Users>().eq("user_id",yhid));
        /*工作室信息*/
        String stuId = user.getUserStudio();
        Studio stu = studioService.selectOne(new EntityWrapper<Studio>().eq("stu_id", stuId));

        /*项目信息*/
        String stuid = stu.getStuId();
        List<Project> stuPro = projectService.selectList(new EntityWrapper<Project>().eq("proj_studio",stuid).eq("proj_state","开发完成"));
        List<ProjImg> projimgList = new ArrayList<>();


        for(Project project:stuPro){
            String projImgpath = "/file/showImg/"+project.getProjImg();
            ProjImg projimg = new ProjImg();

            projimg.setProject(project);
            projimg.setProjImgpath(projImgpath);
            projimgList.add(projimg);
        }

        /*成员信息*/
        /*String stuid = "cf20d9f3c7554dfdaa9a45a41494f2c4";*/
        List<Users> userList  = usersService.selectList(new EntityWrapper<Users>().eq("user_studio",stuid));

        List<JobUsers> ju = new ArrayList<JobUsers>();
        for(Users u:userList){
            /*判断是否 是组员*/
            if(u.getUserEntrytime() != null) {
                Jobuser jobuser = jobuserService.selectByUserId(u.getUserId());
                System.out.println(jobuser);
                /*Jobs jobs = jobsService.selectByJuId(jobuser.getJuJobs());*/

                int id = jobuser.getJuJobs();
                Jobs jobs = jobsService.selectById(id);
                JobUsers ju1 = new JobUsers();
                ju1.setJobs(jobs);
                ju1.setUsers(u);
                String  userImgpath = "/file/showImg/"+u.getUserImg();

                System.out.println(userImgpath);

                ju1.setUserImgpath(userImgpath);

                ju.add(ju1);
            }
        }
        stu.setStuMembernum(userList.size());//更改成员数量
        stu.setStuProjectnum(stuPro.size());//更改项目数量
        studioService.updateAllColumnById(stu);

        ResponseBo map = new ResponseBo();
        map.put("user",user);
        map.put("studio",stu);
        map.put("project",projimgList);
        map.put("member",ju);

        return map;
    }


    //获取用户头像
    @RequestMapping("/showImg")
    public void previewsrc(HttpServletResponse response) {
        //将文件名分割成 文件名 和 格式
        //“ . " 需要用两次转义
        Users u=usersService.selectById(getUser().getAllId());
        Studio stu = studioService.selectById(u.getUserStudio());
        String path = stu.getStuImg();
        //获取服务器中的文件
        File imgFile = new File(ConstCofig.RootPath + ConstCofig.ImgPath + path);
        //输出到页面
        FileController.responseFile(response, imgFile);
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
        Studio stu = studioService.selectById(user.getUserStudio());
        stu.setStuImg(imgName);
        studioService.updateAllColumnById(stu);


        //将路径更新至数据库
        /*Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator = promulgatorService.selectID(user.getAllId());
        promulgator.setPromImg(imgName);
        promulgatorService.updateAllColumnById(promulgator);*/
        return ResponseBo.ok();
    }

    @RequestMapping("/studioAdd")
    public String add(HttpServletRequest req, Studio studio){
        String yhid = getUser().getAllId();

        studio.setStuCreatid(yhid);
        studio.setStuImg("niming.png");
        studio.setStuCreattime(new Date());

        System.out.println(yhid);

        studioService.insertStudio(studio);

        Users user = usersService.selectOne(new EntityWrapper<Users>().eq("user_id",yhid));
        user.setUserStudio(studio.getStuId());
        usersService.insertOrUpdate(user);
        return "/studio/studio_index";
        /*usersService.updateStuid(yhid,stuId);*/
    }

    @RequestMapping("/studioDelete")
    public String delete(){
        String yhid = getUser().getAllId();
        /*用户信息*/
        Users user = usersService.selectOne(new EntityWrapper<Users>().eq("user_id",yhid));
        /*工作室信息*/
        String stuId = user.getUserStudio();
        /*删除工作室信息*/
        studioService.deleteById(stuId);
        /* 遍历删除每个带有stuId的User */
        List<Users> userList = usersService.selectList(new EntityWrapper<Users>().eq("user_studio",stuId));
        for(Users user1:userList){
            user1.setUserStudio(null);
            user1.setUserEntrytime(null);
            usersService.updateAllColumnById(user1);
        }

        System.out.println("删除成功");
        return "/studio/studio_creat";
    }

    /*工作室信息修改的渲染界面*/
    @GetMapping("/infoUpdate")
    @ResponseBody
    public ResponseBo infoUpdate(){
        Users u = usersService.selectById(getUser().getAllId());
        String id = u.getUserStudio();
        System.out.println(id);

        /*StudioTag stutag = new StudioTag();*/

        Studio stu = studioService.selectById(id);
        String stuTag  = stu.getStuTag();
        String str[] = stuTag.split(" ");

        String stuField = stu.getStuField();
        String str1[] = stuField.split(",");

        for (int i=0;i<str.length;i++){
            System.out.println(str[i]);
        }

        return ResponseBo.ok().put("studio",stu).put("tag",str).put("field",str1);
    }

    @PostMapping("/studioUpdate")

    public String updateStudio(HttpServletRequest request){
        Users u = usersService.selectById(getUser().getAllId());
        String id = u.getUserStudio();

        Studio stu = studioService.selectById(id);
        stu.setStuName(request.getParameter("stuName"));
        stu.setStuProvince(request.getParameter("stuProvince"));
        stu.setStuCity(request.getParameter("stuCity"));
        stu.setStuArea(request.getParameter("stuArea"));
        stu.setStuField(request.getParameter("stuField"));
        stu.setStuTag(request.getParameter("stuTag"));
        stu.setStuIntro(request.getParameter("stuIntro"));
        studioService.updateAllColumnById(stu);
        return "/studio/studio_index";
    }
}
