package cn.ck.service.impl;

import cn.ck.entity.Jobuser;
import cn.ck.mapper.JobuserMapper;
import cn.ck.service.JobuserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
@Service
public class JobuserServiceImpl extends ServiceImpl<JobuserMapper, Jobuser> implements JobuserService {
    @Autowired
    private JobuserMapper jobuserMapper;

    public Jobuser selectByUserId(String userId){
         return jobuserMapper.selectByUserId(userId);

     }
}
