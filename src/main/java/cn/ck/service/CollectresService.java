package cn.ck.service;

import cn.ck.entity.Collectres;
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
public interface CollectresService extends IService<Collectres> {

    /**
     * 返回收藏某个视频的人数
     * @param resId 视频id
     * @return
     */
    int getCollectedNumByRes(Integer resId);

    /**
     * 用户是否已经收藏该视频
     * @param userId 用户id
     * @param resId 视频id
     * @return
     */
    boolean isCollected(String userId, Integer resId) throws Exception;
}
