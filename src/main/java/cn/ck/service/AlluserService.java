package cn.ck.service;

import cn.ck.entity.Alluser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-19
 */
public interface AlluserService extends IService<Alluser> {

    /**
     * 修改密码
     * @param userUUId       用户UUID
     * @param oldPassword     原密码
     * @param newPassword  新密码
     */
    boolean updatePassword(String userUUId, String oldPassword, String newPassword);

    /**
     * 保存用户
     */
    void save(Alluser user);

    /**
     * 查询邮箱是否已注册
     * @param email 用户邮箱
     * @return true 已存在
     */
    boolean isExist(String email);

    /**
     * 为user生成UUID
     * @param user 传入user实体
     * @return 设置了UUID的user实体
     */
    Alluser setUserUUID(Alluser user);
}
