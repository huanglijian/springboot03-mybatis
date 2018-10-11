package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
import cn.ck.entity.bean.JobUsers;
import cn.ck.service.*;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/studioNone")
    public String test1(){
        Users u = usersService.selectById(getUser().getAllId());
        /*System.out.println(getUser().getAllId());*/
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

                ju.add(ju1);
            }
        }

        ResponseBo map = new ResponseBo();
        map.put("user",user);
        map.put("studio",stu);
        map.put("project",stuPro);
        map.put("member",ju);

        return map;
    }


    @RequestMapping("/studioAdd")
    public String add(HttpServletRequest req, Studio studio){
        String yhid = getUser().getAllId();

        studio.setStuCreatid(yhid);
        studio.setStuImg("E:/ck/studio/touxiang/timg.jpg");
        studio.setStuCreattime(new Date());

        System.out.println(yhid);
        System.out.println(studio);

        studioService.insertStudio(studio);
        System.out.println(studio.getStuId());
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
            user.setUserStudio(null);
            user.setUserEntrytime(null);
            usersService.updateAllColumnById(user);
        }

        System.out.println("删除成功");
        return "/studio/studio_creat";
    }
}
