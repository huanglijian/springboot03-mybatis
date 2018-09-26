package cn.ck.mapper;

import cn.ck.entity.Jobs;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface JobsMapper extends BaseMapper<Jobs> {
//    Alluser findByEmail(@Param("email") String email);
    Jobs findById(@Param("job_id") String email);
}
