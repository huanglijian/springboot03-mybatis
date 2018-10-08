package cn.ck.service.impl;

import cn.ck.entity.Jobs;
import cn.ck.mapper.JobsMapper;
import cn.ck.service.JobsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
   @Autowired
   private JobsMapper jobsMapper;

    @Override
    public List<Jobs> selectAll() {
        List<Jobs> jobs = this.selectList(new EntityWrapper<Jobs>());


        return null;
    }

    public Jobs selectByJuId(int JuId ){
        return jobsMapper.selectByJuId(JuId);
    }

    @Override
    public List<Jobs> selectLatestJobs() {
        List<Jobs> list = baseMapper.selectList(
                new EntityWrapper<Jobs>()
                .eq("job_state", "招聘中")
                .orderBy("job_creattime", false));
        return list;
    }
}
