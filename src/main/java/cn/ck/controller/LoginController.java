package cn.ck.controller;

import cn.ck.entity.Alluser;
import cn.ck.utils.ResponseBo;
import cn.ck.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;





@Controller
public class LoginController {

	@RequestMapping("/")
	public String homePage(){
		return "homePage";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseBo login(String username, String password, Boolean rememberMe) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return ResponseBo.ok();
		} catch (UnknownAccountException e) {
			return ResponseBo.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return ResponseBo.error(e.getMessage());
		} catch (LockedAccountException e) {
			return ResponseBo.error(e.getMessage());
		} catch (AuthenticationException e) {
			return ResponseBo.error("认证失败！");
		}
	}
	
	@GetMapping("/403")
	public String forbid() {
		return "error/403";
	}

	@RequestMapping("/index")
	public String index(Model model) {
		Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "index";
	}
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}
}
