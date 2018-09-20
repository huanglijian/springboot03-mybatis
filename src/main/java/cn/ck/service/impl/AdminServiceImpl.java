package cn.ck.service.impl;

import cn.ck.entity.Admin;
import cn.ck.mapper.AdminMapper;
import cn.ck.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
