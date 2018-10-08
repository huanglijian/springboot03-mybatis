package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
import cn.ck.entity.bean.JobUsers;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.service.*;
import cn.ck.utils.DateUtils;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/9/27 17:12
 **/

@Controller
@RequestMapping("/mtest")
public class testController extends AbstractController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private PromulgatorService promulgatorService;
    @Autowired
    private JobuserService jobuserService;
    @Autowired
    private JobsService jobsService;




    @RequestMapping("/jump")
    public String pageTest(){
        return "studio/studio_test";
    }


    @GetMapping("/list")
    @ResponseBody
    public List listTest(){
        System.out.println("成功跳转list");
        String stuid = "cf20d9f3c7554dfdaa9a45a41494f2c4";
        List<Users> u  = usersService.selectList(new EntityWrapper<Users>().eq("user_studio",stuid));
        System.out.println(u);
        return u;
    }

    @GetMapping("/map")
    @ResponseBody
    public ResponseBo mapTest(){
        System.out.println("成功跳转map");
        String stuid = "cf20d9f3c7554dfdaa9a45a41494f2c4";
        String proname = "阿里巴巴公司";

        List<Users> u  = usersService.selectList(new EntityWrapper<Users>().eq("user_studio",stuid));
        List<Promulgator> p = promulgatorService.selectList(new EntityWrapper<Promulgator>().eq("prom_name",proname));

        System.out.println(u);
        System.out.println(p);

        ResponseBo res = new ResponseBo();
        res.put("users",u);
        res.put("promulgator",p);
        return res;
    }

/*    @GetMapping("/meList")
    @ResponseBody
    public List mList(){
        System.out.println("跳转meList成功！！");

        String stuid = "cf20d9f3c7554dfdaa9a45a41494f2c4";
        List<Users> userList  = usersService.selectList(new EntityWrapper<Users>().eq("user_studio",stuid));
        List<JobUsers> ju = new ArrayList<JobUsers>();
        for(Users u:userList){
            Jobuser jobuser = jobuserService.selectByUserId(u.getUserId());
            System.out.println(jobuser.getJuJobs());

            Jobs jobs = jobsService.selectByJuId(jobuser.getJuJobs());
            JobUsers ju1 = new JobUsers();
            ju1.setJobs(jobs);
            ju1.setUsers(u);

            ju.add(ju1);
        }
        return ju;
    }*/

}
