package cn.ck.service.impl;

import cn.ck.entity.Jobs;
import cn.ck.mapper.JobsMapper;
import cn.ck.service.JobsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class JobsServiceImpl extends ServiceImpl<JobsMapper, Jobs> implements JobsService {

}
