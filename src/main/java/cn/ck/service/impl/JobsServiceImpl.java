package cn.ck.service.impl;

import cn.ck.entity.Jobs;
import cn.ck.mapper.JobsMapper;
import cn.ck.service.JobsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
@Service
public class JobsServiceImpl extends ServiceImpl<JobsMapper, Jobs> implements JobsService {
//    @Override
//    public Jobs selectById(int job_id) {
//
//        return null;
//    }

    @Override
//    public boolean updateJobs(int job_id, Date job_creattime, String job_money, String job_intro, String job_require, String job_state, int job_num, String job_type, String job_name, String job_studio) {
    public boolean updateJobs(int job_id, String job_name) {
        Jobs jobs = new Jobs();
        jobs.setJobId(job_id);
        return this.update(jobs,
                new EntityWrapper<Jobs>().eq("job_id",job_id).eq("job_num",job_name));
    }
}
