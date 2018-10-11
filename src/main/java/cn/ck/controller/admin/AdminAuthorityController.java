package cn.ck.controller.admin;

import cn.ck.controller.AbstractController;
import cn.ck.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminAuthorityController extends AbstractController {

    @Autowired
    private AdminService adminService;


}
