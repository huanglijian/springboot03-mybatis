package cn.ck.exception;

import cn.ck.utils.ShiroUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	@ExceptionHandler(value = AuthorizationException.class)
	public String handleAuthorizationException() {
		if(ShiroUtils.getSubject().getPrincipal() == null)
			return "redirect:/login";
		else
			return "error/403";
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public String handleDuplicateKeyException(DuplicateKeyException e){
		return "error/500";
	}

//	@ExceptionHandler(Exception.class)
//	public String handleException(Exception e){
//		return "error/500";
//	}
}
