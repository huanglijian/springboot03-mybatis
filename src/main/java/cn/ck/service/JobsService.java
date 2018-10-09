package cn.ck.service;

import cn.ck.entity.Jobs;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface JobsService extends IService<Jobs> {

//    查询招聘表
    public List<Jobs> selectAll();

    public Jobs selectByJuId(int JuId );

    /**
     * 首页取除推荐的职位
     * 招聘中的，按时间排序
     * @return
     */
    List<Jobs> selectLatestJobs();
}
