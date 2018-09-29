package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
import cn.ck.entity.bean.JobUsers;
import cn.ck.mapper.UsersMapper;
import cn.ck.service.*;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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
    private JobsService jobsService;
    @Autowired
    private PromulgatorService promulgatorService;
    @Autowired
    private JobuserService jobuserService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/studioNone")
    public String test1(){
        Users u = usersService.selectById(getUser().getAllId());
        System.out.println(getUser().getAllId());
        if(u.getUserStudio() == null)
            return "studio/studio_creat";
        else
            return "studio/studio_index";
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
        /*工作室信息*/
        String zzid = getUser().getAllId();
        Studio stu = studioService.selectOne(new EntityWrapper<Studio>().eq("stu_creatid",zzid));
        /*项目信息*/
        String stuid = stu.getStuId();
        List<Project> stuPro = projectService.selectList(new EntityWrapper<Project>().eq("proj_studio",stuid).eq("proj_state","开发完成"));
        /*成员信息*/
        /*String stuid = "cf20d9f3c7554dfdaa9a45a41494f2c4";*/
        List<Users> userList  = usersService.selectList(new EntityWrapper<Users>().eq("user_studio",stuid));
        List<JobUsers> ju = new ArrayList<JobUsers>();
        for(Users u:userList){
            Jobuser jobuser = jobuserService.selectByUserId(u.getUserId());
            Jobs jobs = jobsService.selectByJuId(jobuser.getJuJobs());
            JobUsers ju1 = new JobUsers();
            ju1.setJobs(jobs);
            ju1.setUsers(u);

            ju.add(ju1);
        }
        ResponseBo map = new ResponseBo();
        map.put("studio",stu);
        map.put("project",stuPro);
        map.put("member",ju);
        return map;
    }


    @RequestMapping("/studioAdd")
    public void add(HttpServletRequest req, Studio studio){
        String yhid = getUser().getAllId();
        String stuId = studioService.insertStudio(studio,yhid);
        usersService.updateStuid(yhid,stuId);
    }
}
