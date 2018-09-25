package cn.ck.service;

import cn.ck.entity.Jobs;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface JobsService extends IService<Jobs> {

//    查找招聘表
//    Jobs selectById(int job_id);

//    修改招聘表job
    boolean updateJobs(int job_id,String job_name);
}
