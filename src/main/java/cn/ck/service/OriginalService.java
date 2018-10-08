package cn.ck.service;

import cn.ck.entity.Original;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface OriginalService extends IService<Original> {

    /**
     * 主页取出最高分的原创
     * @return
     */
    List<Original> selectHeigestGradeOrigin();
}
