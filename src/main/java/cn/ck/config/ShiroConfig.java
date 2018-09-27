package cn.ck.config;

import cn.ck.shiro.RedisShiroSessionDAO;
import cn.ck.shiro.UserRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

	@Bean("sessionManager")
	public SessionManager sessionManager(RedisShiroSessionDAO redisShiroSessionDAO){
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		//设置session过期时间为1小时(单位：毫秒)，默认为30分钟
		sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionIdUrlRewritingEnabled(false);

		//如果开启redis缓存且renren.shiro.redis=true，则shiro session存到redis里
		sessionManager.setSessionDAO(redisShiroSessionDAO);
		return sessionManager;
	}

	@Bean
	public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		//设置realm.
		securityManager.setRealm(userRealm);
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager);
		//记住我功能
		securityManager.setRememberMeManager(rememberMeManager());
//		securityManager.setCacheManager(cacheManager());
		return securityManager;
	}

	/**
	 * @see ShiroFilterFactoryBean
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
//		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 设置无权限时跳转的 url;
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		//自定义权限拦截 Filter
//		Map<String, Filter>filters = Maps.newHashMap();
//		filters.put("perms", urlPermissionsFilter());
//		filters.put("anon", new AnonymousFilter());
//		bean.setFilters(filters);

		// 拦截器。匹配原则是最上面的最优先匹配，坑爹的Maps.newHashMap();
		Map<String, String> chains = new LinkedHashMap<>();
		chains.put("/login", "anon");
		chains.put("/registered/**", "anon");
		//配置静态文件不会被拦截
		chains.put("/static/**", "anon");
		chains.put("/403", "anon");
		chains.put("/captcha.jpg", "anon");
		chains.put("/druid/**", "anon");
		//配置不会被拦截的链接
		chains.put("/", "anon");
		chains.put("/ckadmin/**", "anon");
		chains.put("/ForJob/**", "anon");
		chains.put("/resource/**", "anon");
		chains.put("/studio/**", "anon");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		chains.put("/logout", "logout");
		// 剩余请求需要身份认证
		chains.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
		return shiroFilterFactoryBean;
	}
	/**
	 * 这个参数是RememberMecookie的名称，随便起。
	 * remenberMeCookie是一个实现了将用户名保存在客户端的一个cookie，与登陆时的cookie是两个simpleCookie。
	 * 登陆时会根据权限去匹配，如是user权限，则不会先去认证模块认证，而是先去搜索cookie中是否有rememberMeCookie，
	 * 如果存在该cookie，则可以绕过认证模块，直接寻找授权模块获取角色权限信息。
	 * 如果权限是authc,则仍会跳转到登陆页面去进行登陆认证.
	 * @return
	 */
	public SimpleCookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setMaxAge(86400);
		return cookie;
	}

	/**
	 * cookie管理对象;记住我功能
	 */
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
