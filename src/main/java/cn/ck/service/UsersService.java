package cn.ck.service;

import cn.ck.entity.Users;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface UsersService extends IService<Users> {

    public void updateStuid(String userId,String stuId);
}
