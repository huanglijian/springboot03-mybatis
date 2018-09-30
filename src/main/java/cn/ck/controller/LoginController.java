package cn.ck.controller;

import cn.ck.entity.Alluser;
import cn.ck.entity.Promulgator;
import cn.ck.entity.Users;
import cn.ck.service.PromulgatorService;
import cn.ck.service.UsersService;
import cn.ck.utils.ResponseBo;
import cn.ck.utils.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;


@Controller
public class LoginController extends AbstractController{

	//验证码产生bean
	@Autowired
	private Producer producer;
	@Autowired
	private PromulgatorService promulgatorService;
	@Autowired
	private UsersService usersService;

	//显示主页
	@RequestMapping("/")
	public String homePage(){
		return "homePage";
	}

	//显示登录页面
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}

	//获取验证码
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

	/**
	 * 登录验证
	 * @param email 用户邮箱
	 * @param password 密码
	 * @param captcha 验证码
	 * @return code：0：成功; 500：失败\ userType : 管理员，发布者，普通用户
	 */
	@PostMapping("/login")
	@ResponseBody
	public ResponseBo login(String email, String password, String captcha) {
		//验证码验证
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return ResponseBo.error("验证码不正确");
		}
		//shiro身份验证
		UsernamePasswordToken token = new UsernamePasswordToken(email, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			//shiro的登录方法
			subject.login(token);
			//更新最后登录时间
			String userType = getUser().getAllType();
			String userId = getUser().getAllId();
			if(userType.equals("普通用户")){
				Users u = usersService.selectById(userId);
				u.setUserLogintime(new Date());
				usersService.updateById(u);
			}
			else if(userType.equals("发布者")){
				Promulgator p = promulgatorService.selectById(userId);
				p.setPromLogintime(new Date());
				promulgatorService.updateById(p);
			}
			//向页面传递数据
			ResponseBo rb = ResponseBo.ok();
			rb.put("userType", getUser().getAllType());
			rb.put("successnUrl", "/");
			return rb;
		} catch (UnknownAccountException e) {
			return ResponseBo.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return ResponseBo.error("账号或密码错误");
		} catch (LockedAccountException e) {
			return ResponseBo.error(e.getMessage());
		} catch (AuthenticationException e) {
			return ResponseBo.error("认证失败！");
		}
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

	@RequestMapping("getLoginUser")
	@ResponseBody
	public ResponseBo getLoginUser(){
		Alluser loginUser = getUser();
		if(loginUser == null)
			return ResponseBo.error(404, "no login");
		return ResponseBo.ok().put("loginUser", loginUser);
	}
}
