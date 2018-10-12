package cn.ck.controller.admin;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Alluser;
import cn.ck.entity.Users;
import cn.ck.service.AdminService;
import cn.ck.service.AlluserService;
import cn.ck.service.UsersService;
import cn.ck.utils.EnityUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiresRoles("admin")
@RequestMapping("admin")
public class AdminAuthorityController extends AbstractController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AlluserService alluserService;
    @Autowired
    private UsersService usersService;

    @RequestMapping("test")
    public String test(){
        return "admin/alluser";
    }

    @RequestMapping("allusers")
    @ResponseBody
    public List<Alluser> getAllusers(){
        Map<String, Object> map = new HashMap<>();
        return alluserService.selectList(new EntityWrapper<>());
    }

    @RequestMapping("map")
    @ResponseBody
    public List<Map<String, Object>> getMap(){
        List<Alluser> allusers = alluserService.selectList(new EntityWrapper<>());
        List<Users> users = usersService.selectList(new EntityWrapper<>());

        Alluser alluser = allusers.get(0);
        Users users1 = users.get(0);
        
        Map<String, Object> alluserMap = EnityUtils.entityToMap(alluser);
        Map<String, Object> userMap = EnityUtils.entityToMap(users1);

        alluserMap.putAll(userMap);

        List<Map<String, Object>> list = new ArrayList<>();

        list.add(alluserMap);
        return list;
    }
}
