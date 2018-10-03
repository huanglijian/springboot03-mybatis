package cn.ck.controller.loginRegisted;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
//	@RequiresPermissions("user:user")
	@RequiresRoles(value = {"promulgator","users"}, logical= Logical.OR)
	@RequestMapping("list")
	public String userList(Model model) {
		model.addAttribute("value", "获取用户信息");
		return "user";
	}
	
	@RequiresPermissions("user:add")
	@RequestMapping("add")
	public String userAdd(Model model) {
		model.addAttribute("value", "新增用户");
		return "user";
	}
	
	@RequiresPermissions("user:delete")
	@RequestMapping("delete")
	public String userDelete(Model model) {
		model.addAttribute("value", "删除用户");
		return "user";
	}
}
