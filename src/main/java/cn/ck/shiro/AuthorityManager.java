package cn.ck.shiro;

public interface AuthorityManager {

    void addRoleToUser(String roleName, String userId) throws Exception;
}
