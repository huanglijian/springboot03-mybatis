package cn.ck.controller.admin;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Alluser;
import cn.ck.service.AdminService;
import cn.ck.service.AlluserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiresRoles("admin")
@RequestMapping("admin")
public class AdminAuthorityController extends AbstractController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AlluserService alluserService;

    @RequestMapping("test")
    public String test(){
        return "admin/alluser";
    }

    @RequestMapping("allusers")
    @ResponseBody
    public List<Alluser> getAllusers(){
        return alluserService.selectList(new EntityWrapper<>());
    }
}
