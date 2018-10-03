package cn.ck.shiro;

public interface AuthorityManager {

    /**
     * shiro的注解
     * @RequiresAuthentication 要求当前Subject 已经在当前的session 中被验证通过才能被访问或调用
     * @RequiresGuest 要求当前的Subject 是一个"guest"，也就是说，他们必须是在之前的session 中没有被验证或被记住才能被访问或调用
     * @RequiresPermissions("permission") 要求当前的Subject 被允许一个或多个权限，以便执行注解的方法
     * @RequiresRoles("role")  要求当前的Subject 拥有所有指定的角色。如果他们没有，则该方法将不会被执行，而且AuthorizationException 异常将会被抛出
     * @RequiresUser 需要当前的Subject 是一个应用程序用户才能被注解的类/实例/方法访问或调用
     * @RequiresRoles(value = {"promulgator","users"}, logical= Logical.OR)  使用逻辑或的示例
     */

    /**
     * 向用户添加一个角色
     * @param roleName 角色名
     * @param userId 用户uuid
     * @throws Exception
     */
    void addRoleToUser(String roleName, String userId) throws Exception;
}
