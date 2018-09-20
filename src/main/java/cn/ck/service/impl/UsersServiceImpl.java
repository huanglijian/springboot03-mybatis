package cn.ck.service.impl;

import cn.ck.entity.Users;
import cn.ck.mapper.UsersMapper;
import cn.ck.service.UsersService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-19
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
