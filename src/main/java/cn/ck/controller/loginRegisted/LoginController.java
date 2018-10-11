package cn.ck.controller.loginRegisted;

import cn.ck.controller.AbstractController;
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
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
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
public class LoginController extends AbstractController {

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
	//@RequiresGuest
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
			String next;
			//判断登录用户的类型
			if(userType.equals("普通用户")){
				Users u = usersService.selectById(userId);
				u.setUserLogintime(new Date());
				usersService.updateById(u);
				next="/";
			}
			else if(userType.equals("发布者")){
				Promulgator p = promulgatorService.selectById(userId);
				p.setPromLogintime(new Date());
				promulgatorService.updateById(p);
				next="/";
			}
			else{
				subject.logout();
				return ResponseBo.error("用户类型错误,请使用用户账号登录");
			}
			//向页面传递数据
//			rb.put("userType", getUser().getAllType());
			return ResponseBo.ok().put("next",next);
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

	/**
	 * 退出
	 */
	@RequiresUser
	@RequestMapping("/logout")
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}


	/************************************************后台管理*********************************************/

	/**
	 * 后台登录页面
	 */
	@RequiresGuest
	@GetMapping("/admin/login")
	public String adminLogin() {
		return "admin/login";
	}

	/**
	 * 登录验证
	 * @param email 用户邮箱
	 * @param password 密码
	 * @param captcha 验证码
	 * @return code：0：成功; 500：失败\ userType : 管理员，发布者，普通用户
	 */
	@PostMapping("/admin/login")
	@ResponseBody
	public ResponseBo adminLogin(String email, String password, String captcha) {
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
			//判断登录用户的类型
			String userType = getUser().getAllType();
			if(!userType.equals("管理员")){
				subject.logout();
				return ResponseBo.error("请用管理员账号登录");
			}
			//向页面传递数据
			String next = "/admin/adminHome";
			return ResponseBo.ok().put("next",next);
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

	@RequiresRoles("admin")
	@RequestMapping("/admin/adminHome")
	public String adminHome(){
		return "admin/adminHome";
	}
}
