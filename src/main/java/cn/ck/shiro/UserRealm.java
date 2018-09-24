package cn.ck.shiro;

import cn.ck.entity.Alluser;
import cn.ck.entity.Permission;
import cn.ck.entity.Role;
import cn.ck.mapper.AlluserMapper;
import cn.ck.mapper.PermissionMapper;
import cn.ck.mapper.RoleMapper;
import cn.ck.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private AlluserMapper alluserMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;
    
    /**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
		String email = user.getAllEmail();

		System.out.println("用户" + email + "获取权限-----ShiroRealm.doGetAuthorizationInfo");
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		// 获取用户角色集
		List<Role> roleList = roleMapper.findByUserEmail(email);
		Set<String> roleSet = new HashSet<String>();
		for (Role r : roleList) {
			roleSet.add(r.getRoName());
		}
		simpleAuthorizationInfo.setRoles(roleSet);

		// 获取用户权限集
		List<Permission> permissionList = permissionMapper.findByUserEmail(email);
		Set<String> permissionSet = new HashSet<String>();
		for (Permission p : permissionList) {
			permissionSet.add(p.getPerName());
		}
		simpleAuthorizationInfo.setStringPermissions(permissionSet);
		return simpleAuthorizationInfo;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;


		String email = token.getUsername();
		Alluser user =  alluserMapper.findByEmail(email);

		if (user == null) {
			throw new UnknownAccountException("用户名或密码错误！");
		}
		if (!user.getAllState().equals("1")) {
			throw new LockedAccountException("账号状态异常,请联系管理员！");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getAllPwd(), ByteSource.Util.bytes(user.getAllSalt()), getName());
		return info;
	}

	//告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}