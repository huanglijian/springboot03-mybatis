package cn.ck.controller;

import cn.ck.entity.Alluser;
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


@Controller
public class LoginController {

	@Autowired
	private Producer producer;

	@RequestMapping("/")
	public String homePage(){
		return "homePage";
	}

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

	@GetMapping("/login")
	public String login() {
		return "login/login";
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseBo login(String email, String password, String captcha) {

		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return ResponseBo.error("验证码不正确");
		}

		UsernamePasswordToken token = new UsernamePasswordToken(email, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return ResponseBo.ok();
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
}
