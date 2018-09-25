package cn.ck.shiro;

import cn.ck.entity.Alluser;
import cn.ck.entity.Role;
import cn.ck.entity.Userrole;
import cn.ck.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限的操作
 * 添加角色
 * 添加权限
 * 为用户添加角色
 * 为角色添加权限
 */
@Service
public class AuthorityManagerImpl implements AuthorityManager {

    @Autowired
    private AlluserService alluserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserroleService userroleService;
    @Autowired
    private RolepermissionService rolepermissionService;

    private String userTypeConvert(String userType) throws Exception {
        switch (userType) {
            case "发布者":
                return "promulgator";
            case "管理员":
                return "admin";
            case "普通用户":
                return "users";
            default:
                throw new Exception("用户类型错误");
        }
    }

    public void addRoleToUser(String userType, String userId) throws Exception {
        List<Role> roleList = roleService.selectList(new EntityWrapper<Role>().eq("ro_name", userTypeConvert(userType)));
        Alluser user = alluserService.selectById(userId);

        if(user == null)
            return;
        if(roleList == null || roleList.size() < 1)
            return;

        for (Role r : roleList) {
            Userrole userrole = new Userrole();
            userrole.setUrAllid(userId);
            userrole.setUrRoid(r.getRoId());
            userroleService.insert(userrole);
        }
    }

}
