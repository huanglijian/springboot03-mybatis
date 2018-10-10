package cn.ck.service;

import cn.ck.entity.Collectori;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface CollectoriService extends IService<Collectori> {

    boolean isCollected(String userId, Integer origId) throws Exception;
}
